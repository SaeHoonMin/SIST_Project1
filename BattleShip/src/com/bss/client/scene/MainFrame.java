package com.bss.client.scene;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.bss.client.GuiComponents.UIDebugWindow;

import com.bss.client.scene.WaitRoomPanel;

public class MainFrame extends JFrame  implements ActionListener, Runnable{
	
	static MainFrame inst;
	
	 
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		/*******************
		 * In Login Panel...
		 *******************/
		
		if (e.getSource().equals(loginWindow.btnExit)) {
			System.exit(0);
		} 
		else if (e.getSource().equals(loginWindow.btnLogin)) {
			waitRoom = new WaitRoomPanel(this);
		
			
			remove(loginWindow);
			setContentPane(waitRoom);
			repaint();
		}
		
		/*********************
		 *  In the WaitRoom..
		 ********************/
		//WaitRoom -> GameReady
		if(e.getSource().equals(waitRoom.b1))
		{
			readyPanel = new GameReadyPanel(this);
			remove(waitRoom);
			
			setContentPane(readyPanel);
			repaint();
		}
		
		/*********************
		 *  In the GameReady..
		 ********************/
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			mX = MouseInfo.getPointerInfo().getLocation().x;
			mY = MouseInfo.getPointerInfo().getLocation().y;
			
			x = getLocation().x;
			y = getLocation().y;
			
			mouseX = mX-x-xOffset;
			mouseY = mY-y-yOffset;
		
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
