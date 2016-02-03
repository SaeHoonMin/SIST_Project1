package com.bss.client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;


public class StyleTextArea extends JTextField implements Runnable, KeyListener {
	
	int length;
	Thread t ;
	
	public StyleTextArea()
	{
		setPreferredSize(new Dimension(150,50));
		setFont(new Font("Arial",Font.BOLD,16));
		setBackground(new Color(124,204,172));
		setForeground(Color.white);
		setBorder(null);
	
		setMargin(new Insets(10,10,10,10));
	
		
		length = 10;
		
		addKeyListener(this);
		
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
//				s = s.substring(0, length);
//				setText(s);
//				setCaretPosition(getDocument().getLength());
				setEditable(false);
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if( (!isEditable()) && e.getKeyCode() == e.VK_BACK_SPACE )
		{
			setEditable(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
