package com.bss.client.container;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.Ship;
import com.bss.client.gameObjects.ShipAngle;
import com.bss.client.gameObjects.Tile;

import resources.ResLoader;

public class GamePlayPanel extends JPanel {
	
	private static GamePlayPanel inst;
	
	Image bgImg;
	
	Grid enemyGrid;
	Grid myGridInfo;
	Grid myGrid;
	ArrayList<Ship> ships;
	
	public static GamePlayPanel getInst()
	{
		return inst;
	}

	public GamePlayPanel(Grid grid, JFrame frame)
	{
		inst = this;
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		
		bgImg = toolKit.createImage(ResLoader.getResURL("images/bg.jpg"));
		
		
		setSize(1280,frame.getHeight());
		
		enemyGrid = new Grid(90,220,this);	
		enemyGrid.setMouseListenerForTile();
		
		myGridInfo = grid;
		myGrid = new Grid(700,220,this);
		
		ShowTileInfo();
		
		
		setShip();
		enemyGrid.setGridZOrder(getComponentCount()-1);
		myGrid.setGridZOrder(getComponentCount()-1);
		
		for(int i=0;i<ships.size();i++)
			System.out.println("ship " + getComponentZOrder(ships.get(i)));
		System.out.println("mygrid "+ getComponentZOrder(myGrid.getTileByRC(0, 0)));
		
//		setBackground(Color.CYAN);
	}
	public Grid getMyGrid()
	{
		return myGridInfo;
	}
	
	public void ShowTileInfo()
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				Tile t = myGridInfo.getTileByRC(i, j);
				if(t.getLocatedShip()!=null)
				{
					System.out.print("O ");
				}
				else
					System.out.print("X ");
			}
			System.out.println();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0,this.getWidth(),this.getHeight(), this);
		paintChildren(g);
		setOpaque(false);
	}	
	
	private void setShip()
	{
		ships = myGridInfo.getLocatedShip();
		for(int i=0; i<ships.size() ;i++)
		{
			Ship s = ships.get(i);
			
			int row = s.getHeadTile().getRow();
			int col = s.getHeadTile().getCol();
			ShipAngle angle = s.getAngle();
			
			Tile myTile = myGrid.getTileByRC(row, col);
			
			Ship ship = new Ship(s.getType(),angle);
			ship.removeMouseListener(ship);
			ship.removeMouseMotionListener(ship);
			setComponentZOrder(ship, getComponentCount()-1);
			add(ship);
			ship.setLocation(myTile.getX(), myTile.getY());
			
			//배 정보 배치
		}
	}
}
