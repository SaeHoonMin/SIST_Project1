package com.bss.client.container;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import com.bss.client.components.UIDebugWindow;

public class MainFrame extends JFrame  implements Runnable{
	
	static MainFrame inst;
	
	 
    //Network
    Socket s;
    OutputStream out;
    BufferedReader in;
	
	
	LoginWindowPanel 	loginWindow;
	WaitRoomPanel		waitRoom;
	GameReadyPanel		readyPanel;
	
	private int mX,mY;
	private int x, y;
	
	public int mouseX, mouseY;
	public static int xOffset = 8 ,yOffset = 31;		//x,y coordinates offset. must sub this value
	
	public static MainFrame getInst()
	{
		if(inst == null)
		{
			inst = new MainFrame();
			System.out.println("If you see this message, it's not safe.");
		}
		return inst;
	}
	
	public MainFrame()
	{
		if(inst == null)
			inst = this;
		
		setSize(1280, 982);
		setTitle("BattleShip");
		
		loginWindow = new LoginWindowPanel(this);
		loginWindow.setAlignmentX(0.5f);
		loginWindow.setAlignmentX(0.5f);
		add(loginWindow);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
		UIDebugWindow debugWindow = new UIDebugWindow();
		
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			synchronized(this){
			mX = MouseInfo.getPointerInfo().getLocation().x;
			mY = MouseInfo.getPointerInfo().getLocation().y;
			
			x = getLocation().x;
			y = getLocation().y;
			
			mouseX = mX-x-xOffset;
			mouseY = mY-y-yOffset;
			}
			
			/*
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}
}
