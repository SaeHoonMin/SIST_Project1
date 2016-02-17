package com.bss.client.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.bss.client.container.MainFrame;
import com.bss.client.container.PanelState;
import com.bss.client.gameObjects.Grid;

import resources.BssColor;

public class GridHighlight extends JPanel implements Runnable{
	
	private static int width = 512, height = 512;
	
	private JPanel parent;
	private Thread thread;
	
	private int targetX;
	private int targetY;
	
	private static ArrayList<Border> borders = new ArrayList<Border>();
	
	public GridHighlight(JPanel parent)
	{
		setOpaque(false);
		this.parent = parent;
		thread = new Thread(this);
		setSize(width,height);
		
		setBorder(BorderFactory.createLineBorder(BssColor.TURQUOISE_MID_T2, 3));
		
		int r ,g , b , a = 200;
		r =  BssColor.TURQUOISE_MID_T1.getRed()-50;
		g =  BssColor.TURQUOISE_MID_T1.getGreen()-50;
		b =  BssColor.TURQUOISE_MID_T1.getBlue()-50;
		for(int i = 0 ; i<100 ; i++)
		{
			Border border = BorderFactory.createLineBorder(new Color(r,g,b,a),4,true);
			borders.add(border);
			r = (r+2);
			g = (g+2);
			b = (b+2);
			if(r>255 || g>255 || b>255)
			{
				break;
			}
		} 
		new Thread(new Runnable(){

			int i =0;
			int phase = 1;
			@Override
			public void run() {
				try{
					while(MainFrame.getPanelState() == PanelState.GAMEPLAY)
					{
						setBorder(borders.get(i));
						i+=phase;
						if(i==borders.size())
						{	phase=-1; i--; }
						else if(i==0)
						{
							phase=1;
						}
						Thread.sleep(12);
					}
				}catch (Exception e)
				{
					
				}
			}
		}).start();
	}
	
	public void moveToGrid(Grid g)
	{
		targetX=g.getStartX()-6;
		targetY=g.getStartY()-6;
		
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		
		int dx = (targetX > getX())? 10:-10;
		int dy = (targetY > getY())? 10:-10;
		
		if(targetX == getX())
			return;
		
		if( getY() == targetY)
			dy = 0;
		
		
		int time = 5;
		
		
		// TODO Auto-generated method stub
		try{
			while(true)
			{
				setLocation(getX()+dx,getY()+dy);
				
				if( dx > 0 && getX() >= targetX)
				{
					setLocation(targetX,targetY);
					return;
				}
				else if( dx < 0 && getX()<= targetX)
				{
					setLocation(targetX,targetY);
					return;
				}
				
				Thread.sleep(time);
			}
		}catch(Exception e)
		{
			
		}
	}
	

}
