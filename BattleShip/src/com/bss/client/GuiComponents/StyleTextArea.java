package com.bss.client.GuiComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class StyleTextArea extends JTextField implements Runnable {
	
	int length;
	Thread t ;
	
	public StyleTextArea()
	{
		setPreferredSize(new Dimension(150,50));
		setFont(new Font("Arial",Font.BOLD,16));
		setBackground(new Color(100,100,100));
		
		setAlignmentX(0.5f);
		setAlignmentY(0.5f);
		
		length = 10;
		
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String s;
		while(true)
		{
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			s = getText();
			if (s.length() > length) {
				s = s.substring(0, length);
				setText(s);
				setCaretPosition(getDocument().getLength());
			}

		}
	}
}
