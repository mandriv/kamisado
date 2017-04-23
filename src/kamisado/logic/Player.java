package kamisado.logic;

public class Player {
	
	private PlayerColor color;
	private String name;
	private boolean ai;
	private int moveCount;
	private int score;
	private int aiDifficulty;

	public Player(int playerColor, String playerName) {
		color = new PlayerColor(playerColor);
		name = playerName;
		ai = false;
		moveCount = 0;
		score = 0;
	}
	
	public Player(int playerColor, String playerName, int aiDifficulty) {
		color = new PlayerColor(playerColor);
		name = playerName;
		ai = true;
		this.aiDifficulty = aiDifficulty;
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
	
	public boolean isAI() {
		return ai;
	}
	
	public int getDifficulty() {
		return aiDifficulty;
	}

}
