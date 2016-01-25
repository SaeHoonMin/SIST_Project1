package com.bss.client.GameObjects;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bss.client.scene.MainFrame;

import resources.ImageUtils;
import resources.ResLoader;

public class Ship extends JLabel implements MouseListener, MouseMotionListener{
	
	private static Ship selected;
	
	private BufferedImage bImg;
	private Image shipImg;
	private Image shipImg_scaled;
	private ImageIcon shipIcon;
	private ImageIcon shipIcon_scaled;
	
	private int headRow;
	private int headCol;
	
	private int tileSize;
	private int hitCount;
	
	private int clickedX;
	private int clickedY;
	
	private int width;
	private int height;
	
	
	
	private MouseState mState;

	//Temporary Creator.
	//Width and Height must be decided by ship type.
	//Also Ship's Rotation function must be implemented.
	public Ship()
	{
		width = 50;
		height = 100;
		
		shipImg = Toolkit.getDefaultToolkit().getImage(ResLoader.getResURL("images/Ship_Temp.png"));
		
		shipImg_scaled = shipImg.getScaledInstance(width+3,height+3,Image.SCALE_FAST);
		
		shipIcon = new ImageIcon(shipImg);
		shipIcon_scaled = new ImageIcon(shipImg_scaled);
		
		setIcon(shipIcon);
		setSize(shipImg.getWidth(this), shipImg.getHeight(this));
		
		setLocation(100, 100);

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this)
		{
			selected = this;
			
			mState = MouseState.PRESSED;
			
			setIcon(shipIcon_scaled);
			
			clickedX = e.getX();
			clickedY = e.getY();
			
			setBorder(BorderFactory.createLineBorder(new Color(255,0,0,100), 3));
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this)
		{
			mState = MouseState.RELEASED;
		
			setIcon(shipIcon);
			setBorder(null);
			
			selected=null;
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(mState == MouseState.PRESSED && e.getSource()==this)
		{
			MainFrame mInst = MainFrame.getInst();
			
			int mouseX = mInst.mouseX -clickedX;
			int mouseY = mInst.mouseY -clickedY;
		
			setLocation(mouseX,mouseY);
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static Ship getSelected()
	{
		return selected;
	}

}
