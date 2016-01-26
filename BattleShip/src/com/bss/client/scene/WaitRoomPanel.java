package com.bss.client.scene;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.bss.client.GuiComponents.StyleButton;


public class WaitRoomPanel extends JPanel 
implements ActionListener{
	
    JTable table1,table2;
    DefaultTableModel model1,model2;
    JTextPane ta;
    JTextField tf;
    JComboBox box;
    StyleButton b1;
    JButton b2,b3,b4,b5;
    
    WaitRoomPanel(JFrame parent) 
    {
    	
    	setSize(1280,parent.getHeight());
    	
    	Font font1=new Font("Serif",Font.PLAIN, 20);
		String[] col1={"방이름","공개/비공개","인원"};
		String[][] row1=new String[0][3];
		model1=new DefaultTableModel(row1,col1);
		
		table1=new JTable(model1);
		table1.getTableHeader().setReorderingAllowed(false); // 두개 메뉴 안바뀌게
		table1.getTableHeader().setResizingAllowed(false);   // 사이즈 변환 못하게
		table1.getTableHeader().setBackground(Color.cyan);
		table1.getTableHeader().setFont(font1);
		table1.setRowHeight(35); // 한줄 높이
		JScrollPane js1=new JScrollPane(table1);
		
		String[] col2={"대화명","현재상태"};
		String[][] row2=new String[0][2];
		model2=new DefaultTableModel(row2,col2);
		table2=new JTable(model2);
		table2.getTableHeader().setReorderingAllowed(false); // 두개 메뉴 안바뀌게
		table2.getTableHeader().setResizingAllowed(false);   // 사이즈 변환 못하게
		table2.getTableHeader().setBackground(Color.pink);
		table2.getTableHeader().setFont(font1);
		table2.setRowHeight(35); // 한줄 높이
		JScrollPane js2=new JScrollPane(table2);
		
		ta = new JTextPane();
		ta.setEditable(false);
		JScrollPane js3 = new JScrollPane(ta);
		
		tf = new JTextField(30);
		box = new JComboBox();
		box.addItem("black");
		box.addItem("blue");
		box.addItem("green");
		box.addItem("cyan");
		box.addItem("pink");
		tf.addActionListener(this);
		
		tf.setFont(font1);
		ta.setFont(font1);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,2,5,5));
		
		add(b1 = new StyleButton("Create Room"));p.add(b2 = new JButton("방들어가기"));
		p.add(b3 = new JButton("1:1게임 신청"));p.add(b4 = new JButton("쪽지보내기"));
		p.add(b5 = new JButton("종 료"));
		
		
		b1.addActionListener((ActionListener)parent);
		b2.addActionListener((ActionListener)parent);
		b3.addActionListener((ActionListener)parent);
		b4.addActionListener((ActionListener)parent);
		b5.addActionListener(this);
		
		
		
		setLayout(null);
		
		
		js1.setBounds(10, 15, 800, 560);
		js2.setBounds(815, 15, 425, 560);
		js3.setBounds(10, 580, 800, 290);
		tf.setBounds(10, 880, 600, 45);
		box.setBounds(620,880,180,45);
		p.setBounds(815,800 ,425,125);
		b1.setBounds(815,590,425,200);
		add(js1);
		add(js2);
		add(js3);
		add(tf);
		add(box);
		add(p);
		
	 }
    // 색상 변경
 	public void initStyle(){
 		// default 색상
 		Style def=StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
 		
 		Style blue=ta.addStyle("blue", def);
 		StyleConstants.setForeground(blue, Color.blue);
 		Style pink=ta.addStyle("pink", def);
 		StyleConstants.setForeground(pink, Color.pink);
 		Style cyan=ta.addStyle("cyan", def);
 		StyleConstants.setForeground(cyan, Color.cyan);
 		Style green=ta.addStyle("green", def);
 		StyleConstants.setForeground(green, Color.green);
 	}
 	
 	// 문자열 결합
 	public void append(String msg,String color){
 		Document doc=ta.getDocument();
 		try {
 			doc.insertString(doc.getLength(), msg+"\n", ta.getStyle(color));
 		} catch (BadLocationException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==tf){
			if(e.getSource()==tf){
				String data=tf.getText();
				if(data.length()<1)
					return;
				initStyle();
				String color=box.getSelectedItem().toString();
				try{
					append(data,color);  // 문자를 계속 붙여나감
				}catch(Exception e1){
					e1.printStackTrace();
				}
				tf.setText("");
			}
		}
		if(e.getSource()==b5){
			System.exit(0);
		}
	}
	
}
