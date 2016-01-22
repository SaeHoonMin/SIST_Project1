package com.bss.client.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.GuiComponents.StyleButton;

import resources.ResourceLoader;

public class GameReadyPanel extends JPanel{
	
	Image bgImg;
	JPanel bg; 
	

	StyleButton b ;
	
	
	public GameReadyPanel(JFrame frame)
	{
		
		setLayout(null);
		
		bgImg = Toolkit.getDefaultToolkit().createImage(ResourceLoader.getResURL("images/bg.jpg"));
		bg = new JPanel(){
			public void paintComponent(Graphics g) {
				
				super.paintComponent(g);
				g.drawImage(bgImg, 0, 0,1280,1024,null);
			//	paintChildren(g);
			}
			
		};
		bg.setSize(1280,1024);
		
		
		b= new StyleButton("haha");
		b.setContentAreaFilled(true);
		b.setBounds(0, 0, 100, 50);
		
		bg.add(b);
		add(bg);
		
		
		
		setSize(1280,1024);
		
		
//		setBackground(Color.CYAN);
	}
	
	
	
}
