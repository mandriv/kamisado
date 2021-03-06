package kamisado.util;

import javax.swing.JFrame;

import org.json.JSONObject;

import kamisado.GUIframes.BoardGUI;
import kamisado.control.GameController;
import kamisado.logic.Board;
import kamisado.logic.Player;
import kamisado.logic.PlayerColor;

public class GameFactory {

	public GameFactory() {
		
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerNormalLocalGame(String name1, String name2, int limit, JFrame menuFrame, 
																								  boolean randomBoard) {
		Player p1 = new Player(PlayerColor.WHITE, name1);
		Player p2 = new Player(PlayerColor.BLACK, name2);
		Board b = new Board(p1, p2, limit, false, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerSpeedLocalGame(String name1, String name2, int limit, JFrame menuFrame, 
			  																					boolean randomBoard){
		Player p1 = new Player(PlayerColor.WHITE, name1);
		Player p2 = new Player(PlayerColor.BLACK, name2);
		Board b = new Board(p1, p2, limit, true, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	public static void createPlayerVsComputerNormalLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame,
																								boolean randomBoard){
		Player p1 = new Player(PlayerColor.WHITE, playerName);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty);
		Board b = new Board(p1, p2, limit, false, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	public static void createPlayerVsComputerSpeedLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame,
																								boolean randomBoard){
		Player p1 = new Player(PlayerColor.WHITE, playerName);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty);
		Board b = new Board(p1, p2, limit, true, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	public static void createComputerVsPlayerNormalLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame,
																								boolean radnomBoard){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty);
		Player p2 = new Player(PlayerColor.BLACK, playerName);
		Board b = new Board(p1, p2, limit, false, radnomBoard);
		GameController gc= new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	public static void createComputerVsPlayerSpeedLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame,
																								boolean randomBoard){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty);
		Player p2 = new Player(PlayerColor.BLACK, playerName);
		Board b = new Board(p1, p2, limit, true, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);

		
	}
	
	public static void createComputerVsComputerNormalLocalGame(int aiDifficulty1, int aiDifficulty2, int limit,
															   JFrame menuFrame, boolean randomBoard){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty1);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty2);
		Board b = new Board(p1, p2, limit, false, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	public static void createComputerVsComputerSpeedLocalGame(int aiDifficulty1, int aiDifficulty2, int limit,
															  JFrame menuFrame, boolean randomBoard){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty1);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty2);
		Board b = new Board(p1, p2, limit, true, randomBoard);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
		
	}
	
	public static void createGameFromJSON(JSONObject json, JFrame menuFrame) {
		Board b = new Board(json);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
	}

}
