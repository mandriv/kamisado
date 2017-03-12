package kamisado_mp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.ImageIcon;

import org.json.JSONObject;

public class User {
	
	public String name;
	public String email;
	public String password;
	public Date dateJoined;
	public int gamesPlayed;
	public int gamesWon;
	public int elo;
	public ImageIcon avatar;
	
	
	
	public User(String name, String email, String dateJoinedString, int gamesPlayed, int gamesWon, int elo,
			int avatarNum) {
		super();
		this.name = name;
		this.email = email;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			dateJoined = df.parse(dateJoinedString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.elo = elo;
		avatar = new ImageIcon(getClass().getResource("/kamisado_media/multiplayer/TCP/avatar"+avatarNum+".jpg"));;
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
	
	public String getJoinedDateFormattedString() {
		DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		return df.format(dateJoined);
	}
	

}
