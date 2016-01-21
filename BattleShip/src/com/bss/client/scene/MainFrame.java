package com.bss.client.scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MainFrame extends JFrame  implements ActionListener{
	 
	LoginWindowPanel 	loginWindow;
	WaitRoomPanel		waitRoom;
	GameReadyPanel		readyPanel;
	
	public MainFrame()
	{
		setSize(1280, 1024);
		setTitle("BattleShip");
	//	setLayout(null);
		
		loginWindow = new LoginWindowPanel(this);
		
		add(loginWindow);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

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
			System.out.println("login");
			waitRoom = new WaitRoomPanel(this);
			remove(loginWindow);
			add(waitRoom);
			revalidate();
		}
		
		/*********************
		 *  In the WaitRoom..
		 ********************/
		//blah blah..
		if(e.getSource().equals(waitRoom.b1))
		{
			readyPanel = new GameReadyPanel(this);
			remove(waitRoom);
			add(readyPanel);
			revalidate();
		}
		
		/*********************
		 *  In the GameReady..
		 ********************/
		
		
	}
}
