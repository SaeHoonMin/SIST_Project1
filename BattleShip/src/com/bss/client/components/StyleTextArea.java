package com.bss.client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

// Currently this class can be used for USER's id field only..
// need to use strategy pattern 

public class StyleTextArea extends JTextField implements KeyListener, CaretListener {
	
	int length;
	Thread t ;
	
	public StyleTextArea()
	{
		setPreferredSize(new Dimension(150,50));
		setFont(new Font("Arial",Font.BOLD,16));
		setBackground(new Color(124,204,172));
		setForeground(Color.white);
		setBorder(null);
		
		CompoundBorder compound = new CompoundBorder(new LineBorder(new Color(124,204,172), 1), new EmptyBorder(0, 10, 0, 0));
		setBorder(compound );     
		
		length = 10;
		
		addCaretListener(this);
		addKeyListener(this);
		
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
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			e.consume();
		}
		if(getText().length()+1 > length)
		{
			System.out.println("over");
			e.consume();
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		if(e.getDot() < getText().length())
		{
			setCaretPosition(getText().length());
		}
	}
}
