package com.bss.client.container;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import com.bss.client.components.UIDebugWindow;
import com.bss.client.gameObjects.Grid;
import com.bss.common.BssDebug;

public class MainFrame extends JFrame  implements Runnable{
	
	static MainFrame inst;
	
	LoginWindowPanel 	loginWindow;
	WaitRoomPanel		waitRoom;
	GameReadyPanel		readyPanel;
	GamePlayPanel		playPanel;
	
	private int mX,mY;
	private int x, y;
	
	public int mouseX, mouseY;
	public static int xOffset = 8 ,yOffset = 31;		//x,y coordinates offset. must sub this value
	
	public static MainFrame getInst()
	{
		if(inst == null)
		{
			inst = new MainFrame(1024,768);
			System.out.println("If you got this message, it's not safe.");
		}
		return inst;
	}
	
	public MainFrame(int width, int height)
	{
		if(inst == null)
			inst = this;
		
		setSize(width, height);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);
	
		setTitle("BattleShip In Space");
		
		loginWindow = new LoginWindowPanel(this);
		loginWindow.setAlignmentX(0.5f);
		loginWindow.setAlignmentX(0.5f);
		add(loginWindow);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
		if(BssDebug.UI_DEBUG)
		{
			UIDebugWindow debugWindow = new UIDebugWindow();
		}
		
		Thread t = new Thread(this);
		t.run();
	}
	
	
	public void quitGame()
	{
		dispose();
		System.exit(0);
	}
	public void openWaitRoom()
	{
		waitRoom = new WaitRoomPanel(this);
		remove(loginWindow);
		setContentPane(waitRoom);
		repaint();
	}
	
	//WaitRoom -> GameReady
	public void openGameReady()
	{
		readyPanel = new GameReadyPanel(this);
		remove(waitRoom);
		
		setContentPane(readyPanel);
		repaint();
	}
	//GameReady -> GameStart
	public void openGameStart(Grid grid)
	{
		playPanel = new GamePlayPanel(grid, this);
		remove(readyPanel);
		
		setContentPane(playPanel);
		repaint();
	}
	
	public static Point getPointForCenter(int width, int height)
	{
		if(inst ==null)
			return new Point(0,0);
		
		int x = inst.getWidth()/2 - width/2 ;
		int y = (inst.getHeight()-yOffset)/2 - height/2 ;
		
		return new Point(x,y);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				synchronized (this) {
					mX = MouseInfo.getPointerInfo().getLocation().x;
					mY = MouseInfo.getPointerInfo().getLocation().y;

					x = getLocation().x;
					y = getLocation().y;

					mouseX = mX - x - xOffset;
					mouseY = mY - y - yOffset;
				}
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
