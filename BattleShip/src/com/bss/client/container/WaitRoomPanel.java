package com.bss.client.container;

import java.awt.Color;
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
	
	private boolean isFindingMatch = false;

	private StyleButton quickMatchBtn;
    private QueueDialog qd;
    private JLabel loadingLabel;
    
    private JPanel panel; 
    
    WaitRoomPanel(JFrame parent) 
    {
    	inst = this;
    	
    	setSize(1280,parent.getHeight());
    	setLayout(null);
		
    	loadingLabel = new JLabel(){
    		@Override
    		protected void paintComponent(Graphics g) {
    			// TODO Auto-generated method stub
    			super.paintComponent(g);
    			g.setColor(getBackground());
    			g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    		}
    	};
    	
    	loadingLabel.setSize(280,210);
    	loadingLabel.setBackground(BssColor.MATCHFINDING_BACK_T1);
    	loadingLabel.setOpaque(true);
    	loadingLabel.setIcon(null);
    	loadingLabel.setBorder(BorderFactory.createLineBorder(BssColor.TURQUOISE_MID_T2,2));
    	
		quickMatchBtn = new StyleButton("Quick Match");
		quickMatchBtn.setSize(280,70);
		quickMatchBtn.setLocation(MainFrame.getPointForCenter(quickMatchBtn.getWidth(), quickMatchBtn.getHeight()));
		quickMatchBtn.addActionListener(this);
    	loadingLabel.setLocation(quickMatchBtn.getX(), quickMatchBtn.getY() - 5 - 210);
		
		add(quickMatchBtn);
		add(loadingLabel);
		
		setOpaque(false);	
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == quickMatchBtn )
		{
			if(BssDebug.GAMEREADY_TESTING)
			{
				gameStart(); return;
			}

			if(BssNetWork.getInst()!=null)
			{	
				if(isFindingMatch == false){
					System.out.println("clicked");
					loadingLabel.setBorder(BorderFactory.createLineBorder(BssColor.ORANGE,2));
					loadingLabel.setIcon(ResContainer.matchFinding_Icon);
					BssNetWork.getInst().sendMessage(BssProtocol.MATCH_QUE_REQ, this);
					quickMatchBtn.setText("Cancel");
					isFindingMatch = true;
					return;
				}
				else
				{
					loadingLabel.setBorder(BorderFactory.createLineBorder(BssColor.TURQUOISE_MID,2));
					loadingLabel.setIcon(null);
					loadingLabel.setVisible(false);
					loadingLabel.setVisible(true);
					quickMatchBtn.setText("Quick Match");
					BssNetWork.getInst().sendMessage(BssProtocol.MATCH_QUE_CANCLED,null);
					isFindingMatch = false;
					return;
				}
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
    

	public void gameStart()
	{
		//Error
		if(qd!=null)
			qd.dispose();
		
		MainFrame.getInst().openGameReady();
	}
	
	public StyleButton getQuickMatchBtn()
	{
		return quickMatchBtn;
	}
    
}
