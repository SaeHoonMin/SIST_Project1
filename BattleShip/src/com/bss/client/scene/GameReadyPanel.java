package com.bss.client.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.GameObjects.Tile;
import com.bss.client.GuiComponents.StyleButton;

import resources.ResourceLoader;

public class GameReadyPanel extends JPanel{
	
	Image bgImg;
	
	Tile[][] tiles;
	
	public GameReadyPanel(JFrame frame)
	{
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		
		bgImg = toolKit.createImage(ResourceLoader.getResURL("images/bg.jpg"));

		
		setSize(1280,frame.getHeight());
		
		int tStartX = toolKit.getScreenSize().width /3;
		int tStartY = toolKit.getScreenSize().height/3;
		
		
		Tile.setStartPosition(400,256);
		
		tiles = new Tile[10][10];
		
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tiles[i][j] = new Tile(i,j);
				
				add(tiles[i][j]);
			}
		}
		
		
//		setBackground(Color.CYAN);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,this.getWidth(),this.getHeight(), this);
		paintChildren(g);
		setOpaque(false);
	}	
	
}
