package com.bss.client.container;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.StyleButton;
import com.bss.client.components.StylePasswordField;
import com.bss.client.components.StyleTextField;
import com.bss.client.gameObjects.Grid;
import com.bss.client.gameObjects.UserInfo;
import com.bss.common.BssDebug;
import com.bss.common.BssProtocol;
import com.bss.server.*;

import resources.ResLoader;
import sun.security.util.Debug;

public class LoginWindowPanel extends JPanel implements ActionListener{

	private Image img;
	
	private JPanel bgPanel; 
	
	private StyleButton btnLogin ;
	private StyleButton btnSetting;
	private StyleButton btnExit ;
	private StyleButton btnSignup;
	
	private StyleTextField taLogin;
	private StylePasswordField taPass;
	private JLabel label ;
	public String userid;

	public LoginWindowPanel(JFrame parent)
	{
		setOpaque(false);
		setLayout(null);
		
		img = Toolkit.getDefaultToolkit().createImage(ResLoader.getResURL("images/login_bg.gif"));
		
		taLogin = new StyleTextField(150,40);
		
		
		taLogin = new StyleTextField(150,40);
		taPass = new StylePasswordField(150, 40);
		btnLogin = new StyleButton("Login");
		btnLogin.addActionListener(this);
		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener(this);
		btnSetting = new StyleButton("Settings");
		btnSignup= new StyleButton("Sign up");
		btnSignup.addActionListener(this);

		btnLogin.setSize(150,70);
		btnExit.setSize(150,70);
		btnSetting.setSize(150,70);
		btnSignup.setSize(150,70);
		
		int screenW = parent.getWidth();
		int screenH = parent.getHeight();
		
		int a = screenW/2 - btnLogin.getWidth()/2 ;
		int b =  screenH/2 - btnLogin.getHeight()/2 ;
		
		taLogin.setLocation(a,b-133); taPass.setLocation(a,b-93);
		btnLogin.setLocation(a,b-50);
		btnSetting.setLocation(a,b+23);
		btnSignup.setLocation(a, b+94);
		btnExit.setLocation(a,b+177);
		
		add(taLogin);add(taPass);
		add(btnSetting);
		add(btnLogin);
		add(btnExit);
		add(btnSignup);
		btnSetting.addActionListener(this);

	}
	
	
	//For testing.. id 입력하기 귀찮으니까
/*	private String createRamdomText(int charlen)
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
	}*/
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		DBA member = new DBA();
		StyleButton b = null;
		if( e.getSource() instanceof StyleButton )
			 b = (StyleButton) e.getSource();
		else
			return;
		
		if (b == btnLogin) {
			
			try{		
				if(member.login(taLogin.getField().getText(),taPass.getField().getText())){

			// Connection first..
			BssNetWork inst = BssNetWork.getInst();
			inst.connection(taLogin.getField().getText());
			if (inst.isConnected()) {
				BssNetWork.getInst().sendMessage(BssProtocol.USERINFO, new UserInfo(taLogin.getField().getText()));
				MainFrame.getInst().openWaitRoom();
				//유저정보보내기
				
				return;
			}

			if (BssDebug.GAMEREADY_TESTING)
				MainFrame.getInst().openWaitRoom();

		}
				else{
					JOptionPane.showMessageDialog(this, "아이디 혹은 비밀번호가 틀립니다.");
				}
			}catch (Exception ex){}
		}
		else if(b==btnSetting){
			MainFrame.getInst().openSetting();
		}
		
		else if(b==btnSignup){
			MainFrame.getInst().openRegister();
		}
		else if(b==btnExit)
			MainFrame.getInst().quitGame();
	
	}	

}
