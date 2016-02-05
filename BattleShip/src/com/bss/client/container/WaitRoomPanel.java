package com.bss.client.container;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bss.client.BssNetWork;
import com.bss.client.components.QueueDialog;
import com.bss.client.components.StyleButton;
import com.bss.client.components.TurnPanel;
import com.bss.common.BssDebug;
import com.bss.common.BssProtocol;

import resources.ResContainer;


public class WaitRoomPanel extends JPanel implements ActionListener{
	
    @Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		arg0.drawImage(ResContainer.bg_waitRoom, 0, 0, this);
		setOpaque(false);
	}

	JTable table1,table2;
    DefaultTableModel model1,model2;
    JTextArea ta;
    JTextField tf;
    JComboBox box;
    
    StyleButton b1;

    QueueDialog qd;
    
    WaitRoomPanel(JFrame parent) 
    {
    	
    	setSize(1280,parent.getHeight());
    	
		String[] col1={"방이름","공개/비공개","인원"};
		String[][] row1=new String[0][3];
		model1=new DefaultTableModel(row1,col1);
		
		
		table1=new JTable(model1);
		
		JScrollPane js1=new JScrollPane(table1);
		
		String[] col2={"ID","대화명","성별","위치"};
		String[][] row2=new String[0][4];
		model2=new DefaultTableModel(row2,col2);
		table2=new JTable(model2);
		JScrollPane js2=new JScrollPane(table2);
		
		
		ta = new JTextArea();
		JScrollPane js3 = new JScrollPane(ta);
		
		
		tf = new JTextField();
		box = new JComboBox();
		box.addItem("black");
		box.addItem("blue");
		box.addItem("green");
		box.addItem("cyan");
		box.addItem("pink");
		
		JPanel p = new JPanel();
		
		p.setLayout(new GridLayout(1,1,5,5));
		
		b1 = new StyleButton("Quick Match");
		b1.setBounds(516,470,265,80);
		b1.addActionListener(this);
		add(b1);
		
		setLayout(null);
		js1.setBounds(10, 15, 500, 350);
		js2.setBounds(10, 370, 500, 180);
		js3.setBounds(515, 15, 265, 300);
		tf.setBounds(515, 320, 160, 30);
		box.setBounds(680,320,90,30);
		p.setBounds(515,470	,265,80);
		add(js1);
		add(js2);
		add(js3);
		add(tf);
		add(box);
	
		setOpaque(false);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == b1)
		{
			System.out.println("sending message match requeset");
			if(BssNetWork.getInst()!=null)
				BssNetWork.getInst().sendMessage(BssProtocol.MATCH_QUE_REQ, this);
			else
				System.out.println("BssNetWork is null.");
			
			if(BssDebug.TESTING)
				gameStart();
			
			
			qd = new QueueDialog();
		}
	}
    
	public void gameStart()
	{
		//Error
		if(qd!=null)
			qd.dispose();
		
		MainFrame.getInst().openGameReady();
	}
    
}
