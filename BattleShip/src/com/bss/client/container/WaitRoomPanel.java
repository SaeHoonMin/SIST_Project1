package com.bss.client.container;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bss.client.BssNetWork;
import com.bss.client.components.EnemyCount;
import com.bss.client.components.MessageDialog;
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
    private MessageDialog qd;
    private JLabel loadingLabel;
    private EnemyCount enemyCount;
    
    private JPanel warpPanel; 
    
    private Image bgImg;
    private Image warpImg  = null;
    
    private Thread t; 
    
    WaitRoomPanel(JFrame parent) 
    {		
    	inst = this;
   
    	setSize(1280,parent.getHeight());
    	setLayout(null);
    	
    	bgImg = ResContainer.bg_waitRoom;

    	warpPanel = new JPanel(){
    		@Override
    		protected void paintComponent(Graphics g) {
    			if(warpImg!=null)
    				g.drawImage(warpImg, 0, 0,getWidth(),getHeight(), this);
    			else
    			{
    				  g.setColor( getBackground() );
    			      g.fillRect(0, 0, getWidth(), getHeight());
    			}
    		}
    	};
    	warpPanel.setBackground(new Color(0,0,0,0));
    	warpPanel.setOpaque(false);
    	warpPanel.setSize(getSize());
    	add(warpPanel);
    	
    	
    	
		
    	loadingLabel = new JLabel(){
    		@Override
    		protected void paintComponent(Graphics g) {
    			// TODO Auto-generated method stub
    			super.paintComponent(g);
    			g.setColor(getBackground());
    			g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    		}
    	};
    	
    	loadingLabel.setSize(280,0);
    	loadingLabel.setBackground(BssColor.MATCHFINDING_BACK_T1);
    	loadingLabel.setOpaque(true);
    	loadingLabel.setIcon(null);
    	loadingLabel.setBorder(BorderFactory.createLineBorder(BssColor.TURQUOISE_MID_T2,2));
    	
		quickMatchBtn = new StyleButton("Quick Match");
		quickMatchBtn.setSize(280,70);
		quickMatchBtn.setLocation(MainFrame.getPointForCenter(quickMatchBtn.getWidth(), quickMatchBtn.getHeight()));
		quickMatchBtn.setLocation(quickMatchBtn.getX(),MainFrame.getInst().getHeight()-200);
		quickMatchBtn.addActionListener(this);
    	loadingLabel.setLocation(quickMatchBtn.getX(), quickMatchBtn.getY() - 5 - 210);
		
    	enemyCount = new EnemyCount();
    	enemyCount.setLocation(quickMatchBtn.getX()-enemyCount.getWidth()-5, quickMatchBtn.getY());
    	
		add(quickMatchBtn);
		add(loadingLabel);
		//add(enemyCount);
		
		setComponentZOrder(warpPanel, getComponentCount()-1);
		setComponentZOrder(quickMatchBtn, getComponentCount()-2);
		setComponentZOrder(loadingLabel, getComponentCount()-3);
		
		bgImg = ResContainer.bg_waitRoom;
		
		setOpaque(false);	
		
		BssNetWork.getInst().setWaitRoom(this);
		
	 }
    
    public void setEnemyCount(String count) {
    	enemyCount.setText(count);
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
				//**** 매치 찾기 버튼 *****//
				if(isFindingMatch == false){
					
					t = 
					new Thread(new Runnable(){

						int i=0;
						@Override
						public void run() {
							quickMatchBtn.setEnabled(false);
							
							int r, g, b, a=0;
							r = BssColor.WARP_BG.getRed();
							g = BssColor.WARP_BG.getGreen();
							b = BssColor.WARP_BG.getBlue();
							
							while(true)
							{
								warpPanel.setBackground(new Color(r,g,b,a));
								loadingLabel.setSize(280,i);
								i+=5;
								a+=5;
								try {
									Thread.sleep(3);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(i>210)
									break;
								loadingLabel.repaint();
							}

							warpImg = ResContainer.img_warp;
							warpPanel.repaint();
							loadingLabel.setIcon(ResContainer.matchFinding_Icon);
							quickMatchBtn.setEnabled(true);
						}
						
					});
					t.start();
					
					loadingLabel.setBorder(BorderFactory.createLineBorder(BssColor.ORANGE,2));
					
					BssNetWork.getInst().sendMessage(BssProtocol.MATCH_QUE_REQ, this);
					quickMatchBtn.setText("Cancel");
					isFindingMatch = true;
					return;
				}
				//********매치 취소 버튼 *******//
				else
				{
					loadingLabel.setBorder(BorderFactory.createLineBorder(BssColor.GREY_T1,2));
					loadingLabel.setBackground(BssColor.BLACK_T2);
					loadingLabel.setIcon(null);
					
					t = new Thread(new Runnable() {

						@Override
						public void run() {
							
							int i = 210;
							int r, g, b, a=250;
							r = BssColor.WARP_BG.getRed();
							g = BssColor.WARP_BG.getGreen();
							b = BssColor.WARP_BG.getBlue();

							quickMatchBtn.setEnabled(false);
							warpImg = null;
							while (true) {
								loadingLabel.setSize(280, i);
								warpPanel.setBackground(new Color(r,g,b,a));
								i -= 5;
	
								a -=5;
								try {
									Thread.sleep(3);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (i < 0)
									break;
								loadingLabel.repaint();
							}
							quickMatchBtn.setEnabled(true);
						}

					});
					t.start();
							
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
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
    
		g.drawImage(bgImg,	 0, 0,1280, 800,this);
	
	}
    

	public void gameStart()
	{
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainFrame.getInst().openGameReady();
	}
	
	public StyleButton getQuickMatchBtn()
	{
		return quickMatchBtn;
	}
    
}
