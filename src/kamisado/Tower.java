package kamisado;

import java.awt.*;

import javax.swing.ImageIcon;

public class Tower {
	
	private int x;
	private int y;
	private String color;
	private String playerColor;
	
	public Tower(int x, int y, String color, String playerColor){
		this.x = x;
		this.y = y;
		this.color = color;
		this.playerColor = playerColor;
	}
	
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
			case "yellow": fileName.append("yellow");
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
