package com.bss.client.scene;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame  implements ActionListener{
	
	static MainFrame inst;
	
	 
	LoginWindowPanel 	loginWindow;
	WaitRoomPanel		waitRoom;
	GameReadyPanel		readyPanel;
	
	public static MainFrame getInst()
	{
		return inst;
	}
	
	public MainFrame()
	{
		if(inst == null)
			inst = this;
		
		setSize(1280, 982);
		setTitle("BattleShip");
	//	setLayout(null);
		
		loginWindow = new LoginWindowPanel(this);
		loginWindow.setAlignmentX(0.5f);
		loginWindow.setAlignmentX(0.5f);
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

}
