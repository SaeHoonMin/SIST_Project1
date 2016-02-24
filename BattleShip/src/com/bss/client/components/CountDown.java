package com.bss.client.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import resources.BssColor;

public class CountDown extends JLabel{
	
	Thread t;
	
	public CountDown()
	{
		setHorizontalAlignment(JLabel.CENTER);
		setText(String.valueOf(60));
		setForeground(new Color(255,255,255));
		setFont(new Font("Arial",Font.BOLD,30));
		setBorder(BorderFactory.createLineBorder(BssColor.YELLOGREEN_T1,5));
		setSize(80,80);
		setBackground(BssColor.BLACK_T1);
	}
	
	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
		super.setText(text);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				int size_Origin = 30;
				int i = size_Origin + 18;
				try
				{
					while(true)
					{
						setFont(new Font("Arial",Font.BOLD,i));
						i--;
						if(i<size_Origin)
							break;
						Thread.sleep(20);
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
		}).start();
		
		
	}

}
