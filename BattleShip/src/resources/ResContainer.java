package resources;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/* 시작 시 게임에 필요한 모든 리소스를 적재하고 가져다 쓸 수 있도록 만들자 .*/

public class ResContainer {
	
	private Toolkit toolKit ;
	
	/*
	 * Tile
	 */
	/* Images... */
	public static Image bg_login;
	public static Image bg_waitRoom;
	
	public static Image shipContainer;
	
	public static Image tile;
	public static Image tile_pressed;
	public static Image tile_over;
	public static Image tile_located;
	public static Image tile_invalid;
	public static Image tile_reserved;
	
	/* Icons... */
	public static ImageIcon tile_icon;
	public static ImageIcon tile_pressed_icon;
	public static ImageIcon tile_over_icon;
	public static ImageIcon tile_located_icon;
	public static ImageIcon tile_invalid_icon;
	public static ImageIcon tile_reserved_icon;

	/*
	 * Ship 
	 */
	/* Images */
	public static Image ship2v;
	public static Image ship2h;
	public static Image ship3v;
	public static Image ship3h;
	public static Image ship4v;
	public static Image ship4h;
	public static Image ship5v;
	public static Image ship5h;
	
	
	/* Icons */
	public static ImageIcon ship2v_icon;
	public static ImageIcon ship2h_icon;
	public static ImageIcon ship3v_icon;
	public static ImageIcon ship3h_icon;
	public static ImageIcon ship4v_icon;
	public static ImageIcon ship4h_icon;
	public static ImageIcon ship5v_icon;
	public static ImageIcon ship5h_icon;
	
	public ResContainer()
	{
		toolKit = Toolkit.getDefaultToolkit();
		
		//frame background images
		bg_login = toolKit.getImage(ResLoader.getResURL("images/bg.jpg"));
		bg_waitRoom = toolKit.getImage(ResLoader.getResURL("images/1.jpg"));
		
		//Tile Images
		tile = toolKit.getImage(ResLoader.getResURL("images/Tile.png"));
		tile_pressed =  toolKit.getImage(ResLoader.getResURL("images/Tile_Black.png"));
		tile_over =  toolKit.getImage(ResLoader.getResURL("images/Tile_Pink.png"));
		tile_located =  toolKit.getImage(ResLoader.getResURL("images/Tile_Blue.png"));
		tile_invalid =  toolKit.getImage(ResLoader.getResURL("images/Tile_Red.png"));
		tile_reserved = toolKit.getImage(ResLoader.getResURL("images/Tile_White.png"));
		//Tile Icons
		tile_icon = new ImageIcon(tile);
		tile_pressed_icon = new ImageIcon(tile_pressed);
		tile_over_icon = new ImageIcon(tile_over);
		tile_located_icon = new ImageIcon(tile_located);
		tile_invalid_icon = new ImageIcon(tile_invalid);
		tile_reserved_icon = new ImageIcon(tile_reserved);
		
		
		//Ship Images
		ship2v = toolKit.getImage(ResLoader.getResURL("images/Ship_Temp_V.png"));
		ship2h = toolKit.getImage(ResLoader.getResURL("images/Ship_Temp_H.png"));
		
		ship3v = toolKit.getImage(ResLoader.getResURL("images/ships/ship3_1_v.png"));
		ship3h = toolKit.getImage(ResLoader.getResURL("images/ships/ship3_1_h.png"));
		
		ship4v = toolKit.getImage(ResLoader.getResURL("images/ships/ship4_1_v.png"));
		ship4h = toolKit.getImage(ResLoader.getResURL("images/ships/ship4_1_h.png"));
		
		ship5v = toolKit.getImage(ResLoader.getResURL("images/ships/ship5_1_v.png"));
		ship5h = toolKit.getImage(ResLoader.getResURL("images/ships/ship5_1_h.png"));
		
		//Ship Icons
		ship2v_icon = new ImageIcon(ship2v);
		ship2h_icon = new ImageIcon(ship2h);
		
		ship3v_icon = new ImageIcon(ship3v);
		ship3h_icon = new ImageIcon(ship3h);
		
		ship4v_icon = new ImageIcon(ship4v);
		ship4h_icon = new ImageIcon(ship4h);
		
		ship5v_icon = new ImageIcon(ship5v);
		ship5h_icon = new ImageIcon(ship5h);
	}
	
}
