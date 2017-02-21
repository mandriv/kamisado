package kamisado_logic;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * @author Wojciech Cichoradzki, Jakub Rudzki
 *
 */
public class Tower {
	
	Player player;
	GameColor color;
	
	/**
	 * @param integer representing 8 different colours
	 * @param integer representing one of two players
	 */
	public Tower(int towerColor, int playerNum){
		color = new GameColor(towerColor);
		player = new Player(playerNum);
	}

	public Image getImage(){
		return new ImageIcon(getClass().getResource("/kamisado_gui/media/pieces/"+color.toString()+"Tower_"+player.toString()+".png")).getImage();
	}
	
	public int getOwner(){
		return player.getWhoseTurn();
	}

}
