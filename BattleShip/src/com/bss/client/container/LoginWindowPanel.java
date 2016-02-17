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
import com.bss.client.components.StyleTextField;
import com.bss.common.BssDebug;

import resources.ResLoader;
import sun.security.util.Debug;

public class LoginWindowPanel extends JPanel implements ActionListener{

	private Image img;
	
	private JPanel bgPanel; 
	
	private StyleButton btnLogin ;
	private StyleButton btnSetting;
	private StyleButton btnExit ;
	
	private StyleTextField taLogin;
	
	private JLabel label ;

	public LoginWindowPanel(JFrame parent)
	{
		setOpaque(false);
		setLayout(null);
		
		img = Toolkit.getDefaultToolkit().createImage(ResLoader.getResURL("images/login_bg.gif"));
		
		taLogin = new StyleTextField(150,40);
		
		
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener(this);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener(this);
		btnSetting = new StyleButton("Settings");

		btnLogin.setSize(150,70);
		btnExit.setSize(150,70);
		btnSetting.setSize(150,70);
		
		int screenW = parent.getWidth();
		int screenH = parent.getHeight();
		
		int a = screenW/2 - btnLogin.getWidth()/2 ;
		int b =  screenH/2 - btnLogin.getHeight()/2 ;
		
		taLogin.setLocation(a,b-43);
		btnLogin.setLocation(a,b);
		btnSetting.setLocation(a,b+73);
		btnExit.setLocation(a,b+167);
		
		add(taLogin);
		add(btnSetting);
		add(btnLogin);
		add(btnExit);
		
		taLogin.getField().setText(createRamdomText(5));
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
		
		if (b == btnLogin) {
			 
			
			// Connection first..
			BssNetWork inst = BssNetWork.getInst();
			
			inst.connection();
			if (inst.isConnected()) {
				MainFrame.getInst().openWaitRoom();
				return;
			}

			if (BssDebug.GAMEREADY_TESTING)
				MainFrame.getInst().openWaitRoom();

		}
		else if(b==btnExit)
			MainFrame.getInst().quitGame();
		else if(b==btnSetting)
		{
			//do something... 
		}
	}	

}
