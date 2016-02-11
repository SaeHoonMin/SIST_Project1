package com.bss.client.container;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.QueueDialog;
import com.bss.client.components.StyleButton;
import com.bss.client.gameObjects.AnimName;
import com.bss.common.BssDebug;
import com.bss.common.BssProtocol;
import com.sun.xml.internal.ws.api.ResourceLoader;

import resources.BssColor;
import resources.BssSprites;
import resources.ResContainer;
import resources.ResLoader;


public class WaitRoomPanel extends JPanel implements ActionListener{
	
	public static WaitRoomPanel inst ;

    private boolean actionAllowed = true;
	private StyleButton quickMatchBtn;
    private QueueDialog qd;
    
    WaitRoomPanel(JFrame parent) 
    {
    	inst = this;
    	
    	setSize(1280,parent.getHeight());
    	setLayout(null);
		
		quickMatchBtn = new StyleButton("Quick Match");
		quickMatchBtn.setSize(330,100);
		System.out.println("quickMatchBtn size : "+quickMatchBtn.getSize());
		quickMatchBtn.setLocation(MainFrame.getPointForCenter(quickMatchBtn.getWidth(), quickMatchBtn.getHeight()));
		quickMatchBtn.addActionListener(this);
		add(quickMatchBtn);
		
		setOpaque(false);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == quickMatchBtn && actionAllowed==true)
		{
			actionAllowed = false;
			if(BssDebug.GAMEREADY_TESTING)
			{
				gameStart(); return;
			}

			if(BssNetWork.getInst()!=null)
			{	
				BssNetWork.getInst().sendMessage(BssProtocol.MATCH_QUE_REQ, this);
				qd = new QueueDialog();
				return;
			}
			else
			{
				System.out.println("BssNetWork is null.");
				return;
			}
		}
	}
	
    @Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		arg0.drawImage(ResContainer.bg_waitRoom, 0, 0, this);
		setOpaque(false);
	}
    
    public boolean isActionAllowed() {
 		return actionAllowed;
 	}

 	public void setActionAllowed(boolean actionAllowed) {
 		this.actionAllowed = actionAllowed;
 	}
    
	public void gameStart()
	{
		//Error
		if(qd!=null)
			qd.dispose();
		
		MainFrame.getInst().openGameReady();
	}
    
}
