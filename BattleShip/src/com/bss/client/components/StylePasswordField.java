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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.bss.client.BssNetWork;
import com.bss.client.container.LoginWindowPanel;
import com.bss.client.container.MainFrame;
import com.bss.common.BssDebug;
import com.bss.common.BssProtocol;
import com.bss.server.Database;

import resources.BssColor;

// Currently this class can be used for USER's id field only..
// need to use strategy pattern 

public class StylePasswordField extends JPanel implements FocusListener{
	
	int length;
	Thread t ;
	LoginWindowPanel lg;
	JPasswordField field;
	CompoundBorder selectedBorder;
	CompoundBorder releasedBorder;
	public StylePasswordField(int w, int h)
	{
		releasedBorder = new CompoundBorder(new LineBorder(BssColor.TURQUOISE_MID_T1, 2), new EmptyBorder(0, 10, 0, 0));
		selectedBorder = new CompoundBorder(new LineBorder(BssColor.ORANGE_T1, 2), new EmptyBorder(0, 10, 0, 0));
		
		setLayout(null);
		setSize(w,h);
		setOpaque(false);
		setBackground(BssColor.BLACK_T1);
		
		field = new JPasswordField(){
			@Override
			public void setText(String t) {
				// TODO Auto-generated method stub
				super.setText(t);
				field.setCaretPosition(field.getPassword().toString().length());
			}
		};
		field.setLocation(10,0);
		field.setSize(w,h);
		field.setFont(new Font("Arial",Font.BOLD,16));
		field.setBackground(BssColor.BLACK_T1);
		field.setForeground(BssColor.TURQUOISE_MID_T1);
		field.setSelectionColor(BssColor.TURQUOISE_MID);
		field.setCaretColor(BssColor.TURQUOISE_MID_T2);
		field.setBorder(null);
		field.setOpaque(false);
		
		
		setBorder( releasedBorder );     
		
		length = 10;
		
//		field.addCaretListener(this);
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

	public JTextField getField()
	{
		return field;
	}
	public char[] getPassword(){
		return field.getPassword();
	}
	public void setText(String str)
	{
		field.setText(str);
	}
/*
	@Override
	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		if(e.getDot() != field.getPassword().toString().length())
			field.setCaretPosition(field.getPassword().toString().length());
	}*/

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
}
