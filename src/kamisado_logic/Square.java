package kamisado_logic;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Square {
	
	GameColor squareColor;
	Tower tower;
	boolean occupied;
	int xPosition;
	int yPosition;
	
	public Square(int color, Tower tower) {
		squareColor = new GameColor(color);
		this.tower = tower;
		occupied = true;
	}
	
	public Square(int color) {
		squareColor = new GameColor(color);
		tower = null;
		occupied = false;
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
		occupied = true;
	}
	
	public void clearSquare(){
		setTower(null);
		occupied = false;
	}
	
	public Tower getTower(){
		return tower;
	}
	
	public boolean isOccupied(){
		return occupied;
	}
	
	public Image getImage(){
		return new ImageIcon("media/tiles/"+getColorString()+"Tile.png").getImage();
	}
	
	public void setX(int x){
		xPosition = x;
	}
	
	public void setY(int y){
		yPosition = y;
	}
	
	public int getX(){
		return xPosition;
	}
	
	public int getY(){
		return yPosition;
	}

}
