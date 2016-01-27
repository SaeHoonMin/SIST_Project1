package com.bss.client.container;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.components.StyleButton;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Ship;
import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.ShipType;
import com.bss.client.gameObjects.Tile;

import resources.ResLoader;

public class GameReadyPanel extends JPanel {
	
	JLabel countDown;
	
	JLabel shipContainer;
	Image img_shipContainer;
	Image bgImg;
	
	
	Grid grid;
	Tile[][] tiles;
	
	int shipContX;
	int shipContY;
	
	double seconds;
	
	
	StyleButton readyBtn;
	StyleButton resetBtn;
	
	public GameReadyPanel(JFrame frame)
	{
		setLayout(null);
		setSize(frame.getWidth(),frame.getHeight());
		
		
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
		shipContainer.setLocation(930,256);
		shipContX = 930; shipContY = 256;
		shipContainer.setSize(270,360);
		
		
		grid = new Grid(400,256,this);	// Automatically Added to parent(frame)
		
		Ship ship = new Ship(ShipType.Frigate, ShipAngle.H, shipContX+150,shipContY+10);	
		Ship ship2 = new Ship(ShipType.Destoryer, ShipAngle.H,shipContX+100,shipContY+70);
		Ship ship3 = new Ship(ShipType.Destoryer, ShipAngle.H,shipContX+100,shipContY+130);
		Ship ship4 = new Ship(ShipType.Cruiser, ShipAngle.H, shipContX+50,shipContY+190);
		Ship ship5 = new Ship(ShipType.BattleShip,ShipAngle.H,shipContX+20,shipContY+250);
		
		
		readyBtn = new StyleButton("Ready");
		readyBtn.setBounds(400,780,250,100);
		resetBtn = new StyleButton("Reset");
		resetBtn.setBounds(660,780,250,100);
		
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
		
		startCountDown();
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,this.getWidth(),this.getHeight(), this);
		paintChildren(g);
		setOpaque(false);
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

}
