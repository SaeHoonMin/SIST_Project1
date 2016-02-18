package com.bss.client.gameObjects;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bss.client.BssNetWork;
import com.bss.client.container.GamePlayPanel;
import com.bss.client.container.MainFrame;
import com.bss.client.container.PanelState;
import com.bss.common.BssProtocol;

import resources.ResContainer;
import resources.ResLoader;

public class Tile extends JLabel implements MouseListener, Serializable{
	
	private static int width=50, height=50;
	
	
	private static MainFrame mInst = MainFrame.getInst();
	private static GamePlayPanel gamePlayPanel;	//initialized when entered gameplaypanel

	private ImageIcon curIcon;
	
	private int row, col;			// Rows and Cols in 10 * 10 Grid
	private int x, y;				// Start Location of this object.
	
	private TileState state;
	private boolean isOver = false;
	
	int mouseX;
	int mouseY;
	
	private Grid parent;
	
	private Ship locatedShip;
	private int shipBodyLoc = -1;		// 0 to 4
	
	public Grid getGrid()
	{
		return parent;
	}

	/*
	 * Ship : reference of located ship
	 * loc 	: ship's body number which 0 to max 5 range int
	 */
	public void setLocatedShip(Ship ship,int loc)
	{
		shipBodyLoc = loc;
		locatedShip = ship;
	}
	public Ship getLocatedShip()
	{
		return locatedShip;
	}
	public int getLocatedBodyNum()
	{
		return shipBodyLoc;
	}
	public void removeShip()
	{
		shipBodyLoc = -1;
		locatedShip = null;
		state = TileState.EMPTY;
	}
	
	
	public Tile(int row, int col,Grid grid)
	{
		setOpaque(false);
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		
		setCurIcon(ResContainer.tile_icon);
		
		this.row = row;
		this.col = col;
		
		parent = grid;
		
		this.x = grid.getStartX() + col * width;
		this.y = grid.getStartY() + row * height;
		
		setBounds ( x,y,width,height);
		
		setIcon(getCurIcon());
		
		state = TileState.NONE;
		
		
	}
	
	public void setMouseListener()
	{
		addMouseListener(this);
		new Thread(new Runnable() {

			int w = x + getWidth();
			int h = y + getHeight();

			@Override
			public void run() {
				while (mInst.getPanelState()==PanelState.GAMEPLAY) {
					if (state == TileState.UNKNOWN) {
						if (mInst.mouseX >= x && mInst.mouseY >= y && mInst.mouseX < w && mInst.mouseY < h
								&& isOver == false) {
							setCurIcon(ResContainer.tile_located_icon);
							resetIcon();
							isOver = true;
						} else if (mInst.mouseX < x | mInst.mouseY < y | mInst.mouseX > w | mInst.mouseY > h) {
							if(isOver==true)
							{
								isOver = false;
								setCurIcon(ResContainer.tile_icon);
								resetIcon();
							}
						}

					}
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

	}
	
	

	public boolean isHit()
	{
		if(locatedShip==null)
			return false;
		return true;
	}
	
	/*
	 * 
	 *  Event Handlers...
	 * 
	 */
	
	public void resetIcon()
	{
		setIcon(getCurIcon());
		
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state == TileState.UNKNOWN )
		{
			/*
			 * 타일 공격하면
			 * c1 		-> server 	어디어디 클릭했다고 서버에 메세지
			 * server 	-> c2		 서버에서 상대방에게 어디어디 공격왔다고 메세지 
			 * c2		-> server 	 어디에 맞았다고 판별 및 처리, 서버에 메세지
			 * server	-> c1 		적중여부, 어떤 배인지 등. 공격한 타일 상태 및 색 변화.
			 */
			
			if( gamePlayPanel!=null && gamePlayPanel.isMyTurn() 
					&& gamePlayPanel.isActionAllowed())
			{	
				BssNetWork.getInst().sendMessage(BssProtocol.ATTACK_PERFORMED, this);
				
				GamePlayPanel.getInst().setActionAllowed(false);
				GamePlayPanel.getInst().stopCountDown();
			}
		}
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public TileState getState() {
		return state;
	}

	public void setState(TileState state) {
		this.state = state;
	}

	public ImageIcon getCurIcon() {
		return curIcon;
	}

	public void setCurIcon(ImageIcon curIcon) {
		this.curIcon = curIcon;
	}

	public static GamePlayPanel getGamePlayPanel() {
		return gamePlayPanel;
	}

	public static void setGamePlayPanel(GamePlayPanel gamePlayPanel) {
		Tile.gamePlayPanel = gamePlayPanel;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public Point getCenterXY()
	{
		return new Point(x+width/2,y+height/2);
	}
	public int getCenterX()
	{
		return x+width/2;
	}
	public int getCenterY()
	{
		return y+height/2;
	}
}
