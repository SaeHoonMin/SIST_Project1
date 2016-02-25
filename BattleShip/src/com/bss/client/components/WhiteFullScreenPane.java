package com.bss.client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WhiteFullScreenPane extends JPanel{

	private JLabel label ;
	public JPanel parent;
	
	public WhiteFullScreenPane(JPanel parent)
	{
		this.parent = parent;
		
		setLayout(new BorderLayout());
		setBackground(new Color(0,0,0,200));
		
		label = new JLabel("hahahahah",SwingConstants.CENTER);
		label.setFont(new Font("Arial",Font.BOLD,40));
		label.setForeground(Color.white);
		
		add("Center",label);
		
		setVisible(true);
		setSize(parent.getSize());
	}
	
	public void setFontSize(int size)
	{
		label.setFont(new Font(label.getFont().getName(),label.getFont().getStyle(),size));
	}
	
	public void setText(String s)
	{
		label.setText(s);
	}
}
