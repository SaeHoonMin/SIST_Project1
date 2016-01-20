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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.GuiComponents.StyleButton;
import com.bss.client.GuiComponents.StyleTextArea;

import resources.ResourceLoader;

public class LoginWindowFrame extends JFrame implements ActionListener{

	Image img;
	
	JPanel bgPanel; 
	JPanel loginPanel;
	
	StyleButton btnLogin ;
	StyleButton btnExit ;
	
	StyleTextArea taLogin;
	StyleTextArea taPwd;
	
	JLabel label ;
	
	public LoginWindowFrame()
	{
		img = Toolkit.getDefaultToolkit().createImage(ResourceLoader.getResURL("images/login_bg.gif"));
		System.out.println(img);
		bgPanel = new JPanel()
		{
			public void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
				paintChildren(g);
				setOpaque(false);
			}
		};
		
	//	label = new JLabel(new ImageIcon(img));
	//	bgPanel.add(label);
		
		loginPanel = new JPanel();
//		loginPanel.setBackground(new Color(0,0,0,0));
		loginPanel.setBounds(100,400,300,400);
		loginPanel.setLayout(new FlowLayout());
		
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener(this);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener(this);
		
		taLogin = new StyleTextArea();
		
		taLogin.setSize(100, 50);
		taPwd = new StyleTextArea();
		
		loginPanel.add(taLogin);
		loginPanel.add(taPwd);
		loginPanel.add(btnLogin);
		loginPanel.add(btnExit);
		
		
		add(loginPanel);
		
		add(bgPanel);
		
		setSize(1280, 1024);
		setVisible(true);
		setResizable(false);
	
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnExit))
		{
			System.exit(0);
		}
		else if(e.getSource().equals(btnLogin))
		{
			System.out.println("login");
			WaitRoomFrame n = new WaitRoomFrame();
			n.setVisible(true);
		}
	}
}
