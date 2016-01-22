package 연습장;
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
        String str = "이 메세지가 이미지파일로 만들어집니다.";
        // java.awt.image.BufferedImage.BufferedImage(int width, int height, int imageType)
        //이미지 객체 생성
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics(); //이미지의 그래픽 객체 얻어옴,
                                          //Graphics객체를 통해 색상을 입히거나, 글자를 쓰거나
        g.setColor(Color.white); //색상을 흰색으로 지정.
        g.fillRect(0, 0, 400, 400); // 사각형을 그림. 즉 흰색으로 채워진 사각형
        g.setColor(Color.blue); //색상을 블루로 지정
        g.drawString(str, 10, 100); //str에 있는 문자열을 x좌표 10, y좌표 100에 그림 
        g.setColor(Color.red);
        g.fillOval(150,150, 100, 100); //원형 그리기  
        //void java.awt.Graphics.fillOval(int x, int y, int width, int height)
        OutputStream out = new FileOutputStream("board.jpg"); //파일로 출력하기위해 파일출력스트림 생성
        ImageIO.write(image, "JPG",out); //이미지 출력! , 이미지를 파일출력스트림을 통해 JPG타입으로 출력
        out.close();  //출력스트림 닫기      
    }

}