package resources;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/* ���� �� ���ӿ� �ʿ��� ��� ���ҽ��� �����ϰ� ������ �� �� �ֵ��� ������ .*/

public class ResContainer {
	
	Toolkit toolKit ;
	
	/* Images... */
	public static Image bg_login;
	public static Image shipContainer;
	
	public static Image tile;
	public static Image tile_pressed;
	public static Image tile_over;
	public static Image tile_valid;
	public static Image tile_invalid;
	
	
	public static Image ship2v;
	public static Image ship2h;
	
	/* Icons... */
	public static ImageIcon tile_icon;
	public static ImageIcon tile_pressed_icon;
	public static ImageIcon tile_over_icon;
	public static ImageIcon tile_valid_icon;
	public static ImageIcon tile_invalid_icon;
	
	public static ImageIcon ship2v_icon;
	public static ImageIcon ship2h_icon;
	
	public ResContainer()
	{
		toolKit = Toolkit.getDefaultToolkit();
		
		//frame background images
		bg_login = toolKit.getImage(ResLoader.getResURL("images/bg.jpg"));
		
		//Tile Images..
		tile = toolKit.getImage(ResLoader.getResURL("images/Tile.png"));
		tile_pressed =  toolKit.getImage(ResLoader.getResURL("images/Tile_Black.png"));
		tile_over =  toolKit.getImage(ResLoader.getResURL("images/Tile_Pink.png"));
		tile_valid =  toolKit.getImage(ResLoader.getResURL("images/Tile_Blue.png"));
		tile_invalid =  toolKit.getImage(ResLoader.getResURL("images/Tile_Red.png"));
		
		tile_icon = new ImageIcon(tile);
		tile_pressed_icon = new ImageIcon(tile_pressed);
		tile_over_icon = new ImageIcon(tile_over);
		tile_valid_icon = new ImageIcon(tile_valid);
		tile_invalid_icon = new ImageIcon(tile_invalid);
		
		
		ship2v = toolKit.getImage(ResLoader.getResURL("images/Ship_Temp_V.png"));
		ship2h = toolKit.getImage(ResLoader.getResURL("images/Ship_Temp_H.png"));
		
		ship2v_icon = new ImageIcon(ship2v);
		ship2h_icon = new ImageIcon(ship2h);
		
		
	}
	
}
