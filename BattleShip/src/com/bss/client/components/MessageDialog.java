package com.bss.client.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bss.client.BssNetWork;
import com.bss.client.container.MainFrame;
import com.bss.client.container.WaitRoomPanel;
import com.bss.common.BssProtocol;

import resources.BssColor;
import resources.BssFont;

public class MessageDialog extends JDialog implements ActionListener{
	
	JLabel msg;
	StyleButton startBtn;
	MainFrame mainFrame = MainFrame.getInst();
	JPanel pane;
	

	public static void Show(String str)
	{
		new MessageDialog(str);
	}

	public MessageDialog(String str) {
		
	//	mainFrame.setEnabled(false);
		setUndecorated(true);
		setBackground(BssColor.BLACK_T2);
		
		setSize(200,100);
		setAlwaysOnTop(true);
	
		pane = new JPanel();
		pane.setOpaque(false);
		pane.setSize(getSize());
		System.out.println(pane.getSize());
		pane.setLayout(null);
	
		JLabel msg = new JLabel(str);
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setForeground(Color.white);
		msg.setFont(BssFont.ARIAL_B16);
		
		startBtn = new StyleButton("Ok");
		
		msg.setBounds(0,0,200,50);
	//	startBtn.setEnabled(false);					// --> 버튼 disabled 일때 모양 바뀌어야해
		startBtn.setBounds(0,50,200,50);
		startBtn.setFontSize(13);
		startBtn.addActionListener(this);
		
		pane.add(msg);
		pane.add(startBtn);
		//pack();
		
		add(pane);
		
		setVisible(true);
		setAutoRequestFocus(true);
		setLocation(mainFrame.getX() + mainFrame.getWidth()/2- getWidth()/2, 
				 mainFrame.getY() + mainFrame.getHeight()/2 - getHeight()/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == startBtn)
		{
			MainFrame.getInst().setEnabled(true);
			dispose();
		}
	}

}
