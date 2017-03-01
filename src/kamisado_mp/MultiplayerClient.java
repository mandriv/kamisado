package kamisado_mp;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MultiplayerClient {

	private static final String apiURL = "http://localhost:3000/";
	private String token;

	public MultiplayerClient() {
		token = null;
	}

	public boolean connectToAPI(String email, String password) {
		JSONObject body = new JSONObject();
		body.put("email", email);
		body.put("password", password);

		try {
			token = getAuthToken(body);
			if (token == null) {
				System.out.println("error authenticating");
				return false;
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		System.out.println("authentication passed, got token");
		return true;
	}

	private String getAuthToken(JSONObject body) throws UnirestException {
		System.out.println("POST on /auth...");
		HttpResponse<JsonNode> response = Unirest.post(apiURL + "auth")
				.header("Content-Type", "application/json")
				.body(body).asJson();

		JSONObject jsonResponse = response.getBody().getObject();
		if (!jsonResponse.has("token") || jsonResponse.getBoolean("error"))
			return null;
		System.out.println("Got results:\n" + jsonResponse.toString());
		return jsonResponse.getString("token");
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
		if (jsonResponse.getBoolean("error"))
			throw new Exception("Error on GET /games!");
		System.out.println("Got results:\n" + jsonResponse.toString());
		
		JSONArray jsonGamesArray = jsonResponse.getJSONArray("message");
		for(int i=0; i<jsonGamesArray.length(); i++){
			JSONObject jsonGame = jsonGamesArray.getJSONObject(i);
			String gameName = jsonGame.getString("name");
			String gameMode = jsonGame.getString("mode");
			String gameP1id = jsonGame.getString("player1_ID");
			String gameP2id = jsonGame.getString("player2_ID");
			String password = jsonGame.getString("password");
			String status = jsonGame.getString("status");
			int p1score = jsonGame.getInt("P1score");
			int p2score = jsonGame.getInt("P2score");
			int time = jsonGame.getInt("time");
			GameListing game = new GameListing(gameName, gameMode, gameP1id, gameP2id, password, status, p1score, p2score, time);
			list.add(game);
		}
		
		return list;
	}
	
	public boolean addGame(GameListing game) throws Exception{
		if(token == null)
			throw new Exception("No authentication! You need to connect first!");
		JSONObject jsonReponse = Unirest.post(apiURL + "games")
				.header("Content-Type", "application/json")
				.header("token", token)
				.body(game.toJSONObject())
				.asJson().getBody().getObject();
		return jsonReponse.getBoolean("error");
	}

}
