package kamisado.logic;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.json.JSONObject;

public class Square {

	private GameColor squareColor;
	private Tower tower;
	private boolean occupied;
	private boolean focused;
	private boolean possible;
	private boolean hovered;
	private int xPosition;
	private int yPosition;

	public Square(int color, Tower tower) {
		squareColor = new GameColor(color);
		this.tower = tower;
		occupied = true;
		focused = false;
		possible = false;
		hovered = false;
	}

	public Square(int color) {
		squareColor = new GameColor(color);
		tower = null;
		occupied = false;
		possible = false;
		hovered = false;
	}

	public void setColor(int color) {
		squareColor.setValue(color);
	}

	public int getColor() {
		return squareColor.getValue();
	}

	public String getColorString() {
		return squareColor.toString();
	}

	public void setTower(Tower tower) {
		this.tower = tower;
		occupied = true;
	}

	public void clearSquare() {
		setTower(null);
		occupied = false;
	}

	public Tower getTower() {
		return tower;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public Image getImage() {
		return new ImageIcon(getClass().getResource("/kamisado_media/tiles/" + getColorString() + "Tile.png"))
				.getImage();
	}

	public void setX(int x) {
		xPosition = x;
	}

	public void setY(int y) {
		yPosition = y;
	}

	public int getX() {
		return xPosition;
	}

	public int getY() {
		return yPosition;
	}

	public void setFocused() {
		focused = true;
	}

	public void defocus() {
		focused = false;
	}

	public boolean isFocused() {
		return focused;
	}

	public void setPossible(boolean possibility) {
		possible = possibility;
	}

	public boolean isPossible() {
		return possible;
	}
	
	public void dehover() {
		hovered = false;
	}
	
	public void setHovered(boolean isHovered) {
		hovered = isHovered;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("color", getColor());
		if(isOccupied())
			json.put("tower", getTower().getJSON());	
		return json;
	}

}
