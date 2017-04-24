package kamisado.logic;

import org.json.JSONObject;

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
		ai = aiDifficulty > 0;
		this.aiDifficulty = aiDifficulty;
		moveCount = 0;
		score = 0;
	}
	
	public Player(int playerColor, String playerName, int aiDifficulty, int moveCount, int score) {
		color = new PlayerColor(playerColor);
		name = playerName;
		ai = aiDifficulty > 0;
		this.aiDifficulty = aiDifficulty;
		this.moveCount = moveCount;
		this.score = score;
	}
	
	public Player(Player p) {
		color = p.color;
		name  = p.name;
		ai    = p.ai;
		moveCount    = p.moveCount; 
		score = p.score;
		aiDifficulty = p.aiDifficulty;
	}
	
	public Player(JSONObject json) {
		
		color = new PlayerColor(json.getInt("color"));
		name  = json.getString("name");
		aiDifficulty = json.getInt("aiDifficulty");
		if(aiDifficulty!=0)
			ai = true;
		moveCount = json.getInt("moveCount");
		score = json.getInt("score");	
		
	}
	
	public int getScore() {
		return score;
	}
	
	public void addPoints(Tower winningTower) {
		score += winningTower.getPointsForWinning();
	}
	
	public void addPoint() {
		score++;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMoveCount() {
		return moveCount;
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
	
	public void incrementMoveCount() {
		moveCount++;
	}
	
	public String getAIdiff() {
		switch(aiDifficulty) {
			case 1:  return "EASY";
			case 2:  return "NORMAL";
			case 3:  return "HARD";
			default: return "";
		}
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("color", getColorValue());
		json.put("name", name);
		json.put("aiDifficulty", aiDifficulty);
		json.put("moveCount", moveCount);
		json.put("score", score);
		return json;
	}

}
