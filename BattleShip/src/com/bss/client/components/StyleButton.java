package com.bss.client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.bss.client.gameObjects.TileState;
import com.sun.corba.se.spi.orbutil.fsm.State;

public class StyleButton extends JButton implements MouseListener{
	

	private int fontSize;
	


	private Color bgColor;
	private Color overColor;
	private Color color;
	
	public StyleButton(String text)
	{	
		
		bgColor = new Color(75,180,138);
		overColor = new Color(124,204,172);
		setText(text);
		setPreferredSize(new Dimension(150,50));
		
		setFont(new Font("Arial", Font.BOLD,16));
		fontSize = getFont().getSize();
		
		setFocusable(false);
		
		setBorderPainted(false);
		setBackground(bgColor);
		setForeground(new Color(0xff,0xf8,0xdc));
		
		
		color = getForeground();
		
		addMouseListener(this);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}
	
	
	public void setPressedState()
	{
		setEnabled(false);
//		CompoundBorder compound = new CompoundBorder(new LineBorder(new Color(197,219,211), 1), new EmptyBorder(0, 1, 0, 0));
		setBorder(BorderFactory.createLineBorder(new Color(197,219,211), 3));
	}
	public void releasePressedState()
	{
		setEnabled(true);
		setBorder(null);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		new Thread(new Runnable() {
			public void run() {
				
				setBackground(overColor);
				setForeground(Color.white);
				
				int fSize = fontSize;
				
				while (true) {
					fSize ++;
					setFont(new Font("Arial", Font.BOLD, fSize));
					try {
						Thread.sleep(10);
					} catch (InterruptedException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
					
					if (fSize >= fontSize * 1.3)
						break;
				}
			}
		}).start();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		new Thread(new Runnable() {
			public void run() {
				
				setBackground(bgColor);
				setForeground(color);

				int fSize = getFont().getSize();
				
				while (true) {
					fSize --;
					setFont(new Font("Arial", Font.BOLD, fSize));
					try {
						Thread.sleep(10);
					} catch (InterruptedException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
					
					if (fSize <= fontSize )
						break;
				}
			}
		}).start();
	}
	
	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getOverColor() {
		return overColor;
	}

	public void setOverColor(Color overColor) {
		this.overColor = overColor;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
