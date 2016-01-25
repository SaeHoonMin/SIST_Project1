package com.bss.client.GameObjects;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bss.client.scene.MainFrame;

import resources.ResContainer;
import resources.ResLoader;

public class Tile extends JLabel implements MouseListener{
	
	private static int width=50, height=50;
	
	private ImageIcon icon;
	private ImageIcon icon_pressed;
	private ImageIcon icon_over;
	
	private boolean pressState;
	private Tile beforeObj;
	
	
	private int row, col;			// Rows and Cols in 10 * 10 Grid
	private int x, y;				// Start Location of this object.
	

	int mouseX;
	int mouseY;
	
	private Ship ship=null;
	private int shipBodyLoc = -1;		// 0 to 4
	
	public Tile(int row, int col,int startX,int startY)
	{
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		icon = new ImageIcon(ResContainer.tile);
		icon_pressed = new ImageIcon(ResContainer.tile_pressed);
		icon_over = new ImageIcon(ResContainer.tile_over);
		
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
	
	public int getX()
	{ return x; }
	public int getY()
	{ return y; }
	public int getWidth()
	{ return width ; }
	public int getHeight()
	{ return height ; }
	
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
		
		setIcon(icon_over);
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
	
	//	System.out.printf("(%d,%d,%d,%d),(%d,%d)\n",x,y,x+width,y+height,MainFrame.getInst().mouseX,MainFrame.getInst().mouseY);
		
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
		
		mouseX = MainFrame.getInst().mouseX;
		mouseY = MainFrame.getInst().mouseY;
		
		if( mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height)
			setIcon(icon_over);
		else
		{
			
			setIcon(icon);
		}
	}

}
