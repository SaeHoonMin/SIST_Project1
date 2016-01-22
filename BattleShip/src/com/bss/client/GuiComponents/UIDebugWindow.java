package com.bss.client.GuiComponents;

import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UIDebugWindow extends JFrame implements Runnable{
	
	Toolkit toolkit ;
	
	JLabel res;
	JLabel resText;
	JLabel cursor;
	JLabel cursorText;
	
	public UIDebugWindow()
	{
		
		toolkit = Toolkit.getDefaultToolkit();
		setSize(400,400);
		setLocation((int) (toolkit.getScreenSize().getWidth()-410),0);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,2));
		add(p);
		
		res = new JLabel("Resolution:");
		resText = new JLabel("text");
		cursor = new JLabel("Cursor Loc:");
		cursorText = new JLabel("text");
		
		p.add(res);
		p.add(resText);
		p.add(cursor);
		p.add(cursorText);
		setVisible(true);
		
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			cursorText.setText(MouseInfo.getPointerInfo().getLocation().toString());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
