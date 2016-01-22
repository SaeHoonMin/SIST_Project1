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

import com.bss.client.GameObjects.Tile;
import com.bss.client.GuiComponents.StyleButton;
import com.bss.client.GuiComponents.StyleTextArea;

import resources.ResourceLoader;

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
		
		img = Toolkit.getDefaultToolkit().createImage(ResourceLoader.getResURL("images/login_bg.gif"));
		
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener((ActionListener) parent);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener((ActionListener) parent);

		btnLogin.setSize(150,70);
		btnExit.setSize(150,70);
		
		int screenW = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int screenH = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
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
