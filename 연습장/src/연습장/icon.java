package ������;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;


public class icon {
    
    public static void main(String[] args) throws IOException {
        String str = "�� �޼����� �̹������Ϸ� ��������ϴ�.";
        // java.awt.image.BufferedImage.BufferedImage(int width, int height, int imageType)
        //�̹��� ��ü ����
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics(); //�̹����� �׷��� ��ü ����,
                                          //Graphics��ü�� ���� ������ �����ų�, ���ڸ� ���ų�
        g.setColor(Color.white); //������ ������� ����.
        g.fillRect(0, 0, 400, 400); // �簢���� �׸�. �� ������� ä���� �簢��
        g.setColor(Color.blue); //������ ���� ����
        g.drawString(str, 10, 100); //str�� �ִ� ���ڿ��� x��ǥ 10, y��ǥ 100�� �׸� 
        g.setColor(Color.red);
        g.fillOval(150,150, 100, 100); //���� �׸���  
        //void java.awt.Graphics.fillOval(int x, int y, int width, int height)
        OutputStream out = new FileOutputStream("board.jpg"); //���Ϸ� ����ϱ����� ������½�Ʈ�� ����
        ImageIO.write(image, "JPG",out); //�̹��� ���! , �̹����� ������½�Ʈ���� ���� JPGŸ������ ���
        out.close();  //��½�Ʈ�� �ݱ�      
    }

}