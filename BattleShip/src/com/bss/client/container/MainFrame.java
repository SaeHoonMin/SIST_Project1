package com.bss.client.container;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import com.bss.client.components.BssSettings;
import com.bss.client.components.Register;
import com.bss.client.components.UIDebugWindow;
import com.bss.client.gameObjects.Grid;
import com.bss.common.BssDebug;
import resources.ResContainer;

public class MainFrame extends JFrame implements Runnable {

	static MainFrame inst;

	LoginWindowPanel loginWindow;
	WaitRoomPanel waitRoom;
	GameReadyPanel readyPanel;
	GamePlayPanel playPanel;
	Register register;
	private int mX, mY;
	private int x, y;
	private static PanelState panelState;

	public int mouseX, mouseY;
	public static int xOffset = 8, yOffset = 31; // x,y coordinates offset. must
													// sub this value

	Cursor defaultCursor = Cursor.getDefaultCursor();
	Cursor targetCursor;
	
	private static String userId;

	public static MainFrame getInst() {
		if (inst == null) {
			inst = new MainFrame(1024, 768);
			System.out.println("If you got this message, it's not safe.");
		}
		return inst;
	}

	public static PanelState getPanelState() {
		return panelState;
	}

	public MainFrame(int width, int height) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		targetCursor = toolkit.createCustomCursor(ResContainer.target_cursor, new Point(13, 13), "Target");

		if (inst == null)
			inst = this;

		setSize(width, height);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);

		setTitle("BattleShip In Space");

		loginWindow = new LoginWindowPanel(this);
		loginWindow.setAlignmentX(0.5f);
		loginWindow.setAlignmentX(0.5f);
		add(loginWindow);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		if (BssDebug.UI_DEBUG) {
			UIDebugWindow debugWindow = new UIDebugWindow();
		}

		Thread t = new Thread(this);
		t.run();
	}

	public void quitGame() {
		dispose();
		System.exit(0);
	}

	public void openWaitRoom() {
		removePanels();
		panelState = PanelState.WAITROOM;
		waitRoom = new WaitRoomPanel(this);
		setContentPane(waitRoom);
	}

	public void settingsWin() {
		this.setSize(1280, 800);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의
																		// 해상도
																		// 얻기
		Dimension mf_size = super.getSize();
		int left = (screen.width / 2) - (mf_size.width / 2);
		int top = (screen.height / 2) - (mf_size.height / 2);
		super.setLocation(left, top);
	}

	public void settingsfull() {
		/*
		 * GraphicsDevice device = GraphicsEnvironment.
		 * getLocalGraphicsEnvironment().getDefaultScreenDevice();
		 * device.setFullScreenWindow(this);
		 * this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 */
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, res.width, res.height);
	}

	// WaitRoom -> GameReady
	public void openGameReady() {
		removePanels();
		panelState = PanelState.GAMEREADY;
		readyPanel = new GameReadyPanel(this);
		setContentPane(readyPanel);
	}

	// GameReady -> GameStart
	public void openGameStart(Grid grid) {
		removePanels();
		panelState = PanelState.GAMEPLAY;
		playPanel = new GamePlayPanel(grid, this);
		setContentPane(playPanel);
	}

	public void openSetting() {
		BssSettings bSet = new BssSettings();

	}

	public void openRegister() {
		register = new Register();
	}

	public void removePanels() {
		if (playPanel != null) {
			remove(playPanel);
			playPanel = null;
		}
		if (waitRoom != null) {
			remove(waitRoom);
			waitRoom = null;
		}
		if (readyPanel != null) {
			remove(readyPanel);
			readyPanel = null;
		}
	}

	public static Point getPointForCenter(int width, int height) {
		if (inst == null)
			return new Point(0, 0);

		int x = inst.getWidth() / 2 - width / 2;
		int y = (inst.getHeight() - yOffset) / 2 - height / 2;

		return new Point(x, y);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				synchronized (this) {
					mX = MouseInfo.getPointerInfo().getLocation().x;
					mY = MouseInfo.getPointerInfo().getLocation().y;

					x = getLocation().x;
					y = getLocation().y;

					mouseX = mX - x - xOffset;
					mouseY = mY - y - yOffset;
				}
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void setDefaultCursor() {
		setCursor(defaultCursor);
	}

	public void setTargetCursor() {
		setCursor(targetCursor);
	}

	public static String getUserId() {
		return userId;
	}

	public static void setUserId(String userId) {
		MainFrame.userId = userId;
	}
}
