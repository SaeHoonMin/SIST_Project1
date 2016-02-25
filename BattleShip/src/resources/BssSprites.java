package resources;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.bss.client.gameObjects.AnimName;

public class BssSprites {
	
	private static String spriteRoot = "images/sprites/";
	
	private static final ArrayList<ImageIcon> EXPLOSION_1 = new ArrayList<ImageIcon>();
	private static final ArrayList<ImageIcon> EXPLOSION_2 = new ArrayList<ImageIcon>();
	
	private static final ArrayList<ImageIcon> LOADING_GIF = new ArrayList<ImageIcon>();
	
	static
	{
		for(int i=1;i<=33;i++)
		{
			ImageIcon icon = new ImageIcon(ResLoader.getResURL(spriteRoot+"explosion1/"+i+".png") );
			icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			EXPLOSION_1.add(icon);
		}
		for(int i=1;i<=36;i++)
		{
			ImageIcon icon = new ImageIcon(ResLoader.getResURL(spriteRoot+"explosion2/explosion2_"+i+".png"));
			icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			EXPLOSION_2.add(icon);
		}
		
		for(int i=1;i<=9;i++)
		{
			ImageIcon icon = new ImageIcon(ResLoader.getResURL(spriteRoot+"Loading/loading_gif_p"+i+".jpg"));
			icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			LOADING_GIF.add(icon);
		}
		
	}
	
	public static ArrayList<ImageIcon> getAnimSprites(AnimName name)
	{
		
		switch(name)
		{
		case EXPLOSION_1:
			return EXPLOSION_1;
		case EXPLOSION_2:
			return EXPLOSION_2;
		case LOADING_GIF:
			return LOADING_GIF;
		default:
			return null;
		}
	}
}
