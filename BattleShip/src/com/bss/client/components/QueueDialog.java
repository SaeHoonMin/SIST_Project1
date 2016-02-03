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

import com.bss.client.container.MainFrame;

public class QueueDialog extends JDialog implements ActionListener{
	
	JLabel msg;
	StyleButton startBtn;
	StyleButton cancleBtn;
	MainFrame mainFrame = MainFrame.getInst();
	JPanel pane;
	
	public StyleButton getCancleBtn()
	{
		return cancleBtn;
	}

	public QueueDialog() {
		
	//	mainFrame.setEnabled(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0,200));
		
		
		setSize(200,100);
	
		pane = new JPanel();
		pane.setOpaque(false);
		pane.setSize(getSize());
		System.out.println(pane.getSize());
		pane.setLayout(null);
	
		JLabel msg = new JLabel("Finding a Match..");
	//	msg.setBackground(new Color(0,0,0,200));
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setForeground(Color.white);
		msg.setFont(new Font("Arial",Font.BOLD,16));
		
		startBtn = new StyleButton("Start");
		cancleBtn = new StyleButton("Cancle");
		
		msg.setBounds(0,0,200,50);
		startBtn.setBounds(0,50,99,50);
		startBtn.setFontSize(13);
		startBtn.addActionListener(this);
		cancleBtn.setBounds(101,50,100,50);
		cancleBtn.setFontSize(13);
		cancleBtn.addActionListener(this);
		
		pane.add(msg);
		pane.add(startBtn);
		pane.add(cancleBtn);
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
			//���������� ��Ȱ��ȭ ���
		}
		else if(e.getSource() == cancleBtn)
		{
			dispose();
			MainFrame.getInst().setEnabled(true);
		}
	}

}
