package kamisado.util;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import kamisado.logic.Player;

public class GameStat {
	public Player player1;
	public Player player2;
	LocalDateTime timestamp;
	
	public GameStat(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;
		timestamp = LocalDateTime.now();
	}
	
	public GameStat(JSONObject json) {
		JSONObject obj = json.getJSONObject("gameStat");
		timestamp = LocalDateTime.parse(obj.getString("time"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		player1 = new Player(obj.getJSONObject("player1"));
		player2 = new Player(obj.getJSONObject("player2"));
	}
	
	public JSONObject getJSON() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		JSONObject json = new JSONObject();
		JSONObject content = new JSONObject();
		content.put("time", dtf.format(timestamp));
		content.put("player1", player1.getJSON());
		content.put("player2", player2.getJSON());
		json.put("gameStat", content);
		return json;
	}
	
	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dtf.format(timestamp);
	}
	
	public Player getWinner() {
		if(player1.getScore() > player2.getScore())
			return player1;
		return player2;
	}
	
	public Player getHuman() {
		if(player1.isAI())
			return player2;
		return player1;
	}
}
