package kamisado_mp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MultiplayerClient {

	private static final String apiURL = "https://kamisado-cs207.herokuapp.com/";
	private String token;
	public String userID;
	private String lastMessage;

	public MultiplayerClient() {
		token = null;
		userID = null;
		lastMessage = "";
	}

	public boolean connectToAPI(String username, String password) {
		JSONObject body = new JSONObject();
		body.put("name", username);
		body.put("password", password);
		
		System.out.println(body.toString());
		
		try {
			if(!setTokenAndUserID(body))
				return false;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("authentication passed, got token");
		return true;
	}

	private boolean setTokenAndUserID(JSONObject body) throws UnirestException {
		System.out.println("POST on /auth...");
		HttpResponse<JsonNode> response = Unirest.post(apiURL + "auth")
				.header("Content-Type", "application/json")
				.body(body)
				.asJson();

		JSONObject jsonResponse = response.getBody().getObject();
		lastMessage = jsonResponse.getString("message");
		if (jsonResponse.getBoolean("error"))
			return false;
		System.out.println("Got results:\n" + jsonResponse.toString());
		
		token = jsonResponse.getString("token");
		userID = jsonResponse.getString("userID");
		return true;
	}
	
	public ArrayList<GameListing> getGames() throws Exception{
		if(token == null)
			throw new Exception("No authentication! You need to connect first!");
	
		ArrayList<GameListing> list = new ArrayList<>();
		
		System.out.println("GET on /games...");
		HttpResponse<JsonNode> response = Unirest.get(apiURL + "games")
				.header("token", token)
				.asJson();
		JSONObject jsonResponse = response.getBody().getObject();
		
		System.out.println("Got results:\n" + jsonResponse.toString());
		
		if (jsonResponse.getBoolean("error")){
			throw new Exception("Error on GET /games!");
		}
		
		JSONArray jsonGamesArray = jsonResponse.getJSONArray("message");
		lastMessage = jsonGamesArray.toString();
		for(int i=0; i<jsonGamesArray.length(); i++){
			JSONObject jsonGame = jsonGamesArray.getJSONObject(i);
			String name = jsonGame.getString("name");
			String mode = jsonGame.getString("mode");
			String p1id = jsonGame.getString("player1_ID");
			String p2id = jsonGame.getString("player2_ID");
			String password = jsonGame.getString("password");
			String status = jsonGame.getString("status");
			int p1score = jsonGame.getInt("P1score");
			int p2score = jsonGame.getInt("P2score");
			int time = jsonGame.getInt("time");
			int pointsLimit = jsonGame.getInt("pointLimit");
			int rank = jsonGame.getInt("rank");
			GameListing game = new GameListing(name, mode, p1id, p2id, password, status, p1score, p2score, time, pointsLimit, rank);
			list.add(game);
		}
		
		return list;
	}
	
	public boolean addGame(GameListing game) throws Exception{
		if(token == null)
			throw new Exception("No authentication! You need to connect first!");
		System.out.println("POST on /games...");
		System.out.println("Trying to POST: "+game.toJSONObject().toString());
		JSONObject jsonResponse = Unirest.post(apiURL + "games")
				.header("Content-Type", "application/json")
				.header("token", token)
				.body(game.toJSONObject())
				.asJson().getBody().getObject();
		lastMessage = jsonResponse.getString("message");
		System.out.println("Got results:\n" + jsonResponse.toString());
		return jsonResponse.getBoolean("error");
	}
	
	public User getUserByID(String userID) throws Exception{
		if(token == null)
			throw new Exception("No authentication! You need to connect first!");
		
		System.out.println("GET on /games/" + userID + "...");
		HttpResponse<JsonNode> response = Unirest.get(apiURL + "users/" + userID)
				.header("token", token)
				.asJson();
		JSONObject jsonResponse = response.getBody().getObject().getJSONObject("message");
		lastMessage = jsonResponse.toString();
		
		System.out.println("Got results:\n" + jsonResponse.toString());
		
		String name = jsonResponse.getString("name");
		String email = jsonResponse.getString("email");
		String dateJoined = jsonResponse.getString("dateJoined");
		int gamesPlayed = jsonResponse.getInt("gamesPlayed");
		int gamesWon = jsonResponse.getInt("gamesWon");
		int elo = jsonResponse.getInt("eloRating");
		int avatarNum = jsonResponse.getInt("avatarNum");
		
		User user = new User(name, email, dateJoined, gamesPlayed, gamesWon, elo, avatarNum);
		
		return user;
	}
	
	public boolean registerNewUser(User user) throws UnirestException{
		JSONObject jsonResponse = Unirest.post(apiURL + "users")
				.header("Content-Type", "application/json")
				.body(user.getJSONforRegister())
				.asJson().getBody().getObject();
		lastMessage = jsonResponse.getString("message");
		return jsonResponse.getBoolean("error");
	}
	
	public String getLastMessage() {
		return lastMessage;
	}

	public boolean resetPassword(String email) throws UnirestException {
		JSONObject body = new JSONObject();
		body.put("email", email);
		
		JSONObject jsonResponse = Unirest.post(apiURL + "users/resetPassword")
				.header("Content-Type", "application/json")
				.body(body)
				.asJson().getBody().getObject();
		lastMessage = jsonResponse.getString("message");
		return jsonResponse.getBoolean("error");
	}
	
	public boolean changeAvatar(int avatar) throws UnirestException {
		JSONObject body = new JSONObject();
		body.put("avatar", avatar);
		
		System.out.println("PUTting on users with "+body.toString());
		
		JSONObject jsonResponse = Unirest.put(apiURL + "users/" + userID)  
				.header("Content-Type", "application/json")
				.header("token", token)
				.body(body)
				.asJson().getBody().getObject();
		
		lastMessage = jsonResponse.getString("message");
		
		System.out.println("Got response"+jsonResponse.toString());
		
		return jsonResponse.getBoolean("error");
	}
	
	public boolean changeEmail(String newEmail) throws UnirestException {
		JSONObject body = new JSONObject();
		body.put("email", newEmail);
		
		System.out.println("PUTting on users with "+body.toString());
		
		JSONObject jsonResponse = Unirest.put(apiURL+ "users/" + userID)
				.header("Content-Type", "application/json")
				.header("token", token)
				.body(body)
				.asJson().getBody().getObject();
		
		lastMessage = jsonResponse.getString("message");
		
		System.out.println("Got response"+jsonResponse.toString());
		
		return jsonResponse.getBoolean("error");
	}
	
	public boolean changePassword(String oldPassword, String newPassword) throws UnirestException {
		JSONObject body = new JSONObject();
		body.put("oldPassword", oldPassword);
		body.put("newPassword", newPassword);
		
		System.out.println("PUTting on users with "+body.toString());
		
		JSONObject jsonResponse = Unirest.put(apiURL+ "users/" + userID)
				.header("Content-Type", "application/json")
				.header("token", token)
				.body(body)
				.asJson().getBody().getObject();
		
		lastMessage = jsonResponse.getString("message");
		
		System.out.println("Got response"+jsonResponse.toString());
		
		return jsonResponse.getBoolean("error");
	}
}
