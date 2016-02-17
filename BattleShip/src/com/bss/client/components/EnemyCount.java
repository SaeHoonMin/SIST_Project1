package com.bss.client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import resources.BssColor;
import resources.BssFont;

public class EnemyCount extends JPanel implements MouseListener{
	
	private JLabel label;
	private Color bgColor;
	private Color labelColor;
	
	private TitledBorder tRelBorder;
	private TitledBorder tOnBorder;
	
	public EnemyCount()
	{
		bgColor = BssColor.BLACK_T1;
		labelColor = BssColor.TURQUOISE_MID;
		
		Border border = BorderFactory.createLineBorder(BssColor.TURQUOISE_MID_T1,2);
		tRelBorder = BorderFactory.createTitledBorder(border, "Enemy Detected");
		tRelBorder.setTitleColor(BssColor.TURQUOISE_MID_T1);
		
		border = BorderFactory.createLineBorder(BssColor.ORANGE_T1,2);
		tOnBorder = BorderFactory.createTitledBorder(border, "Enemy Detected");
		tOnBorder.setTitleColor(BssColor.ORANGE_T1);
		
		label = new JLabel("",SwingConstants.CENTER);
		label.setForeground(labelColor);			//청록
		label.setFont(new Font("Arial", Font.BOLD,16));
		label.setPreferredSize(new Dimension(150,50));		//문제
		
		add("Center",label);
		setSize(270,70);
		setBackground(bgColor);
		setBorder(tRelBorder);
		
		addMouseListener(this);
		setOpaque(false);
	}
	
	protected void paintComponent(Graphics g)
    {
		super.paintComponent(g);
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
    }
	
	public void setText(String txt)
	{
		label.setText(txt);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
				setBorder(tOnBorder);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
				setBorder(tRelBorder);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
