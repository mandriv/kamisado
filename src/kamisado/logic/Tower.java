package kamisado.logic;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author Wojciech Cichoradzki, Jakub Rudzki
 *
 */
public class Tower {
	
	private static final int RANGE_SUMO        = 5;
	private static final int RANGE_DOUBLESUMO  = 3;
	private static final int RANGE_TRIPLESUMO  = 1;

	PlayerColor player;
	GameColor color;
	int sumo;

	/**
	 * @param integer
	 *            representing 8 different colours
	 * @param integer
	 *            representing one of two players
	 */
	public Tower(int towerColor, int playerNum) {
		color = new GameColor(towerColor);
		player = new PlayerColor(playerNum);
		sumo = 0;
	}
	
	public Tower(int towerColor, int playerNum, int lvl) {
		color = new GameColor(towerColor);
		player = new PlayerColor(playerNum);
		sumo = lvl;
	}

	public Image getImage() {
		return new ImageIcon(getClass()
				.getResource("/kamisado_media/pieces/" + color.toString() + "Tower_" + player.toString() + ".png"))
						.getImage();
	}
	
	public int getSumoLevel() {
		return sumo;
	}
	
	public int getRange() {
		switch(sumo) {
			case 1: return RANGE_SUMO;
			case 2: return RANGE_DOUBLESUMO;
			case 3: return RANGE_TRIPLESUMO;
			default: return 7;
		}
	}
	
	public Image getSumoImage() {
		return new ImageIcon(getClass()
				.getResource("/kamisado_media/sumo/sumo" + getSumoLevel() + "_" + player.toString() + ".png"))
						.getImage();
	}

	public int getOwnerColorValue() {
		return player.value();
	}

	public GameColor getColor() {
		return color;
	}
	
	public int getColorValue() {
		return color.getValue();
	}
	
	public void upgrade() {
		if(sumo < 3)
			sumo++;
	}

}
