package com.bss.client.scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.GameObjects.Tile;

import resources.ResourceLoader;

public class GamePlayPanel extends JPanel {
	
	Image bgImg;
	
	Tile [][] enemyTiles;
	Tile [][] myTiles;
	
	public GamePlayPanel(JFrame frame)
	{
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		
		bgImg = toolKit.createImage(ResourceLoader.getResURL("images/bg.jpg"));

		
		setSize(1280,frame.getHeight());
		
		
		
//		setBackground(Color.CYAN);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,this.getWidth(),this.getHeight(), this);
		paintChildren(g);
		setOpaque(false);
	}	

}
