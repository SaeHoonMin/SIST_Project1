package 연습장;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class AppletR extends Applet implements WindowListener {

   public static void main(String[] args) {
      AppletR app = new AppletR();
      Frame w = new Frame("정현의 어플리케이션");
      w.addWindowListener(app);
      w.add("Center", app);
      w.setSize(500, 500);
      w.setVisible(true);
      app.init();
      app.start();
   }

   public void paint(Graphics g) {
      super.paint(g);
      g.drawString("Welcome to my place", 10, 10);
	
		
   }

   public void windowClosing(WindowEvent e) {
      System.exit(0);	
   }
   

   public void windowActivated(WindowEvent e) {}
   public void windowClosed(WindowEvent e) {}
   public void windowDeactivated(WindowEvent e) {}
   public void windowDeiconified(WindowEvent e) {}
   public void windowIconified(WindowEvent e) {}
   public void windowOpened(WindowEvent e) {}

}

