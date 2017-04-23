package kamisado.mp;

import java.net.URISyntaxException;

import org.json.JSONObject;

import io.socket.client.*;
import io.socket.emitter.*;

public class WebSocketsHandler {
	
	Socket socket;
	ChatController chatControl;
	
	public WebSocketsHandler(ChatController chatControl) {
		try {
			socket = IO.socket("https://kamisado-cs207.herokuapp.com/");
			this.chatControl = chatControl;
			socket.on("chat message", onChatMessage);
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		socket.close();
	}
	
	public void sendChatMessage(String username, String message) {
		JSONObject chatMessage = new JSONObject();
		chatMessage.put("name", username);
		chatMessage.put("message", message);
		socket.emit("chat message", chatMessage);
	}
	
	private Emitter.Listener onChatMessage = new Emitter.Listener() {
		
		@Override
		public void call(Object... args) {
			JSONObject chatMessage = (JSONObject) args[0];
			chatControl.addMessage(chatMessage);
		}
	};
	
}