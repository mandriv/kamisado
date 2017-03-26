package kamisado_logic;

public class Player {
	
	private PlayerColor color;
	private String name;
	private boolean ai;
	private int moveCount;
	private int score;

	public Player(int playerColor, String playerName, boolean isAI) {
		color = new PlayerColor(playerColor);
		name = playerName;
		ai = isAI;
		moveCount = 0;
		score = 0;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incrementScore() {
		score++;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void incrementMoveCount() {
		moveCount++;
	}
	
	public void resetMoveCount() {
		moveCount = 0;
	}
	
	public int getColorValue() {
		return color.value();
	}

}
