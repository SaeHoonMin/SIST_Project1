package com.bss.client.container;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.CountDown;
import com.bss.client.components.GridHighlight;
import com.bss.client.components.StyleChatTextArea;
import com.bss.client.components.StyleTextField;
import com.bss.client.components.WhiteFullScreenPane;
import com.bss.client.gameObjects.AnimName;
import com.bss.client.gameObjects.FixedLocAnimation;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Ship;
import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.ShipColor;
import com.bss.client.gameObjects.ShipType;
import com.bss.client.gameObjects.Tile;
import com.bss.client.gameObjects.TileState;
import com.bss.common.AttackResult;
import com.bss.common.BssProtocol;
import com.bss.common.MatchState;

import resources.ResContainer;
import resources.ResLoader;

public class GamePlayPanel extends JPanel implements KeyListener{
	
	private static GamePlayPanel inst;
	
	Image bgImg;
	
	Grid enemyGrid;
	Grid myGridInfo;
	Grid myGrid;
	GridHighlight ghlight;
	ArrayList<Ship> ships;
	
	CountDown countDown;
	
	WhiteFullScreenPane turnPanel ;
	
	private MatchState matchState;
	
	private boolean isMyTurn = false;
	private boolean actionAllowed =  false;
	
	private int myShipCount=5;
	private int enemyShipCount=5;
	
	private StyleTextField myId;
	private StyleTextField enemyId;
	
	private StyleChatTextArea myChatField;
	private StyleChatTextArea enemyChatField;
	private StyleTextField myInputField;
	
	private BssNetWork netWork;
	
	Thread countThread;
	
	public int getMyShipCount()
	{
		return myShipCount;
	}

	public static GamePlayPanel getInst()
	{
		return inst;
	}
	
