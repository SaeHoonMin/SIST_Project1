package com.bss.client.GuiComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

public class StyleTextArea extends JTextField {
	
	public StyleTextArea()
	{
		setPreferredSize(new Dimension(150,50));
		setFont(new Font("Arial",Font.BOLD,16));
		setBackground(new Color(200,200,200));
		setAlignmentX(0.5f);
		setAlignmentY(0.5f);
		
	}
}
