package com.bss.client.gameObjects;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.bss.client.container.MainFrame;

import resources.ResContainer;

public class Grid implements Runnable, Serializable{
	
	private int startX, startY;
	
	private Tile[][] tiles;
	
	private ArrayList<Ship> locatedShip = new ArrayList<Ship>();
	
	private JPanel panel;
	
	
	public ArrayList<Ship> getLocatedShip()
	{
		return locatedShip;
	}
	public Tile getTileByRC(int row, int col)
	{
		return tiles[row][col];
	}
	
	public Grid(int startX, int startY, JPanel panel)
	{
		this.startX=startX;
		this.startY=startY;
		this.panel = panel;
		
		tiles = new Tile[10][10];
		
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tiles[i][j] = new Tile(i,j,this);
				panel.add(tiles[i][j]);
			}
		}
		
	}
	
	public void startLocateThread()
	{
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
				{
					return tiles[i][j];
				}
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
	public synchronized void run() {
		// TODO Auto-generated method stub
		MainFrame inst = MainFrame.getInst();
		int x , y;
		int count=0;
		Tile t = null;
		Tile t_before = null;
		
		Ship ship;
		Ship ship_before =null;
		ArrayList<Point> opp;
		ArrayList<Tile> reservedTiles = new ArrayList<Tile>();
		
		while(true)
		{
			//버그 : 배 전부 없앨때? reserved 잔상
			// 발생빈도 낮음

			x = inst.mouseX;
			y = inst.mouseY;
			
			t_before = t;

			t = whichTile(x, y);
			
			ship = Ship.getSelected();

			if (ship != null)
				ship_before = ship;

			
			// 현재 선택된 배는 없지만(마우스클릭 뗀 상태) 이전에 선택했던 배가 있고, 이전 루프에서
			// 타일에 배가 들어간다고 판정되면  ==> 배를 위치시킨다.
			if(ship==null && ship_before!=null && count==ship_before.getTileSize() )
			{
				Tile minTile = reservedTiles.get(0);
				Tile maxTile = reservedTiles.get(0);
				for(int i=0; i< reservedTiles.size(); i++)
				{	
					reservedTiles.get(i).setIcon(ResContainer.tile_located_icon);
					reservedTiles.get(i).setState(TileState.LOCATED);
					reservedTiles.get(i).setLocatedShip(ship_before, i);
					
					
					if(minTile.getX()>reservedTiles.get(i).getX() ||
							minTile.getY()>reservedTiles.get(i).getY())
						minTile = reservedTiles.get(i);
					if(maxTile.getX()<reservedTiles.get(i).getX() ||
							maxTile.getY()<reservedTiles.get(i).getY())
						maxTile = reservedTiles.get(i);
				}
				
				
				ship_before.setLocation(minTile.getX(), minTile.getY());
				ship_before.setLocated(true);
				ship_before.setHeadTile(minTile);
				ship_before.setTaileTile(maxTile);

				setReservedTiles(ship_before);
				
				locatedShip.add(ship_before);
				
				ship_before=null;
				
				reservedTiles.clear();
				continue;
			}
			else if(ship==null && ship_before != null)
			{
				//버그 예상..
				for(int i=0;i<locatedShip.size();i++)
				{
					if(locatedShip.get(i)==ship_before)
					{
						unsetReservedTiles(ship_before);
						locatedShip.remove(i);
						break;
					}
				}
				
				ship_before.returnToSlot();
				ship_before = null;
				
			}
			
			// 배를 선택한 상태이고 타일 안에도 마우스가 위치한다면
			if(t!=null && ship!=null )
			{
				boolean same = false;
				count=1;
				
				opp = ship.getOffsetPoints();
				Tile addingTile;
				TileState tState = t.getState();
				
				if(t_before != t)
				{
					clearReservedTiles(reservedTiles);
				}
				if(tState == TileState.LOCATED && isLocatedShip(ship)
						&& t.getLocatedShip()==ship)
				{
					unsetReservedTiles(ship);
					removeLocatedShip(ship);
				}
				
				
				for (int i = 0; i < opp.size(); i++) {
					Point p = opp.get(i);
					addingTile = whichTile(x + p.x, y + p.y);
					if (addingTile != null && addingTile.getState() == TileState.EMPTY) {
						for (int j = 0; j < reservedTiles.size(); j++) {
							if ((addingTile.getRow() == reservedTiles.get(j).getRow())
									&& (addingTile.getCol() == reservedTiles.get(j).getCol())) {
								same = true;
								break;
							}
						}
						if (same == false) {
							reservedTiles.add(addingTile);
						}
						count++;
					}

				}

				if (tState != TileState.EMPTY)
					count--;

				same = false;

				if (count == ship.getTileSize()) {
					for (int j = 0; j < reservedTiles.size(); j++) {
						if ((t.getRow() == reservedTiles.get(j).getRow())
								&& (t.getCol() == reservedTiles.get(j).getCol())) {
							same = true;
							break;
						}
					}
					if (same == false) {
						reservedTiles.add(t);
					}
					// 소트 해야함...
					for (int i = 0; i < count; i++) {
						Tile tile = reservedTiles.get(i);
						tile.setIcon(ResContainer.tile_located_icon);
						// tile.setState(TileState.LOCATED);
						// tile.setLocatedShip(ship, i);
					}
				}

			}
			else if(t==null && ship !=null)	
			{
				/*********************************
				 *  배는 선택되었지만 타일은 매치하지 않으면 
				 *  배치된 배 중에서 어떤 배인지를 찾고 되돌려보낸다.
				 * *******************************/
				
				for(int i=0; i<reservedTiles.size() ; i++)
				{
					Ship s = reservedTiles.get(i).getLocatedShip();
					if(s==ship)
					{
						//locateInfo.remove(i);
						s.setLocated(false);
						s.setHeadTile(null);	
					}
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
	
	private boolean isLocatedShip(Ship s)
	{
		for(int i=0;i<locatedShip.size();i++)
		{
			if(locatedShip.get(i)==s)
				return true;
		}
		return false;
	}
	private void removeLocatedShip(Ship s)
	{
		for(int i =0; i<locatedShip.size();i++)
		{
			if(locatedShip.get(i)==s)
				locatedShip.remove(i);
		}
	}
	
	private void setReservedTiles(Ship s)
	{
		Tile minTile = s.getHeadTile();
		Tile maxTile = s.getTaileTile();
		
		int il = maxTile.getRow()+1;
		int jl = maxTile.getCol()+1;
		for( int i = minTile.getRow()-1  ; i <= il ; i++)
		{
			for (int j = minTile.getCol()-1 ; j <= jl ; j++)
			{
				if( i>=0 && i<10 && j>=0 && j<10)
				{
					if(tiles[i][j].getState()!=TileState.LOCATED)
					{
						tiles[i][j].setState(TileState.RESERVED);
						tiles[i][j].setIcon(ResContainer.tile_reserved_icon);
					}
					
				}
			}
		}
	}
	private void unsetReservedTiles(Ship s)
	{
		Tile minTile = s.getHeadTile();
		if(minTile==null)
			return;
		
		int il = s.getTaileTile().getRow()+1;
		int jl = s.getTaileTile().getCol()+1;
		
		for( int i = minTile.getRow()-1 ; i <= il ; i++)
		{
			for (int  j = minTile.getCol()-1 ; j <= jl ; j++)
			{
				if( i>=0 && i<10 && j>=0 && j<10)
				{
					tiles[i][j].setState(TileState.EMPTY);
					tiles[i][j].setIcon(ResContainer.tile_icon);
				}
			}
		}
		
		for(int h = 0; h<locatedShip.size(); h++)
		{
			Ship ship = locatedShip.get(h);
			if(ship==s)
				continue;
			
			Tile mint = ship.getHeadTile();
			if(mint==null)
				return;
			
			Tile maxt = ship.getTaileTile();
			
			int ill = maxt.getRow()+1;
			int jll = maxt.getCol()+1;
			for( int i = mint.getRow()-1 ; i <= ill ; i++)
			{
				for (int  j = mint.getCol()-1 ; j <= jll ; j++)
				{
					if( i>=0 && i<10 && j>=0 && j<10)
					{
						if(tiles[i][j].getState()!=TileState.LOCATED)
						{
							tiles[i][j].setState(TileState.RESERVED);
							tiles[i][j].setIcon(ResContainer.tile_reserved_icon);
						}
					}
				}
			}
			
		}
	}
	
	private void clearReservedTiles(ArrayList<Tile> reserved)
	{
		
		for(int i=0; i< reserved.size(); i++)
		{
			Tile tile = reserved.get(i);
			TileState ts = tile.getState();
			if(ts != TileState.RESERVED && ts != TileState.LOCATED)
			{
				tile.setIcon(ResContainer.tile_icon);
				tile.setLocatedShip(null, -1);
			}
		}
		reserved.clear();
	}

	public int getStartY() {
		return startY;
	}

	public int getStartX() {
		return startX;
	}

	
}
