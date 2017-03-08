package kamisado_mp;

import org.json.JSONObject;

public class GameListing {

	String name;
	String mode;
	String p1id;
	String p2id;
	String password;
	String status;
	int p1score;
	int p2score;
	int time;
	int pointsLimit;
	int rank;
	
	
	//for POST methods
	public GameListing(String gameName, String gameMode, int limit, String password, int rank) {
		name = gameName;
		mode = gameMode;
		pointsLimit = limit;
		this.password = password;
		this.rank = rank;
	}

	public GameListing(String name, String mode, String p1id, String p2id, String password, String status, int p1score,
			int p2score, int time, int pointsLimit, int rank) {
		super();
		this.name = name;
		this.mode = mode;
		this.p1id = p1id;
		this.p2id = p2id;
		this.password = password;
		this.status = status;
		this.p1score = p1score;
		this.p2score = p2score;
		this.time = time;
		this.pointsLimit = pointsLimit;
		this.rank = rank;
	}

	//only for new games
	public JSONObject toJSONObject(){
		JSONObject game = new JSONObject();
		game.put("name", this.name);
		game.put("mode", this.mode);
		game.put("limit", this.pointsLimit);
		game.put("rank", this.rank);
		game.put("password", this.password);
		return game;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isProtected(){
		return (password.length()>0);
	}
	
	public String getMode(){
		return mode;
	}
	
	public int getRank(){
		return rank;
	}
	
	public String getStatus(){
		return status;
	}
}
