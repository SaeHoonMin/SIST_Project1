package ������;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BeautyClass extends JFrame{
	Image img = null;
	
	BeautyClass() {
		try{
			File sourceimage = new File("C:\\Users\\aaa\\Desktop\\����\\pixed.jpg");
			img = ImageIO.read(sourceimage);
		}catch(IOException e){
			System.out.println("�̹��� �����̾����ϴ�.");
		}
	
	init();
	setTitle("����");
	setSize(700,300);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	setResizable(true);
	
	}
	public static void main(String[] args) {
		BeautyClass im = new BeautyClass();

	}
public void init(){
	JLabel label = new JLabel(new ImageIcon(img));
	add(label);
	
	}    
}
