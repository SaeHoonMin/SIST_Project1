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
import javax.swing.JLabel;
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

public class StyleChatTextArea extends JPanel{
	
	private int length;
	
	JLabel field;
	
	CompoundBorder selectedBorder;
	CompoundBorder releasedBorder;
	
	Thread t;
	
	private boolean fadeEnd = true;
	
	public StyleChatTextArea(int w, int h)
	{
		releasedBorder = new CompoundBorder(new LineBorder(new Color(255,255,255,240), 2), new EmptyBorder(0, 10, 0, 0));
		
		setLayout(null);
		setSize(w,h);
		setOpaque(false);
		setBackground(BssColor.BLACK_T2);
		
		field = new JLabel(){
			@Override
			public void setText(String t) {
				// TODO Auto-generated method stub
				super.setText(t);
			}
		};
		
		field.setLocation(10,0);
		field.setSize(w-10,h);
		field.setFont(new Font("¸¼Àº °íµñ",Font.TRUETYPE_FONT,16));
		field.setBackground(BssColor.BLACK_T1);
		field.setForeground(new Color(255,255,255,240));
		field.setBorder(null);
		field.setOpaque(false);
		
		setBorder( releasedBorder );     
		
		setVisible(false);
		
		add(field);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void showText(String str)
	{
		field.setText(str);
		
		setVisible(true);
		
		if(t != null && fadeEnd == true)
		{
			t.interrupt();
			fadeEnd=false;
		}
		
		t = new Thread(new Runnable(){
			// bg, border, font
			int alpha = 240;
			@Override
			public void run() {
				fadeEnd=true;
				while(fadeEnd)
				{
					setBorder(new CompoundBorder(new LineBorder(new Color(255,255,255,alpha), 2), new EmptyBorder(0, 10, 0, 0)) );    
					field.setForeground(new Color(255,255,255,alpha));
					setBackground(new Color(0,0,0,alpha));
					
					alpha-=3;
					
					if(alpha<=0)
					{
						setVisible(false);
						fadeEnd=false;
					}
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("fading thread : "+e.getMessage());
					}
				}
			}
			
		});
		
	
		
		t.start();
	}


}
