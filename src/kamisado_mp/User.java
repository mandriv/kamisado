package kamisado_mp;

import java.util.Date;

import org.json.JSONObject;

public class User {
	
	public String name;
	public String email;
	public Date dateJoined;
	public String password;
	public int gamesPlayed;
	public int gamesWon;
	public int elo;
	public boolean admin;
	
	public User(String name, String email, Date dateJoined, String hashedPassword, int gamesPlayed, int gamesWon, int elo, boolean admin) {
		this.name = name;
		this.email = email;
		this.dateJoined = dateJoined;
		password = hashedPassword;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.elo = elo;
		this.admin = admin;
	}
	
	public User(String name, String email, String password){
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public JSONObject getJSONforRegister(){
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("email", email);
		json.put("password", password);
		return json;
	}
	

}