	public GamePlayPanel(Grid grid, JFrame frame)
	{
		inst = this;
		netWork = BssNetWork.getInst();
		netWork.setGamePlay(this);
		
		isMyTurn = false;
		matchState = MatchState.START;
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		
		bgImg = toolKit.createImage(ResLoader.getResURL("images/bg3.jpg"));
		
		
		setSize(frame.getWidth(),frame.getHeight());
		
		int gridX = (frame.getWidth() -  (1100)) /2;
		int gridY = frame.getHeight()/2 -500/2 -15;
		
		turnPanel = new WhiteFullScreenPane(this);
		add(turnPanel);
		setComponentZOrder(turnPanel, getComponentCount()-1);
		turnPanel.setVisible(false);
		
		enemyGrid = new Grid(gridX-10,gridY,this);	
		enemyGrid.setMouseListenerForTile();
		
		myGridInfo = grid;
		myGrid = new Grid(gridX+610,gridY,this);
		
	
		setShip();
		
		ghlight = new GridHighlight(this);
		add(ghlight);
		ghlight.setLocation(myGrid.getStartX()-6, myGrid.getStartY()-6);
		
		enemyGrid.setGridZOrder(getComponentCount()-1);
		enemyGrid.setEnemyTileAction();
		myGrid.setGridZOrder(getComponentCount()-1);
		
		
		JLabel ourLabel = new JLabel();
		ourLabel.setIcon(new ImageIcon(ResContainer.ourForces));
		ourLabel.setLocation(myGrid.getStartX()-50, myGrid.getStartY()-40);
		ourLabel.setSize(600,600);
		ourLabel.setVisible(true);
		ourLabel.setOpaque(false);		
		add(ourLabel);
		
		countDown = new CountDown();
		countDown.setLocation(frame.getWidth()/2 - 100/2, gridY-105);
		countDown.setText("");
		add(countDown);
		
		
		myId = new StyleTextField(200,30);
		myId.setLocation(myGrid.getStartX() + 500 - myId.getWidth(), myGrid.getStartY()+510);
		myId.setFocusable(false);
		myId.getField().setText(MainFrame.getUserId());
		setComponentZOrder(myId,getComponentCount()-1);
		enemyId = new StyleTextField(200,30);
		enemyId.setLocation(enemyGrid.getStartX(), myGrid.getStartY()+510);
		enemyId.setFocusable(false);
		setComponentZOrder(enemyId,getComponentCount()-1);
		add(myId);add(enemyId);
		
		
		myChatField = new StyleChatTextArea(500, 30);
		myChatField.setLocation(myGrid.getStartX() + 500 - myChatField.getWidth(), myGrid.getStartY()+550);
		enemyChatField =  new StyleChatTextArea(500,30);
		enemyChatField.setLocation(enemyGrid.getStartX(), myGrid.getStartY()+550);
		
		myInputField = new StyleTextField(500,30);
		myInputField.getField().removeCaretListener(myInputField);
		myInputField.getField().removeKeyListener(myInputField);
		myInputField.setLength(40);
		myInputField.getField().setFont(new Font("맑은 고딕",Font.BOLD,16));
		myInputField.setLocation(frame.getWidth()/2 -500/2, frame.getHeight() - 80);
		myInputField.setVisible(false);
		
		add(myInputField); add(myChatField); add(enemyChatField);
		
		
		setComponentZOrder(ourLabel,getComponentCount()-1);
		
		netWork.sendMessage(BssProtocol.ID_REQ, null);
		
		setFocusable(true);

		new Thread(new Runnable(){

			@Override
			public void run() {
				while(MainFrame.getPanelState()==PanelState.GAMEPLAY)
				{
					if(!myInputField.isVisible())
						requestFocus();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
		
		addKeyListener(this);
		showTileInfo();
	}
	
	public void setEnemyId(String str)
	{
		enemyId.getField().setText(str);
	}
	
	public Grid getMyGrid()
	{
		return myGrid;
	}
	
	private void randomAttack()
	{
		Random rand =new Random();
		int r,c;
		while(true)
		{
			r = rand.nextInt(10);
			c = rand.nextInt(10);
			Tile t = enemyGrid.getTileByRC(r, c);
			if(t.getState() == TileState.UNKNOWN){
				BssNetWork.getInst().sendMessage(BssProtocol.ATTACK_PERFORMED, t);
				setActionAllowed(false);
				break;
			}
		}
	}
	
	public void startCountDown() {
		countThread = new Thread(new Runnable() {

			@Override
			public void run() {
				countDown.setText("15");
				int i = 15;
				try{
				while(actionAllowed)
				{
					countDown.setText(String.valueOf(i));
					i--;
					if(i<0)
					{
						randomAttack();
						break;
					}
					Thread.sleep(1000);
				}
				}catch (Exception e)
				{
					
				}
				countDown.setText("");
			}

		});
		countThread.start();
	}
	public void stopCountDown()
	{
		countThread.interrupt();
		countDown.setText("");
	}

	
	//길어질것이다...
	public AttackResult Attacked(int row, int col)
	{
		Tile infoTile = myGridInfo.getTileByRC(row, col);
		Tile myTile = myGrid.getTileByRC(row, col);
		
		AttackResult ret;
		
		if(infoTile.getLocatedShip()!=null)
		{
			Ship ship = infoTile.getLocatedShip();
			
			if( ship.attacked() != 0)
			{
				FixedLocAnimation.Play(AnimName.EXPLOSION_1, this, myTile.getX()+25, myTile.getY()+25);
				myTile.setState(TileState.INVALID);
				myTile.setIcon(ResContainer.tile_invalid_icon);
				ret = new AttackResult(row,col,true);
			}
			else
			{
				myTile.setState(TileState.INVALID);
				myTile.setIcon(ResContainer.tile_invalid_icon);
				
				Tile head = ship.getHeadTile();
				Tile tail = ship.getTaileTile();
				
				for(int i = head.getRow(); i<=tail.getRow(); i++)
				{
					for(int j= head.getCol(); j<=tail.getCol(); j++)
					{
						Tile temp = myGrid.getTileByRC(i, j);
						FixedLocAnimation.Play(AnimName.EXPLOSION_2, this, temp.getX()+25, temp.getY()+25);
					}
				}
				for(int i = head.getRow()-1; i<=tail.getRow()+1; i++)
				{
					for(int j= head.getCol()-1; j<=tail.getCol()+1; j++)
					{
						if( i>=0 && i<10 && j>=0 && j<10)
						{
							Tile temp2 = myGrid.getTileByRC(i, j);
							if(temp2.getState() != TileState.INVALID)
							{
								temp2.setIcon(ResContainer.tile_reserved_icon);
								temp2.setState(TileState.RESERVED);
							}
								
						}
					}
				}
				
				myShipCount--;
				
				ret = new AttackResult(row, col, true, 
						ship.getType(),head.getRow(),head.getCol(),tail.getRow(),tail.getCol(),ship.getAngle());
			}
		}
		else
		{
			myTile.setState(TileState.RESERVED);
			myTile.setIcon(ResContainer.tile_reserved_icon);
			ret = new AttackResult(row,col,false);
		}
		return ret;
	}
	
	public void AttackDone(AttackResult result)
	{
		int row1 = result.row;
		int col1 = result.col;
		boolean isHit = result.isHit;
		
		Tile t = enemyGrid.getTileByRC(row1, col1);
		
		if(isHit)
		{
			if(result.type == null){
				FixedLocAnimation.Play(AnimName.EXPLOSION_1, GamePlayPanel.getInst(), t.getCenterX(), t.getCenterY());
				t.setIcon(ResContainer.tile_invalid_icon);
				t.setState(TileState.INVALID);
			}
			else
			{
				Tile shipTile = enemyGrid.getTileByRC(result.headRow, result.headCol);
				Ship s = new Ship(result.type,result.ang,ShipColor.RED);
				
				s.setLocation(shipTile.getX(), shipTile.getY());
				s.removeMouseListener(s);
				s.removeMouseMotionListener(s);
				add(s);
				setComponentZOrder(s, 1);
				
				for(int i = result.headRow; i<=result.tailRow; i++)
				{
					for(int j= result.headCol; j<=result.tailCol; j++)
					{
						Tile temp = enemyGrid.getTileByRC(i, j);
						FixedLocAnimation.Play(AnimName.EXPLOSION_2, this, temp.getX()+25, temp.getY()+25);
						temp.setIcon(ResContainer.tile_invalid_icon);
						temp.setState(TileState.INVALID);
					}
				}
				for(int i = result.headRow-1; i<=result.tailRow+1; i++)
				{
					for(int j= result.headCol-1; j<=result.tailCol+1; j++)
					{
						if( i>=0 && i<10 && j>=0 && j<10)
						{
							Tile temp2 = enemyGrid.getTileByRC(i, j);
							if(temp2.getState() != TileState.INVALID)
							{
								temp2.setIcon(ResContainer.tile_reserved_icon);
								temp2.setState(TileState.RESERVED);
							}
								
						}
					}
				}

				enemyShipCount--;
				if (enemyShipCount == 0) {
					
					actionAllowed = false;
					turnPanel.setVisible(true);
					turnPanel.setFont(new Font("Arial",Font.BOLD,50));
					turnPanel.setText("Victory!");
					

					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					turnPanel.setVisible(false);

					netWork.sendMessage(BssProtocol.MATCH_ENDS, null);
					
					MainFrame.getInst().openWaitRoom();
					
				}
				
			}
		}
		else
		{
			t.setIcon(ResContainer.tile_reserved_icon);
			t.setState(TileState.RESERVED);
		}
		
		startCountDown();
		GamePlayPanel.getInst().setActionAllowed(true);
		
	}
	
	public void showTileInfo()
	{
		System.out.println("tile mapping info");
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				Tile t = myGridInfo.getTileByRC(i, j);
				if(t.getLocatedShip()!=null)
				{
					System.out.print("O ");
				}
				else
					System.out.print("X ");
			}
			System.out.println();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,this.getWidth(),this.getHeight(), this);
		paintChildren(g);
		setOpaque(false);
	}	
	
	private void setShip()
	{
		ships = myGridInfo.getLocatedShip();
		for(int i=0; i<ships.size() ;i++)
		{
			Ship s = ships.get(i);
			
			int row = s.getHeadTile().getRow();
			int col = s.getHeadTile().getCol();
			ShipAngle angle = s.getAngle();
			
			Tile myTile = myGrid.getTileByRC(row, col);
			
			Ship ship = new Ship(s.getType(),angle);
			ship.removeMouseListener(ship);
			ship.removeMouseMotionListener(ship);
			setComponentZOrder(ship, getComponentCount()-1);
			add(ship);
			ship.setLocation(myTile.getX(), myTile.getY());
			
			//배 정보 배치
		}
	}
	
	public void showMyTurn()
	{
		actionAllowed = false;
		turnPanel.setVisible(true);
		turnPanel.setText("Your Turn");
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ghlight.moveToGrid(myGrid);
		
		//내턴일때 화면 마우스오버 먹히는거 버그 수정
		
		turnPanel.setVisible(false);
		actionAllowed = true;
		startCountDown();
	}
	public void showEnemyTurn()
	{
		actionAllowed = false;
		setComponentZOrder(turnPanel, 1);
		turnPanel.setText("Enemy Turn");
		turnPanel.setVisible(true);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ghlight.moveToGrid(enemyGrid);
		
		turnPanel.setVisible(false);
		actionAllowed = true;
	}
	
	/*
	 * 
	 *   lose & freewin &... -> 중복 코드!
	 */
	public void lose()
	{
		actionAllowed = false;
		turnPanel.setVisible(true);
		turnPanel.setText("You\nLose    ");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnPanel.setVisible(false);
		netWork.sendMessage(BssProtocol.MATCH_ENDS, null);
		MainFrame.getInst().openWaitRoom();
	}
	public void freeWin()
	{
		actionAllowed = false;
		turnPanel.setVisible(true);
		turnPanel.setText("Victory!\nYour opponent has left.");

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnPanel.setVisible(false);
		MainFrame.getInst().openWaitRoom();
	}
	
	public void setMatchSTate(MatchState state)
	{
		matchState = state;
	}
	public MatchState getMatchState()
	{
		return matchState;
	}

	public boolean isMyTurn() {
		return isMyTurn;
	}

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

	public boolean isActionAllowed() {
		return actionAllowed;
	}

	public void setActionAllowed(boolean actionAllowed) {

		this.actionAllowed = actionAllowed;
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(myInputField.isVisible())
			{
				if(myInputField.getText().length()>0)
				{
					//챗메세지날림, 내 메세지창 켬, 내 인풋창 끔
					myChatField.showText(myInputField.getText());
					netWork.sendMessage(BssProtocol.CHAT_MSG, myInputField.getText());
					
					myInputField.getField().setText(null);
					myInputField.setVisible(false);
					myInputField.getField().removeKeyListener(this);
				}
				else
				{
					myInputField.setVisible(false);
					myInputField.getField().removeKeyListener(this);
				}
			}
			else
			{	
				myInputField.setVisible(true);
				myInputField.getField().addKeyListener(this);
				myInputField.requestFocus();
			}
			
		}
	}
	
	public void chatMsgReceive(String str)
	{
		System.out.println(str);
		enemyChatField.showText(str);
	}

	public void keyReleased(KeyEvent e) {
		
	}

}
