package kamisado;

import java.awt.*;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * @author Wojciech Cichoradzki, Jakub Rudzki
 *
 */
public class Tower {
	
	private int x;
	private int y;
	private String color;
	private String playerColor;
	private static final String[] supportedColors= {"orange","blue","turquoise","pink","yellow","red","green","brown"};
	private static final String[] supportedPlayerColors= {"B","W"};
	
	/**
	 * @param X coordinate on board
	 * @param Y coordinate on board
	 * @param Color of a tower (supported: "orange","blue","turquoise","pink","yellow","red","green","brown")
	 * @param Color of player's towers (supported: "B" or "W")
	 */
	public Tower(int x, int y, String color, String playerColor){
		if(!Arrays.asList(supportedColors).contains(color) || !Arrays.asList(supportedPlayerColors).contains(playerColor))
			throw new IllegalArgumentException("Unsupported color!");
		this.x = x;
		this.y = y;
		this.color = color;
		this.playerColor = playerColor;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Image getImage(){
		StringBuilder fileName = new StringBuilder("src/");
		switch(this.color){
			case "blue": fileName.append("blue"); break;
			case "brown": fileName.append("brown"); break;
			case "green": fileName.append("green"); break;
			case "orange": fileName.append("orange"); break;
			case "pink": fileName.append("pink"); break;
			case "red": fileName.append("red"); break;
			case "turquoise": fileName.append("turquoise"); break;
			case "yellow": fileName.append("yellow"); break;
			default : throw new IllegalStateException("Invalid color exception!");
		}
		fileName.append("Tower");
		fileName.append(playerColor);
		fileName.append(".png");
		System.out.println(fileName.toString());
		return new ImageIcon(fileName.toString()).getImage();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

}
