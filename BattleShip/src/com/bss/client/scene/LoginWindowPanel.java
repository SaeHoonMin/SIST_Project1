package com.bss.client.scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.GuiComponents.StyleButton;
import com.bss.client.GuiComponents.StyleTextArea;

import resources.ResLoader;

public class LoginWindowPanel extends JPanel{

	Image img;
	
	JPanel bgPanel; 
	
	StyleButton btnLogin ;
	StyleButton btnExit ;
	
	StyleTextArea taLogin;
	StyleTextArea taPwd;
	
	JLabel label ;

	public LoginWindowPanel(JFrame parent)
	{
		
		setLayout(null);
		
		img = Toolkit.getDefaultToolkit().createImage(ResLoader.getResURL("images/1.jpg"));
		
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener((ActionListener) parent);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener((ActionListener) parent);

		btnLogin.setSize(150,70);
		btnExit.setSize(150,70);
		
		int screenW = parent.getWidth();
		int screenH = parent.getHeight();
		
		btnLogin.setLocation(screenW/2 - btnLogin.getWidth()/2, screenH/2 - btnLogin.getHeight()/2);
		btnExit.setLocation(screenW/2 - btnExit.getWidth()/2, screenH/2 - btnExit.getHeight()/2 + btnLogin.getHeight()+5);
		
		
		add(btnLogin);
		add(btnExit);
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
		setOpaque(false);
		
	}	

}
