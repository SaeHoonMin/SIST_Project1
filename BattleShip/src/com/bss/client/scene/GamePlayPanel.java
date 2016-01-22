package com.bss.client.scene;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class GamePlayPanel extends JPanel {
	
	Image bgImg;
	
	
	
	public void paintComponent(Graphics g) {
		
	//	g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
		setOpaque(false);
		
	}	

}
