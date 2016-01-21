package com.bss.client.scene;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.GuiComponents.StyleButton;
import com.bss.client.GuiComponents.StyleTextArea;

import resources.ResourceLoader;

public class LoginWindowPanel extends JPanel{

	Image img;
	
	JPanel bgPanel; 
	JPanel loginPanel;
	
	StyleButton btnLogin ;
	StyleButton btnExit ;
	
	StyleTextArea taLogin;
	StyleTextArea taPwd;
	
	JLabel label ;
	
	public LoginWindowPanel(JFrame parent)
	{
		
		setLayout(null);
		
		img = Toolkit.getDefaultToolkit().createImage(ResourceLoader.getResURL("images/login_bg.gif"));
	
		
		loginPanel = new JPanel();
		loginPanel.setBackground(new Color(0,0,0));
		loginPanel.setBounds(100,400,300,400);
		loginPanel.setLayout(new FlowLayout());
		
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener((ActionListener) parent);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener((ActionListener) parent);
		
		taLogin = new StyleTextArea();
		
		taLogin.setSize(100, 50);
		taPwd = new StyleTextArea();
		
		loginPanel.add(taLogin);
		loginPanel.add(taPwd);
		loginPanel.add(btnLogin);
		loginPanel.add(btnExit);
		
		
		add(loginPanel);
		
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
		paintChildren(g);
		setOpaque(false);
	}
	

}
