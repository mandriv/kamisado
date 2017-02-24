package kamisado_logic;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * @author Wojciech Cichoradzki, Jakub Rudzki
 *
 */
public class Tower {
	
	PlayerColor player;
	GameColor color;
	
	/**
	 * @param integer representing 8 different colours
	 * @param integer representing one of two players
	 */
	public Tower(int towerColor, int playerNum){
		color = new GameColor(towerColor);
		player = new PlayerColor(playerNum);
	}

	public Image getImage(){
		return new ImageIcon(getClass().getResource("/kamisado_media/pieces/"+color.toString()+"Tower_"+player.toString()+".png")).getImage();
	}
	
	public int getOwner(){
		return player.getWhoseTurn();
	}
	
	public int getColorValue(){
		return color.getValue();
	}

}
