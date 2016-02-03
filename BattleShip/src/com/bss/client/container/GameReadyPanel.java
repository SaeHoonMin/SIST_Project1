package com.bss.client.container;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.StyleButton;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Ship;
import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.ShipType;
import com.bss.client.gameObjects.Tile;
import com.bss.common.BssProtocol;

import resources.ResLoader;

public class GameReadyPanel extends JPanel implements ActionListener{
	
	JLabel countDown;
	
	JLabel shipContainer;
	Image img_shipContainer;
	Image bgImg;
	
	
	Grid grid;
	
	int shipContX;
	int shipContY;
	
	double seconds;
	
	
	StyleButton readyBtn;
	StyleButton resetBtn;
	
	private MainFrame mainFrame = MainFrame.getInst();
	
	public GameReadyPanel(JFrame frame)
	{
		setOpaque(false);
		setLayout(null);
		setSize(frame.getWidth(),frame.getHeight());
		

		int gridX = mainFrame.getWidth()/2 - 500/2 - 50;
		int gridY = mainFrame.getHeight()/2 -500/2 - 50;
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
	
		bgImg = toolKit.createImage(ResLoader.getResURL("images/bg.jpg"));	
		img_shipContainer = toolKit.createImage(ResLoader.getResURL("images/ShipContainer.png"));
		
		countDown = new JLabel();
		countDown.setHorizontalAlignment(JLabel.CENTER);
		countDown.setText(String.valueOf(60));
		countDown.setForeground(new Color(255,255,255));
		countDown.setFont(new Font("Arial",Font.BOLD,30));
		countDown.setBorder(BorderFactory.createLineBorder(new Color(100,255,100,100),5));
		countDown.setBounds(1025, 160, 100, 100);
		
		shipContainer = new JLabel();
		shipContainer.setIcon(new ImageIcon(img_shipContainer));
		shipContainer.setLocation(gridX+500+10,gridY);
	
		shipContX = gridX+500+10; shipContY = gridY;
		
		shipContainer.setSize(270,360);
		
		
		
		
	//	grid = new Grid(400,256,this);	// Automatically Added to parent(frame)
		grid = new Grid(gridX,gridY,this);
		
		Ship ship = new Ship(ShipType.Frigate, ShipAngle.H, shipContX+150,shipContY+10);	
		Ship ship2 = new Ship(ShipType.Destoryer, ShipAngle.H,shipContX+100,shipContY+70);
		Ship ship3 = new Ship(ShipType.Destoryer, ShipAngle.H,shipContX+100,shipContY+130);
		Ship ship4 = new Ship(ShipType.Cruiser, ShipAngle.H, shipContX+50,shipContY+190);
		Ship ship5 = new Ship(ShipType.BattleShip,ShipAngle.H,shipContX+20,shipContY+250);
		
		
		readyBtn = new StyleButton("Ready");
		readyBtn.setBounds(gridX,gridY+505,249,100);
		readyBtn.addActionListener(this);
		resetBtn = new StyleButton("Reset");
		resetBtn.setBounds(gridX+251,gridY+505,249,100);
		
		add(shipContainer);
		add(ship);
		add(ship2);
		add(ship3);
		add(ship4);
		add(ship5);
		
		add(readyBtn);
		add(resetBtn);
		
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
		startCountDown();
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,this.getWidth(),this.getHeight(), this);
	//	paintChildren(g);
		
	}
	
	public void startCountDown()
	{
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					seconds = 60;
					
					while(true)
					{
						Thread.sleep(1000);
						seconds-=1;
						countDown.setText(String.valueOf((int)seconds));
						if(seconds<=0)
							break;
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == readyBtn)
		{
			if(grid.getLocatedShip().size()<5)
			{
				
				return;
			}
			else
			{	
				readyBtn.setPressedState();
				BssNetWork.getInst().sendMessage(BssProtocol.MATCH_READY,this);
			}
			
			//
		}
	}
	
	public void gameStart()
	{
		MainFrame.getInst().openGameStart(grid);
	}

}
