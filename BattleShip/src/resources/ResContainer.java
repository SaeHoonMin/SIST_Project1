package resources;

import java.awt.Image;
import java.awt.Toolkit;

/* 시작 시 게임에 필요한 모든 리소스를 적재하고 가져다 쓸 수 있도록 만들자 .*/

public class ResContainer {
	
	Toolkit toolKit ;
	
	/* Images... */
	public static Image bg_login;
	public static Image shipContainer;
	
	public static Image tile;
	public static Image tile_pressed;
	public static Image tile_over;
	public static Image tile_possible;
	public static Image tile_invalid;
	
	/* Icons... */
	
	
	public ResContainer()
	{
		toolKit = Toolkit.getDefaultToolkit();
		
		//frame background images
		bg_login = toolKit.getImage(ResLoader.getResURL("images/bg.jpg"));
		
		//Tile Images..
		tile = toolKit.getImage(ResLoader.getResURL("images/Tile.png"));
		tile_pressed =  toolKit.getImage(ResLoader.getResURL("images/Tile_Black.png"));
		tile_over =  toolKit.getImage(ResLoader.getResURL("images/Tile_Pink.png"));
		tile_possible =  toolKit.getImage(ResLoader.getResURL("images/Tile_Blue.png"));
		tile_invalid =  toolKit.getImage(ResLoader.getResURL("images/Tile_Red.png"));
		
		
		
	}
	
}
