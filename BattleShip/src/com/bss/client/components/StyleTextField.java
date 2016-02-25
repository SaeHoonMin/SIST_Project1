package com.bss.client.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import resources.BssColor;
import sun.print.resources.serviceui;

// Currently this class can be used for USER's id field only..
// need to use strategy pattern 

public class StyleTextField extends JPanel implements KeyListener, CaretListener ,FocusListener{
	
	private int length;
	Thread t ;
	
	JTextField field;
	
	CompoundBorder selectedBorder;
	CompoundBorder releasedBorder;
	public StyleTextField(int w, int h)
	{
		releasedBorder = new CompoundBorder(new LineBorder(BssColor.TURQUOISE_MID_T1, 2), new EmptyBorder(0, 10, 0, 0));
		selectedBorder = new CompoundBorder(new LineBorder(BssColor.ORANGE_T1, 2), new EmptyBorder(0, 10, 0, 0));
		
		setLayout(null);
		setSize(w,h);
		setOpaque(false);
		setBackground(BssColor.BLACK_T1);
		
		field = new JTextField(){
			@Override
			public void setText(String t) {
				// TODO Auto-generated method stub
				super.setText(t);
				field.setCaretPosition(field.getText().length());
			}
		};
		field.setLocation(10,0);
		field.setSize(w-10,h);
		field.setFont(new Font("Arial",Font.BOLD,16));
		field.setBackground(BssColor.BLACK_T1);
		field.setForeground(BssColor.TURQUOISE_MID_T1);
		field.setSelectionColor(BssColor.TURQUOISE_MID);
		field.setCaretColor(BssColor.TURQUOISE_MID_T2);
		field.setBorder(null);
		field.setOpaque(false);
		
		setBorder( releasedBorder );     
		
		setLength(10);
		
		field.addCaretListener(this);
		field.addKeyListener(this);
		field.addFocusListener(this);
		
		add(field);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if( (!field.isEditable()) && e.getKeyCode() == e.VK_BACK_SPACE )
		{
			field.setEditable(true);
		}
		
	}
	public JTextField getField()
	{
		return field;
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
		if(field.getText().length()+1 > getLength())
		{
			System.out.println("over");
			e.consume();
		}
	}
	
	@Override
	public void requestFocus() {
		// TODO Auto-generated method stub
		super.requestFocus();
		field.requestFocus();
	}
	
	@Override
	public void setFocusable(boolean focusable) {
		// TODO Auto-generated method stub
		super.setFocusable(focusable);
		if(focusable==false)
			field.setEditable(false);
		else
			field.setEditable(true);
		field.setFocusable(focusable);
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		if(e.getDot() != field.getText().length())
			field.setCaretPosition(field.getText().length());
	}

	@Override
	public void focusGained(FocusEvent e) {
		setBorder(selectedBorder);
		field.setForeground(BssColor.ORANGE_T1);
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		setBorder(releasedBorder);
		field.setForeground(BssColor.TURQUOISE_MID_T1);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public String getText()
	{
		return field.getText();
	}
}
