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
	
	public static Image target_cursor;
	
	public static Image bg_login;
	public static Image bg_waitRoom;
	
	public static Image img_warp;
	
	public static Image matchFinding;
	
	public static Image shipContainer;
	
	public static Image ourForces;
	
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
	public static Image ship2v_r;
	public static Image ship2h_r;
	public static Image ship3v_r1;
	public static Image ship3h_r1;
	public static Image ship3v_r2;
	public static Image ship3h_r2;
	public static Image ship4v_r;
	public static Image ship4h_r;
	public static Image ship5v_r;
	public static Image ship5h_r;
	
	public static Image ship2v_b;
	public static Image ship2h_b;
	public static Image ship3v_b2;
	public static Image ship3h_b2;
	public static Image ship3v_b1;
	public static Image ship3h_b1;
	public static Image ship4v_b;
	public static Image ship4h_b;
	public static Image ship5v_b;
	public static Image ship5h_b;
	
	/* Icons */
	public static ImageIcon ship2v_r_icon;
	public static ImageIcon ship2h_r_icon;
	public static ImageIcon ship3v_r2_icon;
	public static ImageIcon ship3h_r2_icon;
	public static ImageIcon ship3v_r1_icon;
	public static ImageIcon ship3h_r1_icon;
	public static ImageIcon ship4v_r_icon;
	public static ImageIcon ship4h_r_icon;
	public static ImageIcon ship5v_r_icon;
	public static ImageIcon ship5h_r_icon;
	
	public static ImageIcon ship2v_b_icon;
	public static ImageIcon ship2h_b_icon;
	public static ImageIcon ship3v_b2_icon;
	public static ImageIcon ship3h_b2_icon;
	public static ImageIcon ship3v_b1_icon;
	public static ImageIcon ship3h_b1_icon;
	public static ImageIcon ship4v_b_icon;
	public static ImageIcon ship4h_b_icon;
	public static ImageIcon ship5v_b_icon;
	public static ImageIcon ship5h_b_icon;
	
	public static ImageIcon matchFinding_Icon;
	
	public ResContainer()
	{
		toolKit = Toolkit.getDefaultToolkit();
		
		target_cursor = toolKit.getImage(ResLoader.getResURL("images/target_cursor.png"));
		
		//frame background images
		bg_login = toolKit.getImage(ResLoader.getResURL("images/bg.jpg"));
		bg_waitRoom = toolKit.getImage(ResLoader.getResURL("images/1.jpg"));
		img_warp = toolKit.getImage(ResLoader.getResURL("images/warp.gif"));
		
		ourForces = toolKit.getImage(ResLoader.getResURL("images/ourForces.png"));
		
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
		ship2v_b = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_2_v.png"));
		ship2h_b = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_2_h.png"));
		
		ship3v_b1 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_3_1_v.png"));
		ship3h_b1 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_3_1_h.png"));
		ship3v_b2 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_3_2_v.png"));
		ship3h_b2 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_3_2_h.png"));
		
		ship4v_b = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_4_v.png"));
		ship4h_b = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_4_h.png"));
		
		ship5v_b = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_5_v.png"));
		ship5h_b = toolKit.getImage(ResLoader.getResURL("images/ships/ship_b_5_h.png"));
		
		ship2v_r = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_2_v.png"));
		ship2h_r = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_2_h.png"));
		
		ship3v_r1 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_3_1_v.png"));
		ship3h_r1 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_3_1_h.png"));
		ship3v_r2 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_3_2_v.png"));
		ship3h_r2 = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_3_2_h.png"));
		
		ship4v_r = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_4_v.png"));
		ship4h_r = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_4_h.png"));
		
		ship5v_r = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_5_v.png"));
		ship5h_r = toolKit.getImage(ResLoader.getResURL("images/ships/ship_r_5_h.png"));
		
		//Ship Icons
		ship2v_b_icon = new ImageIcon(ship2v_b);
		ship2h_b_icon = new ImageIcon(ship2h_b);
		
		ship3v_b1_icon = new ImageIcon(ship3v_b1);
		ship3h_b1_icon = new ImageIcon(ship3h_b1);
		ship3v_b2_icon = new ImageIcon(ship3v_b2);
		ship3h_b2_icon = new ImageIcon(ship3h_b2);
		
		ship4v_b_icon = new ImageIcon(ship4v_b);
		ship4h_b_icon = new ImageIcon(ship4h_b);
		
		ship5v_b_icon = new ImageIcon(ship5v_b);
		ship5h_b_icon = new ImageIcon(ship5h_b);
		
		ship2v_r_icon = new ImageIcon(ship2v_r);
		ship2h_r_icon = new ImageIcon(ship2h_r);
		
		ship3v_r1_icon = new ImageIcon(ship3v_r1);
		ship3h_r1_icon = new ImageIcon(ship3h_r1);
		ship3v_r2_icon = new ImageIcon(ship3v_r2);
		ship3h_r2_icon = new ImageIcon(ship3h_r2);
		
		ship4v_r_icon = new ImageIcon(ship4v_r);
		ship4h_r_icon = new ImageIcon(ship4h_r);
		
		ship5v_r_icon = new ImageIcon(ship5v_r);
		ship5h_r_icon = new ImageIcon(ship5h_r);
		
		
		matchFinding =  toolKit.getImage(ResLoader.getResURL("images/280_210Match_Find.gif"));
		
		matchFinding_Icon = new ImageIcon(matchFinding);
	}
	
}
