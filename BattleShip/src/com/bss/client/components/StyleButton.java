package com.bss.client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class StyleButton extends JButton implements MouseListener{
	
	JLabel label ;
	
	private int fontSize;
	private Color bgColor;
	private Color overColor;
	private Color color;
	
	public StyleButton(String text)
	{	
		
		bgColor = new Color(75,180,138,200);
		overColor = new Color(124,204,172,200);
		
		setPreferredSize(new Dimension(150,50));
		
		setFocusable(false);
		
		setBorderPainted(false);
		setBackground(bgColor);
		setForeground(color.white);
		
		label = new JLabel(text,SwingConstants.CENTER);
		label.setForeground(Color.white);
		label.setFont(new Font("Arial", Font.BOLD,16));
		label.setPreferredSize(new Dimension(150,50));		//¹®Á¦
		
		
		fontSize = label.getFont().getSize();
		
		setLayout(new BorderLayout());
		add("Center",label);
		
		
		color = getForeground();
		
		setOpaque(false);
		
		addMouseListener(this);
	}
	
	protected void paintComponent(Graphics g)
    {
		super.paintComponent(g);
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        
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
					label.setFont(new Font("Arial", Font.BOLD, fSize));
					repaint();
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

				int fSize = label.getFont().getSize();
				
				while (true) {
					fSize --;
					label.setFont(new Font("Arial", Font.BOLD, fSize));
					repaint();
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
		label.setFont(new Font("Arial", Font.BOLD, fontSize));
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
