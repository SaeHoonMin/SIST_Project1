package com.bss.client.GameObjects;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bss.client.scene.MainFrame;
import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import resources.ImageUtils;
import resources.ResContainer;
import resources.ResLoader;

public class Ship extends JLabel implements MouseListener, MouseMotionListener{
	
	private int id;

	private static Ship selected;
	
	private boolean isLocated=false;
	
	private int clickedX;
	private int clickedY;
	
	private int width;
	private int height;
	
	private int tileSize;
	
	
	private ArrayList<Point> offsetPoints = new ArrayList<Point>();
	private Tile headTile;

	private ShipType type;
	private ShipAngle angle;
	private MouseState mState;
	
	private ImageIcon vIcon;
	private ImageIcon hIcon;
	private ImageIcon curIcon;
	
	private Image vImage;
	private Image hImage;
	private Image curImage;
	
	public MouseState getMouseState()
	{
		return mState;
	}
	public ShipAngle getAngle()
	{
		return angle;
	}
	public ShipType getType()
	{
		return type;
	}
	public ArrayList<Point> getOffsetPoints()
	{
		return offsetPoints;
	}
	public int getTileSize()
	{
		return tileSize;
	}
	public static Ship getSelected()
	{
		return selected;
	}
	public boolean isLocated() {
		return isLocated;
	}
	public void setLocated(boolean isLocated) 
	{
		this.isLocated = isLocated;
	}
	
	//Temporary Creator.
	//Width and Height must be decided by ship type.
	//Also Ship's Rotation function must be implemented.
	public Ship(ShipType type, ShipAngle angle, int id)
	{
		this.id = id;
		this.type = type;
		this.angle = angle;
		
		setShipForm();
		
		//getScaled
		

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private void setShipForm()
	{
		if(type == ShipType.BattleShip)
		{
			tileSize = 5;
		}
		else if(type==ShipType.Cruiser)
		{
			tileSize = 4;
		}
		else if(type==ShipType.Destoryer)
		{
			tileSize = 3;
			
			vImage = ResContainer.ship3v;
			hImage = ResContainer.ship3h;
			vIcon = ResContainer.ship3v_icon;
			hIcon = ResContainer.ship3h_icon;
			
			if(angle==ShipAngle.H)
			{
				width = 150;
				height = 50;
			}
			else
			{
				width = 50;
				height = 150;
			}
		}
		else if(type==ShipType.Frigate)
		{
			tileSize = 2;
			
			vImage = ResContainer.ship2v;
			hImage = ResContainer.ship2h;
			vIcon = ResContainer.ship2v_icon;
			hIcon = ResContainer.ship2h_icon;
			
			if(angle==ShipAngle.H)
			{
				width = 100;
				height = 50;
			}
			else
			{
				width = 50;
				height = 100;
			}
			
		}
		
		if(angle == ShipAngle.H)
		{
			curIcon = hIcon;
			curImage = hImage;
		}
		else
		{
			curIcon = vIcon;
			curImage = vImage;
		}
		
		setSize(width,height);
		setIcon(curIcon);
	}
	
	
	public void rotateShip()
	{
		if(angle == ShipAngle.H)
		{
			angle = ShipAngle.V;
			curIcon = vIcon;
			curImage = vImage;
			int t = width;
			width = height;
			height = t;
			
			setSize(width,height);
		}
		else if(angle==ShipAngle.V)
		{
			angle = ShipAngle.H;
			curIcon = hIcon;
			curImage = vImage;
			int t = width;
			width = height;
			height = t;
			
			setSize(width,height);
		}
		
		setIcon(curIcon);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isLocated)
			rotateShip();		//Temporary
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
	public synchronized void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this)
		{
			
			/* 순서 절대 주의. */
			
			clickedX = e.getX();
			clickedY = e.getY();
			
			offsetPoints.clear();
			if (angle == ShipAngle.H) {
				for (int i = -1; clickedX + i * 50 > 0; i--) {
					offsetPoints.add(new Point(i * 50, 0));
				}
				for (int i = 1; clickedX + i * 50 < width; i++) {
					offsetPoints.add(new Point(i * 50, 0));
				}
			} else if (angle == ShipAngle.V) {
				for (int i = -1; clickedY + i * 50 > 0; i--) {
					offsetPoints.add(new Point(0, i * 50));
				}
				for (int i = 1; clickedY + i * 50 < height; i++) {
					offsetPoints.add(new Point(0, i * 50));
				}
			}
			
			
			mState = MouseState.PRESSED;
			selected = this;
			
			setBorder(BorderFactory.createLineBorder(new Color(255,0,0,100), 3));
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this)
		{
			mState = MouseState.RELEASED;
		
			setIcon(curIcon);
			setBorder(null);

			System.out.println("===============");
			for(Point p : offsetPoints)
				System.out.println(p);
			offsetPoints.clear();
			
			selected=null;
		}
	}


	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if((mState == MouseState.PRESSED || mState == MouseState.DRAGGING )&& e.getSource()==this)
		{
			mState = MouseState.DRAGGING;
			
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
	
	
	public Tile getHeadTile() {
		return headTile;
	}
	public void setHeadTile(Tile headTile) {
		this.headTile = headTile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	


}
