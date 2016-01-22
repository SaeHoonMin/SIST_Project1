package com.bss.client.GameObjects;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resources.ImageUtils;
import resources.ResourceLoader;

public class Tile extends JLabel implements MouseListener{
	
	private static int width=50, height=50;
	private static int startX, startY;
	
	
	private Image img;
	private Image img_pressed;
	private ImageIcon icon_pressed;
	private ImageIcon icon;
	
	private int row, col;
	private int x, y;
	
	private Ship ship=null;
	private int shipBodyLoc = -1;		// 0 to 4
	
	public static void setStartPosition(int X, int Y){
		startX = X;
		startY = Y;
	}

	public Tile(int row, int col)
	{
		
		
		img = Toolkit.getDefaultToolkit().getImage(ResourceLoader.getResURL("images/Tile.png"));
		img_pressed = Toolkit.getDefaultToolkit().getImage(ResourceLoader.getResURL("images/Tile_Pressed.png"));
		icon = new ImageIcon(img);
		icon_pressed = new ImageIcon(img_pressed);
		
		this.row = row;
		this.col = col;
		
		
		this.x = startX + col * width;
		this.y = startY + row * height;
		
		setBounds ( x,y,width,height);
		
		setIcon(icon);
		
		
		addMouseListener(this);
	}
	

	public boolean isHit()
	{
		if(ship==null)
			return false;
		return true;
	}
	
	
	
	/*
	 * 
	 *  Event Handlers...
	 * 
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		setIcon(null);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		setIcon(icon);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		Tile t;
		
		if( e.getSource() instanceof Tile )
			t = (Tile) e.getSource();
		else
			return;

		t.setIcon(icon_pressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		Tile t;
		
		if( e.getSource() instanceof Tile )
			t = (Tile) e.getSource();
		else
			return;

		t.setIcon(icon);
	}

}
