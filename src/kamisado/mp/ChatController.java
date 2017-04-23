package kamisado.mp;

import javax.swing.JTextArea;

import org.json.JSONObject;

public class ChatController {
	
	private JTextArea chat;
	
	public ChatController(JTextArea chat) {
		this.chat = chat;
	}
	
	public void addMessage(JSONObject chatMessage) {
		String user = chatMessage.getString("name");
		String message = chatMessage.getString("message");
		chat.append(user+": "+message+"\n");
	}
}
