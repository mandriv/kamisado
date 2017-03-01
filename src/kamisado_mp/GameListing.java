package kamisado_mp;

import org.json.JSONObject;

public class GameListing {

	String name;
	String mode;
	String P1id;
	String P2id;
	String password;
	String status;
	int p1score;
	int p2score;
	int time;
	String rank;
	
	
	//for POST methods
	public GameListing(String gameName, String gameMode, String password) {
		rank = "Sergeant";
		name = gameName;
		mode = gameMode;
		this.password = password;
	}
	
	//for GET method
	public GameListing(	String gameName,
						String gameMode,
						String gameP1id,
						String gameP2id,
						String password,
						String status,
						int p1score,
						int p2score,
						int time) {
		rank = "Sergeant";
		name = gameName;
		mode = gameMode;
		P1id = gameP1id;
		P2id = gameP2id;
		this.password = password;
		this.status = status;
		this.p1score = p1score;
		this.p2score = p2score;
		this.time = time;
		
	}
	
	
	//only for new games
	public JSONObject toJSONObject(){
		JSONObject game = new JSONObject();
		game.put("name", this.name);
		game.put("mode", this.mode);
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
	
	public String getRank(){
		return rank;
	}
	
	public String getStatus(){
		return status;
	}
}
