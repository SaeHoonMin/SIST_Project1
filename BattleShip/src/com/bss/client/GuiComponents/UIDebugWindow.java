package com.bss.client.GuiComponents;

import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.scene.MainFrame;

public class UIDebugWindow extends JFrame implements Runnable{
	
	Toolkit toolkit ;
	
	JLabel res;
	JLabel resText;
	JLabel cursor;
	JLabel cursorText;
	JLabel relCursor;
	JLabel relCursorText;
	
	public UIDebugWindow()
	{
		
		toolkit = Toolkit.getDefaultToolkit();
		setSize(600,300);
		setLocation((int) (toolkit.getScreenSize().getWidth()-410),0);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3,2));
		add(p);
		
		res = new JLabel("Resolution:");
		resText = new JLabel("text");
		cursor = new JLabel("Cursor Loc:");
		cursorText = new JLabel("text");
		relCursor	= new JLabel("Cursor in Window : ");
		relCursorText = new JLabel();
		
		p.add(res);
		p.add(resText);
		p.add(cursor);
		p.add(cursorText);
		p.add(relCursor);
		p.add(relCursorText);
		setVisible(true);
		
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		
		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();
		MainFrame inst = MainFrame.getInst();
		
		
		// TODO Auto-generated method stub
		while(true)
		{
			
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY	= MouseInfo.getPointerInfo().getLocation().y;
			
			int x= inst.getLocation().x;
			int y = inst.getLocation().y;
			
			int w = inst.getWidth();
			int h = inst.getHeight();
			
			buf.delete(0, buf.length());
			buf.append("x : ");
			buf.append(mouseX-x);
			buf.append(" y: ");
			buf.append(mouseY-y);
			

			buf2.delete(0, buf2.length());
			buf2.append("x : ");
			buf2.append(mouseX);
			buf2.append(" y: ");
			buf2.append(mouseY);
			
			
			resText.setText(inst.getSize().toString());
			
			if(mouseX-x < 0 || mouseY-y <0 || mouseY>h || mouseX > w)
				relCursorText.setText("Cursor isn't inside the window.");
			else
				relCursorText.setText(buf.toString());
			
			
			cursorText.setText(buf2.toString());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
