package com.bss.client.container;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.components.TurnPanel;
import com.bss.client.gameObjects.AnimName;
import com.bss.client.gameObjects.FixedLocAnimation;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Ship;
import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.ShipType;
import com.bss.client.gameObjects.Tile;
import com.bss.client.gameObjects.TileState;
import com.bss.common.AttackResult;
import com.bss.common.MatchState;

import resources.ResContainer;
import resources.ResLoader;

public class GamePlayPanel extends JPanel {
	
	private static GamePlayPanel inst;
	
	Image bgImg;
	
	Grid enemyGrid;
	Grid myGridInfo;
	Grid myGrid;
	ArrayList<Ship> ships;
	
	TurnPanel turnPanel ;
	
	private MatchState matchState;
	
	private boolean isMyTurn;
	private boolean actionAllowed;

	public static GamePlayPanel getInst()
	{
		return inst;
	}

	public GamePlayPanel(Grid grid, JFrame frame)
	{
		inst = this;
		
		isMyTurn = false;
		matchState = MatchState.START;
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		
		bgImg = toolKit.createImage(ResLoader.getResURL("images/bg.jpg"));
		
		
		setSize(frame.getWidth(),frame.getHeight());
		
		int gridX = (frame.getWidth() -  (1030)) /2;
		int gridY = frame.getHeight()/2 -500/2 - 50;
		
		turnPanel = new TurnPanel(this);
		add(turnPanel);
		setComponentZOrder(turnPanel, getComponentCount()-1);
		turnPanel.setVisible(false);
		
		enemyGrid = new Grid(gridX,gridY,this);	
		enemyGrid.setMouseListenerForTile();
		
		myGridInfo = grid;
		myGrid = new Grid(gridX+530,gridY,this);
		
		setShip();
		enemyGrid.setGridZOrder(getComponentCount()-1);
		myGrid.setGridZOrder(getComponentCount()-1);
		
//		showTileInfo();
	}
	public Grid getMyGrid()
	{
		return myGrid;
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
				Ship s = new Ship(result.type,result.ang);
				
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
			}
		}
		else
		{
			t.setIcon(ResContainer.tile_reserved_icon);
			t.setState(TileState.RESERVED);
		}
		
		GamePlayPanel.getInst().setActionAllowed(true);
		
	}
	
	public void showTileInfo()
	{
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
		
		//내턴일때 화면 마우스오버 먹히는거 버그 수정
		
		turnPanel.setVisible(false);
		actionAllowed = true;
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
		
		turnPanel.setVisible(false);
		actionAllowed = true;
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
}
