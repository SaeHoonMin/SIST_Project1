package com.bss.client.gameObjects;

import java.awt.Color;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.bss.client.container.MainFrame;
import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import resources.BssColor;
import resources.ImageUtils;
import resources.ResContainer;
import resources.ResLoader;

public class Ship extends JLabel 
implements MouseListener, MouseMotionListener, Serializable{
	
	private static Ship selected;
	
	private boolean isLocated=false;
	
	private int startX;
	private int startY;
	
	private int clickedX;
	private int clickedY;
	
	private int width;
	private int height;
	
	private int tileSize;
	
	private ArrayList<Point> offsetPoints = new ArrayList<Point>();
	private Tile headTile;
	private Tile taileTile;

	private ShipType type;
	private ShipAngle angle;
	private MouseState mState;
	
	private ImageIcon vIcon;
	private ImageIcon hIcon;
	private ImageIcon curIcon;
	
	private Image vImage;
	private Image hImage;
	private Image curImage;
	
	private Border overBorder;
	private Border clickBorder;
	
	private static MainFrame mInst = MainFrame.getInst();
	
	private int hitPoint;
	
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
	public Tile getHeadTile() {
		return headTile;
	}
	public void setHeadTile(Tile headTile) {
		this.headTile = headTile;
	}

	
	//Temporary Creator.
	//Width and Height must be decided by ship type.
	//Also Ship's Rotation function must be implemented.
	public Ship(ShipType type, ShipAngle angle )
	{
		this.type = type;
		this.angle = angle;
		
		setShipForm();
		
		//getScaled
		setLocation(100,100);
		startX=100; startY = 100;
		
		createBorder();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Ship(ShipType type, ShipAngle angle, ShipColor sColor )
	{
		this.type = type;
		this.angle = angle;
		
		if(sColor==ShipColor.BLUE)
			setShipForm();
		else
			setShipFormRed();
		
		//getScaled
		setLocation(100,100);
		startX=100; startY = 100;
		
		createBorder();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Ship(ShipType type, ShipAngle angle, int x, int y)
	{
		this.type = type;
		this.angle = angle;
		
		setShipForm();
		
		setLocation(x,y);
		startX=x; startY = y;
		
		createBorder();
		addMouseListener(this);
		addMouseMotionListener(this);
		
		selected = null;
	}
	
	private void createBorder()
	{
		Border overOut = BorderFactory.createLineBorder(BssColor.YELLOGREEN_T1,2,true);
		Border overIn = BorderFactory.createLineBorder(BssColor.YELLOGREEN,1);
		
		Border clickOut = BorderFactory.createLineBorder(BssColor.ORANGE_T1,2,true);
		Border clickIn = BorderFactory.createLineBorder(BssColor.ORANGE,1);
		
		overBorder = 	BorderFactory.createCompoundBorder(overOut,overIn);
		clickBorder =BorderFactory.createCompoundBorder(clickOut,clickIn);
		
	
	}
	
	private void setShipForm()
	{
		if(type == ShipType.BattleShip)
		{
			tileSize = 5;
			
			vImage = ResContainer.ship5v_b;
			hImage = ResContainer.ship5h_b;
			vIcon = ResContainer.ship5v_b_icon;
			hIcon = ResContainer.ship5h_b_icon;
			
			if(angle==ShipAngle.H)
			{
				width = 250;
				height = 50;
			}
			else
			{
				width = 50;
				height = 250;
			}
		}
		else if(type==ShipType.Cruiser)
		{
			tileSize = 4;
			
			vImage = ResContainer.ship4v_b;
			hImage = ResContainer.ship4h_b;
			vIcon = ResContainer.ship4v_b_icon;
			hIcon = ResContainer.ship4h_b_icon;
			
			if(angle==ShipAngle.H)
			{
				width = 200;
				height = 50;
			}
			else
			{
				width = 50;
				height = 200;
			}
		}
		else if(type==ShipType.Destoryer1)
		{
			tileSize = 3;
			
			vImage = ResContainer.ship3v_b1;
			hImage = ResContainer.ship3h_b1;
			vIcon = ResContainer.ship3v_b1_icon;
			hIcon = ResContainer.ship3h_b1_icon;
			
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
		else if(type==ShipType.Destoryer2)
		{
			tileSize = 3;
			
			vImage = ResContainer.ship3v_b2;
			hImage = ResContainer.ship3h_b2;
			vIcon = ResContainer.ship3v_b2_icon;
			hIcon = ResContainer.ship3h_b2_icon;
			
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
			
			vImage = ResContainer.ship2v_b;
			hImage = ResContainer.ship2h_b;
			vIcon = ResContainer.ship2v_b_icon;
			hIcon = ResContainer.ship2h_b_icon;
			
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
		
		hitPoint = tileSize;
		
		setSize(width,height);
		setIcon(curIcon);
	}
	private void setShipFormRed()
	{
		if(type == ShipType.BattleShip)
		{
			tileSize = 5;
			
			vImage = ResContainer.ship5v_r;
			hImage = ResContainer.ship5h_r;
			vIcon = ResContainer.ship5v_r_icon;
			hIcon = ResContainer.ship5h_r_icon;
			
			if(angle==ShipAngle.H)
			{
				width = 250;
				height = 50;
			}
			else
			{
				width = 50;
				height = 250;
			}
		}
		else if(type==ShipType.Cruiser)
		{
			tileSize = 4;
			
			vImage = ResContainer.ship4v_r;
			hImage = ResContainer.ship4h_r;
			vIcon = ResContainer.ship4v_r_icon;
			hIcon = ResContainer.ship4h_r_icon;
			
			if(angle==ShipAngle.H)
			{
				width = 200;
				height = 50;
			}
			else
			{
				width = 50;
				height = 200;
			}
		}
		else if(type==ShipType.Destoryer1)
		{
			tileSize = 3;
			
			vImage = ResContainer.ship3v_r1;
			hImage = ResContainer.ship3h_r1;
			vIcon = ResContainer.ship3v_r1_icon;
			hIcon = ResContainer.ship3h_r1_icon;
			
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
		else if(type==ShipType.Destoryer2)
		{
			tileSize = 3;
			
			vImage = ResContainer.ship3v_r2;
			hImage = ResContainer.ship3h_r2;
			vIcon = ResContainer.ship3v_r2_icon;
			hIcon = ResContainer.ship3h_r2_icon;
			
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
			
			vImage = ResContainer.ship2v_r;
			hImage = ResContainer.ship2h_r;
			vIcon = ResContainer.ship2v_r_icon;
			hIcon = ResContainer.ship2h_r_icon;
			
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
		
		hitPoint = tileSize;
		
		setSize(width,height);
		setIcon(curIcon);
	}
	public int attacked()
	{
		hitPoint--;
		return hitPoint;
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
		if(e.getClickCount()==2)
		{
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		setBorder(overBorder);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		setBorder(null);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this)
		{
			/* 순서 절대 주의. */
			
			clickedX = e.getX();
			clickedY = e.getY();
			
			if(SwingUtilities.isRightMouseButton(e) && !SwingUtilities.isLeftMouseButton(e))
			{	
				int temp = clickedX;
				clickedX = clickedY;
				clickedY = temp;
				
				int mouseX = mInst.mouseX -clickedX;
				int mouseY = mInst.mouseY -clickedY;

				if(taileTile !=null && headTile != null)
					headTile.getGrid().unsetReservedTiles(this);
				
				rotateShip();

				
				setLocation(mouseX,mouseY);
			}
			
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
			setBorder(clickBorder);
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this)
		{
			
			mState = MouseState.RELEASED;
		
			setIcon(curIcon);
			
			int x = mInst.mouseX;
			int y = mInst.mouseY;
			
			if(x >= getX() && y >=getY() && x<getX()+width && y<getY()+height)
				setBorder(overBorder);
			else
				setBorder(null);

			offsetPoints.clear();
			
			selected=null;
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if((mState == MouseState.PRESSED || mState == MouseState.DRAGGING )&& e.getSource()==this)
		{
			mState = MouseState.DRAGGING;
			
			int mouseX = mInst.mouseX -clickedX;
			int mouseY = mInst.mouseY -clickedY;
		
			setLocation(mouseX,mouseY);
		}
	}
	
	public void returnToSlot()
	{
		new Thread(new Runnable(){
			
			int dx, dy;
			int signx, signy;
			int ax, ay;
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				dx = Math.abs(startX - getX());
				dy = Math.abs(startY - getY());
				
				if(startX-getX() < 0)
					signx = -1;
				else
					signx = 1;
				if(startY-getY() < 0)
					signy = -1;
				else
					signx = 1;
				
				ax = 1+ dx/10;
				ay = 1+ dy/10;
				
				while(true)
				{
					if (angle == ShipAngle.V)
						rotateShip();
				
					
					
					if( dy <= 0 && dx <=0)
					{
						setLocation(startX,startY);
						break;
					}
					
					setLocation(getX()+(signx*ax),getY()+(signy+ay));
					
					dx-=ax;
					dy-=ay;
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public Tile getTaileTile() {
		return taileTile;
	}
	public void setTaileTile(Tile taileTile) {
		this.taileTile = taileTile;
	}
}
