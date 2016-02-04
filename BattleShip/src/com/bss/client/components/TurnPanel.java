package com.bss.client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TurnPanel extends JPanel{

	public JLabel label ;

	public JPanel parent;
	
	public TurnPanel(JPanel parent)
	{
		this.parent = parent;
		
		//parent.setComponentZOrder(this, 1);
		
		setLayout(new BorderLayout());
		
		setBackground(new Color(0,0,0,200));
		
		label = new JLabel("hahahahah",SwingConstants.CENTER);
		label.setFont(new Font("Arial",Font.BOLD,30));
		label.setForeground(Color.white);
		add("Center",label);
		setVisible(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setSize(parent.getSize());
			}
			
		}).start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

}
