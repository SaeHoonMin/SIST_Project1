package com.bss.client.gameObjects;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.bss.client.container.MainFrame;

import resources.ResContainer;

public class Grid implements Runnable{
	
	private int startX, startY;
	
	private Tile[][] tiles;
	private ArrayList<Tile> locatedTiles = new ArrayList<Tile>();
	private ArrayList<Ship> locatedShips = new ArrayList<Ship>();
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
		// guess this can be optimized?
		// by using vector & hashmap ?
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
		if( t.getX() <= x && t.getY() <= y 
			&& t.getX()+t.getWidth() > x && t.getY()+t.getHeight() > y)
			return true;
		return false;
	}
	
	/*  
	 * Cosider Refactoring ..
	 * 
	 * complexity too high...
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		MainFrame inst = MainFrame.getInst();
		int x , y;
		int count=0;
		Tile t;
		
		Ship ship;
		Ship ship_before =null;
		ArrayList<Point> opp;
		ArrayList<Tile> reservedTiles = new ArrayList<Tile>();
		
		while(true)
		{

			x = inst.mouseX;
			y = inst.mouseY;

			t = whichTile(x, y);

			ship = Ship.getSelected();

			if (ship != null)
				ship_before = ship;

			
			// ���� ���õ� ��� ������(���콺Ŭ�� �� ����) ������ �����ߴ� �谡 �ְ�, ���� ��������
			// Ÿ�Ͽ� �谡 ���ٰ� �����Ǹ�  ==> �踦 ��ġ��Ų��.
			if(ship==null && ship_before!=null && count==ship_before.getTileSize() )
			{
				Tile minTile = reservedTiles.get(0);
				for(int i=0; i< reservedTiles.size(); i++)
				{
					/*************** *******************/
					locatedTiles.add(reservedTiles.get(i));
					
					reservedTiles.get(i).setIcon(ResContainer.tile_valid_icon);
					if(minTile.getX()>reservedTiles.get(i).getX() ||
							minTile.getY()>reservedTiles.get(i).getY())
						minTile = reservedTiles.get(i);
				}
				ship_before.setLocation(minTile.getX(), minTile.getY());
				ship_before.setLocated(true);
				ship_before.setHeadTile(minTile);
				locatedShips.add(ship_before);
				
				ship_before=null;
				
				reservedTiles.clear();
				continue;
			}
			
			// �踦 ������ �����̰� Ÿ�� �ȿ��� ���콺�� ��ġ�Ѵٸ�
			if(t!=null && ship!=null )
			{
				
				count=1;
				
				opp = ship.getOffsetPoints();
				Tile addingTile;
				
				clearReservedTiles(reservedTiles);
				
				for(int i=0; i<opp.size();i++)
				{
					Point p = opp.get(i);
					addingTile = whichTile( x + p.x  , y + p.y );
					if(addingTile != null)
					{
						reservedTiles.add(addingTile);
						count++;
					}	
				}
				
				if(count==ship.getTileSize())
				{
					reservedTiles.add(t);
					for(int i=0;i<count;i++)
					{
						reservedTiles.get(i).setIcon(ResContainer.tile_valid_icon);
					}
				}
				else
				{
					clearReservedTiles(reservedTiles);
				}
				
			}
			else if(t==null)	
			{
				/*********************************
				 *  ��� ���õǾ����� Ÿ���� ��ġ���� ������ 
				 *  ��ġ�� �� �߿��� � �������� ã�� �ǵ���������.
				 * *******************************/
				for(int i=0; i<locatedShips.size() ; i++)
				{
					Ship s = locatedShips.get(i);
					if(s==ship)
					{
						locatedShips.remove(i);
						s.setLocated(false);
						s.setHeadTile(null);			
					}
					s.returnToSlot();
				}
				count=1;
				
				clearReservedTiles(reservedTiles);
			}
			
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	//Thread ends.
	
	
	private void clearReservedTiles(ArrayList<Tile> reserved)
	{
		for(int i=0; i< reserved.size(); i++)
		{
			reserved.get(i).setIcon(ResContainer.tile_icon);
		}
		reserved.clear();
	}
	
}
