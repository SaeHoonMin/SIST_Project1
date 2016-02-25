package com.bss.client.container;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.CountDown;
import com.bss.client.components.MessageDialog;
import com.bss.client.components.StyleButton;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Ship;
import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.ShipType;
import com.bss.client.gameObjects.Tile;
import com.bss.common.BssProtocol;

import resources.BssColor;
import resources.ResContainer;
import resources.ResLoader;

public class GameReadyPanel extends JPanel implements ActionListener{
	
	final static int READY_COUNTDOWN = 60;
	
	JLabel countDown;
	
	JLabel shipContainer;
	Image img_shipContainer;
	Image bgImg;
	
	Grid grid;
	
	double seconds;
	
	ArrayList<Ship> ships = new ArrayList<Ship>();
	
	StyleButton readyBtn;
	StyleButton resetBtn;
	StyleButton autoBtn;
	
	Image warpImg = null;
	JPanel warpPanel;
	private MainFrame mainFrame = MainFrame.getInst();
	
	public GameReadyPanel(JFrame frame)
	{
		setOpaque(false);
		setLayout(null);
		setSize(frame.getWidth(),frame.getHeight());

		int gridX = mainFrame.getWidth()/2 - 500/2 - 50;
		int gridY = mainFrame.getHeight()/2 -500/2 - 50;
		
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
	
		bgImg = toolKit.createImage(ResLoader.getResURL("images/bg3.jpg"));	
		img_shipContainer = toolKit.createImage(ResLoader.getResURL("images/ShipContainer.png"));
		
		
		
		
		warpImg = ResContainer.img_warp;
		warpPanel = new JPanel(){
    		@Override
    		protected void paintComponent(Graphics g) {
				if(warpImg!=null)
    				g.drawImage(warpImg, 0, 0,getWidth(),getHeight(), this);
				else
				{
					  g.setColor( getBackground() );
    			      g.fillRect(0, 0, getWidth(), getHeight());
				}
    		}
    	};
    	warpPanel.setBackground(new Color(0,0,0,0));
    	warpPanel.setOpaque(false);
    	warpPanel.setSize(getSize());
    	add(warpPanel);
    	Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				
				int i = 210;
				int r, g, b, a=250;
				r = BssColor.WARP_BG.getRed();
				g = BssColor.WARP_BG.getGreen();
				b = BssColor.WARP_BG.getBlue();

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				warpImg = null;
				warpPanel.repaint();
				while (true) {
					warpPanel.setBackground(new Color(r,g,b,a));
					a -=5;
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (a<0)
						break;
				}
				remove(warpPanel);
				startCountDown();
			}
		});
		t.start();
		
		
		countDown = new CountDown();
		countDown.setBounds(gridX-105, gridY, 100,100);
		
		shipContainer = new JLabel();
		shipContainer.setIcon(new ImageIcon(img_shipContainer));
		shipContainer.setBackground(BssColor.BLACK_T1);
	
		
		shipContainer.setSize(250,260);
		
	//	grid = new Grid(400,256,this);	// Automatically Added to parent(frame)
		grid = new Grid(gridX,gridY,this);
		

		
		readyBtn = new StyleButton("Ready");
		readyBtn.setBounds(gridX,gridY+505,249,100);
		readyBtn.addActionListener(this);
		resetBtn = new StyleButton("Reset");
		resetBtn.setBounds(gridX+251,gridY+505,249,100);
		resetBtn.addActionListener(this);
		autoBtn = new StyleButton("Auto");
		autoBtn.setBounds(gridX+502,gridY+505,249,100);
		autoBtn.addActionListener(this);
		
		
		shipContainer.setLocation(autoBtn.getX(),autoBtn.getY()-5-shipContainer.getHeight());
		
		Ship ship = new Ship(ShipType.Frigate, ShipAngle.H, shipContainer.getX()+150,shipContainer.getY()+5);	
		Ship ship2 = new Ship(ShipType.Destoryer1, ShipAngle.H,shipContainer.getX()+100,shipContainer.getY()+55);
		Ship ship3 = new Ship(ShipType.Destoryer2, ShipAngle.H,shipContainer.getX()+100,shipContainer.getY()+105);
		Ship ship4 = new Ship(ShipType.Cruiser, ShipAngle.H, shipContainer.getX()+45,shipContainer.getY()+155);
		Ship ship5 = new Ship(ShipType.BattleShip,ShipAngle.H,shipContainer.getX(),shipContainer.getY()+205);
		
		ships.add(ship);
		ships.add(ship2);
		ships.add(ship3);
		ships.add(ship4);
		ships.add(ship5);
		
		add(shipContainer);
		add(ship);
		add(ship2);
		add(ship3);
		add(ship4);
		add(ship5);
		
		add(readyBtn);
		add(resetBtn);
		add(autoBtn);
		add(countDown);
		
		/*
		 * Set zOrders of Components
		 */
		setComponentZOrder(ship , 1);
		setComponentZOrder(ship2 , 2);
		setComponentZOrder(ship3 , 3);
		setComponentZOrder(ship4 , 4);
		setComponentZOrder(ship5 , 5);
		grid.setGridZOrder(10);
		setComponentZOrder(shipContainer,10);
		
		grid.startLocateThread();
		grid.setGridEmpty();
		
		BssNetWork.getInst().setReadyRoom(this);
		
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,1280,900, this);
	//	paintChildren(g);
		
	}
	
	public void startCountDown()
	{
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					seconds = READY_COUNTDOWN;
					
					while(MainFrame.getPanelState() == PanelState.GAMEREADY)
					{
						Thread.sleep(1000);
						seconds-=1;
						countDown.setText(String.valueOf((int)seconds));
						if(seconds<=0)
						{
							if(grid.getLocatedShip().size()<5)
							{
								grid.randomLocate(ships);
							}
							break;
						}
					}
					
					ready();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	
	public void opponentReady()
	{
		
	}
	
	public void ready()
	{
		for(int i=0; i<ships.size();i++)
		{
			ships.get(i).removeMouseListener(ships.get(i));
			ships.get(i).removeMouseMotionListener(ships.get(i));
		}
		readyBtn.setEnabled(false);
		autoBtn.setEnabled(false);
		resetBtn.setEnabled(false);
		
		BssNetWork.getInst().sendMessage(BssProtocol.MATCH_READY,this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == readyBtn)
		{
			if(grid.getLocatedShip().size()<5)
				return;
			else
				ready();
		}
		else if(e.getSource()==resetBtn)
		{
			grid.resetGrid();
		}
		else if(e.getSource()==autoBtn)
		{
			grid.randomLocate(ships);
		}
		
	}
	
	public void gameStart()
	{
		MainFrame.getInst().openGameStart(grid);
	}
	
	public void out()
	{
		MessageDialog.Show("Your Opponent Has Left");
		MainFrame.getInst().openWaitRoom();
	}

}
