package com.bss.client.container;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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

public class LoginWindowPanel extends JPanel implements ActionListener, KeyListener {

	private Image img;

	private JPanel bgPanel;

	public StyleButton btnLogin;
	private StyleButton btnSetting;
	private StyleButton btnExit;
	private StyleButton btnSignup;

	public StyleTextField taLogin;
	public StylePasswordField taPass;
	static Boolean login_check;

	public LoginWindowPanel(JFrame parent) {
		setOpaque(false);
		setLayout(null);

		img = Toolkit.getDefaultToolkit().createImage(ResLoader.getResURL("images/login_bg.gif"));

		taLogin = new StyleTextField(150, 40);

		taLogin = new StyleTextField(150, 40);
		taPass = new StylePasswordField(150, 40);
		btnLogin = new StyleButton("Login");
		taPass.getField().addKeyListener((KeyListener) this);

		btnLogin.addActionListener(this);

		btnExit = new StyleButton("Exit Game");
		btnExit.addActionListener(this);
		btnSetting = new StyleButton("Settings");
		btnSignup = new StyleButton("Sign up");
		btnSignup.addActionListener(this);

		btnLogin.setSize(150, 70);
		btnExit.setSize(150, 70);
		btnSetting.setSize(150, 70);
		btnSignup.setSize(150, 70);

		int screenW = parent.getWidth();
		int screenH = parent.getHeight();

		int a = screenW / 2 - btnLogin.getWidth() / 2;
		int b = screenH / 2 - btnLogin.getHeight() / 2;

		taLogin.setLocation(a, b - 133);
		taPass.setLocation(a, b - 93);
		btnLogin.setLocation(a, b - 50);
		btnSetting.setLocation(a, b + 23);
		btnSignup.setLocation(a, b + 94);
		btnExit.setLocation(a, b + 177);

		add(taLogin);
		add(taPass);
		add(btnSetting);
		add(btnLogin);
		add(btnExit);
		add(btnSignup);
		btnSetting.addActionListener(this);

	}

	public static void login_Check(Boolean login_check) {
		LoginWindowPanel.login_check = login_check;

	}

	// For testing.. id 입력하기 귀찮으니까
	/*
	 * private String createRamdomText(int charlen) { Random rand = new
	 * Random(); StringBuffer buf = new StringBuffer(); int num; for(int
	 * i=0;i<charlen;i++) { num = rand.nextInt(26)+65; buf.append((char)num); }
	 * buf.append(rand.nextInt(10)); buf.append(rand.nextInt(10));
	 * 
	 * return buf.toString(); }
	 */

	public void paintComponent(Graphics g) {

		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
/*		if (e.getSource() == taPass || e.getSource() == taLogin) {
			System.out.println("sdsd");
		}*/

		StyleButton b = null;
		StylePasswordField p = null;

		if (e.getSource() instanceof StyleButton)
			b = (StyleButton) e.getSource();
		else
			return;

		if (b == btnLogin) {
			BssNetWork inst = BssNetWork.getInst();
			// inst.connection(taLogin.getField().getText());
			inst.connection();

			if (inst.isConnected()) {
				String login_info = taLogin.getField().getText() + "|" + taPass.getField().getText();
				BssNetWork.getInst().sendMessage(BssProtocol.LOGIN_CHECK, login_info);

				while (true) {
					if (login_check != null) {
						break;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			try {

				if (login_check) {
					// Connection first..
					if (inst.isConnected()) {
						BssNetWork.getInst().sendMessage(BssProtocol.USERINFO,
								new UserInfo(taLogin.getField().getText()));
						MainFrame.getInst().openWaitRoom();
						// 유저정보보내기
						return;
					}

					if (BssDebug.GAMEREADY_TESTING)
						MainFrame.getInst().openWaitRoom();
				} else {
					JOptionPane.showMessageDialog(this, "아이디 혹은 비밀번호가 틀립니다.");
				}
				login_check = null;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "서버의 주소 또는 포트번호가 다릅니다.");
				try {
					Thread.sleep(10);

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} else if (b == btnSetting) {
			MainFrame.getInst().openSetting();
		}

		else if (b == btnSignup) {
			MainFrame.getInst().openRegister();
		} else if (b == btnExit)
			MainFrame.getInst().quitGame();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btnLogin.doClick();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
