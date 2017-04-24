package kamisado.util;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import kamisado.logic.Player;

public class GameStat {
	Player player1;
	Player player2;
	LocalDateTime timestamp;
	
	public GameStat(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;
		timestamp = LocalDateTime.now();
	}
	
	public JSONObject getJSON() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		JSONObject json = new JSONObject();
		JSONObject content = new JSONObject();
		content.put("time", dtf.format(timestamp));
		content.put("player1", player1.getJSON());
		content.put("player2", player2.getJSON());
		json.put("gameStat", content);
		return json;
	}
}
