package kamisado_logic;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Square {
	
	GameColor squareColor;
	Tower tower;
	
	public Square(int color, Tower tower) {
		squareColor = new GameColor(color);
		this.tower = tower;
	}
	
	public Square(int color) {
		squareColor = new GameColor(color);
		tower = null;
	}
	
	public void setColor(int color){
		squareColor.setValue(color);
	}
	
	public int getColor(){
		return squareColor.getValue();
	}
	
	public String getColorString(){
		return squareColor.toString();
	}
	
	public void setTower(Tower tower){
		this.tower = tower;
	}
	
	public Tower getTower(){
		return tower;
	}
	
	public boolean isOccupied(){
		return (tower!=null);
	}
	
	public Image getTileImage(){
		return new ImageIcon("media/tiles/"+getColorString()+"Tile.png").getImage();
	}

}
