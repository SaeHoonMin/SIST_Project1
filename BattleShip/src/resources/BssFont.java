package resources;

import java.awt.Font;

public class BssFont {
	
	public static final Font ARIAL_B16 = new Font("Arial", Font.BOLD,16);
	
	public static final Font []  ARIAL_ARR16_20 = new Font[5];
	
	
	static
	{
		for(int i=0;i<5;i++)
			ARIAL_ARR16_20[i] = new Font("Arial",Font.BOLD,16+i);
	}

}
