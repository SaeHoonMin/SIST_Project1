package com.bss.client.GameObjects;

import javax.swing.JPanel;

import com.bss.client.scene.MainFrame;

public class Grid implements Runnable{
	
	private int startX, startY;
	
	private Tile[][] tiles;
	private JPanel panel;
	
	public Grid(int startX, int startY, JPanel panel)
	{
		this.startX = startX;
		this.startY = startY;
		this.panel = panel;
		
		tiles = new Tile[10][10];
		
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tiles[i][j] = new Tile(i,j,startX,startY);
				panel.add(tiles[i][j]);
			}
		}
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setGridZOrder(int order)
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				panel.setComponentZOrder(tiles[i][j], order);
			}
		}
	}
	
	public Tile whichTile(int x, int y)
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(isInside(tiles[i][j],x,y))
					return tiles[i][j];
			}
		}
		return null;
	}
	
	private boolean isInside(Tile t, int x, int y)
	{
		if( t.getX() <= x && t.getY() <= y && t.getX()+t.getWidth() > x && t.getY()+t.getHeight() > y)
			return true;
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		MainFrame inst = MainFrame.getInst();
		int x , y;
		Tile t;
		while(true)
		{
			x = inst.mouseX;
			y = inst.mouseY;
			
			t = whichTile(x,y);
			if(t!=null && Ship.getSelected()!=null)
				t.setIcon(null);
				
	
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
