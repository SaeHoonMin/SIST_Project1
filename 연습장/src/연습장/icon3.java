package ø¨Ω¿¿Â;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class icon3 {
    
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public icon3(){
      prepareGUI();
   }

   public static void main(String[] args){
	   icon3  swingControlDemo = new icon3();      
      swingControlDemo.showImageIconDemo();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Java Swing Examples");
      mainFrame.setSize(1000,1000);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    

      statusLabel.setSize(300,150);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   // Returns an ImageIcon, or null if the path was invalid. 
   private static ImageIcon createImageIcon(String path,
      String description) {
      java.net.URL imgURL = icon3.class.getResource(path);
      if (imgURL != null) {
         return new ImageIcon(imgURL, description);
      } else {            
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }


   private void showImageIconDemo(){
      headerLabel.setText("Control in action: ImageIcon"); 
	  
      ImageIcon icon = createImageIcon("board.jpg","Java");

      JLabel commentlabel = new JLabel("", icon,JLabel.CENTER);

      controlPanel.add(commentlabel);

      mainFrame.setVisible(true);  
   }
}