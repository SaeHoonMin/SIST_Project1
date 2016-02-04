package com.bss.client.gameObjects;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bss.client.BssNetWork;
import com.bss.client.container.GamePlayPanel;
import com.bss.client.container.MainFrame;
import com.bss.common.BssProtocol;

import resources.ResContainer;
import resources.ResLoader;

public class Tile extends JLabel implements MouseListener, Serializable{
	
	private static int width=50, height=50;
	
	
	private static GamePlayPanel gamePlayPanel;	//initialized when entered gameplaypanel

	private ImageIcon curIcon;
	
	private int row, col;			// Rows and Cols in 10 * 10 Grid
	private int x, y;				// Start Location of this object.
	
	private TileState state;
	
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
	}
	public void releaseMouseListener()
	{
		releaseMouseListener();
	}

	public boolean isHit()
	{
		if(locatedShip==null)
			return false;
		return true;
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
			 * Ÿ�� �����ϸ�
			 * c1 		-> server 	����� Ŭ���ߴٰ� ������ �޼���
			 * server 	-> c2		 �������� ���濡�� ����� ���ݿԴٰ� �޼��� 
			 * c2		-> server 	 ��� �¾Ҵٰ� �Ǻ� �� ó��, ������ �޼���
			 * server	-> c1 		���߿���, � ������ ��. ������ Ÿ�� ���� �� �� ��ȭ.
			 */
			if(GamePlayPanel.getInst()!=null && GamePlayPanel.getInst().isMyTurn())
				BssNetWork.getInst().sendMessage(BssProtocol.ATTACK_PERFORMED, this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	
		if(state == TileState.UNKNOWN && GamePlayPanel.getInst().isActionAllowed())
		{
			setCurIcon(ResContainer.tile_over_icon);
			resetIcon();
		}
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(state == TileState.UNKNOWN && GamePlayPanel.getInst().isActionAllowed())
		{
			setCurIcon(ResContainer.tile_icon);

			resetIcon();
		}
	
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	/*	
		Tile t;
		
		if( e.getSource() instanceof Tile )
			t = (Tile) e.getSource();
		else
			return;
	
	//	System.out.printf("(%d,%d,%d,%d),(%d,%d)\n",x,y,x+width,y+height,MainFrame.getInst().mouseX,MainFrame.getInst().mouseY);
		
		setCurIcon(ResContainer.tile_pressed_icon);
		
		resetIcon();
		
	*/
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	/*	
		Tile t;
		
		if( e.getSource() instanceof Tile )
			t = (Tile) e.getSource();
		else
			return;
		
		mouseX = MainFrame.getInst().mouseX;
		mouseY = MainFrame.getInst().mouseY;
		
		if( mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height)
			setCurIcon(ResContainer.tile_over_icon);
		else
		{
			setCurIcon(ResContainer.tile_icon);
		}
		resetIcon();
	*/
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

}
