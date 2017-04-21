package kamisado_util;

import javax.swing.JFrame;

import kamisado.control.GameController;
import kamisado_GUI_frames.BoardGUI;
import kamisado_logic.Board;
import kamisado_logic.Player;
import kamisado_logic.PlayerColor;

public class GameFactory {

	public GameFactory() {
		
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerNormalLocalGame(String name1, String name2, int limit, JFrame menuFrame) {
		Player p1 = new Player(PlayerColor.WHITE, name1);
		Player p2 = new Player(PlayerColor.BLACK, name2);
		Board b = new Board(p1, p2, limit, false);
		GameController gc;
		try {
			gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerSpeedLocalGame(String name1, String name2, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, name1);
		Player p2 = new Player(PlayerColor.BLACK, name2);
		Board b = new Board(p1, p2, limit, true);
		GameController gc;
		try {
			gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createPlayerVsComputerNormalLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, playerName);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty);
		Board b = new Board(p1, p2, limit, false);
		GameController gc;
		try {
			gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createPlayerVsComputerSpeedLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, playerName);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty);
		Board b = new Board(p1, p2, limit, true);
		GameController gc;
		try {
			gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createComputerVsPlayerNormalLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty);
		Player p2 = new Player(PlayerColor.BLACK, playerName);
		Board b = new Board(p1, p2, limit, false);
		GameController gc;
		try {
			gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createComputerVsPlayerSpeedLocalGame(String playerName, int aiDifficulty, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty);
		Player p2 = new Player(PlayerColor.BLACK, playerName);
		Board b = new Board(p1, p2, limit, true);
		try {
			GameController gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createComputerVsComputerNormalLocalGame(int aiDifficulty1, int aiDifficulty2, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty1);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty2);
		Board b = new Board(p1, p2, limit, false);
		try {
			GameController gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createComputerVsComputerSpeedLocalGame(int aiDifficulty1, int aiDifficulty2, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, "Computer", aiDifficulty1);
		Player p2 = new Player(PlayerColor.BLACK, "Computer", aiDifficulty2);
		Board b = new Board(p1, p2, limit, true);
		try {
			GameController gc = new GameController(b, menuFrame);
			BoardGUI bgui = new BoardGUI(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
