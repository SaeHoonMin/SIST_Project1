package com.bss.client.container;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.StyleButton;
import com.bss.client.components.StyleTextArea;

import resources.ResLoader;

public class LoginWindowPanel extends JPanel implements ActionListener{

	private Image img;
	
	private JPanel bgPanel; 
	
	private StyleButton btnLogin ;
	private StyleButton btnSetting;
	private StyleButton btnExit ;
	
	private StyleTextArea taLogin;
	private StyleTextArea taPwd;
	
	private JLabel label ;

	public LoginWindowPanel(JFrame parent)
	{
		setOpaque(false);
		setLayout(null);
		
		img = Toolkit.getDefaultToolkit().createImage(ResLoader.getResURL("images/1.jpg"));
		
		taLogin = new StyleTextArea();
		
		
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener(this);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener(this);
		btnSetting = new StyleButton("Settings");

		taLogin.setSize(150,40);
		btnLogin.setSize(150,70);
		btnExit.setSize(150,70);
		btnSetting.setSize(150,70);
		
		int screenW = parent.getWidth();
		int screenH = parent.getHeight();
		
		int a = screenW/2 - btnLogin.getWidth()/2;
		int b =  screenH/2 - btnLogin.getHeight()/2;
		
		taLogin.setLocation(a,b-75);
		btnLogin.setLocation(a,b);
		btnSetting.setLocation(a,b+75);
		btnExit.setLocation(a,b+150);
		
		add(taLogin);
		add(btnSetting);
		add(btnLogin);
		add(btnExit);
		
		taLogin.setText(createRamdomText(5));
		
	}
	
	
	//For testing.. id ÀÔ·ÂÇÏ±â ±ÍÂúÀ¸´Ï±î
	private String createRamdomText(int charlen)
	{
		Random rand = new Random();
		StringBuffer buf = new StringBuffer();
		int num;
		for(int i=0;i<charlen;i++)
		{
			num = rand.nextInt(26)+65;
			buf.append((char)num);
		}
		buf.append(rand.nextInt(10));
		buf.append(rand.nextInt(10));
		
		return buf.toString();
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		StyleButton b = null;
		if( e.getSource() instanceof StyleButton )
			 b = (StyleButton) e.getSource();
		else
			return;
		
		if(b==btnLogin)
		{
			//Connection first..
			if(BssNetWork.getInst().isConnected())
			{
				MainFrame.getInst().openWaitRoom();
			}
		}
		else if(b==btnExit)
			MainFrame.getInst().quitGame();
		else if(b==btnSetting)
		{
			//do something... 
		}
	}	

}
