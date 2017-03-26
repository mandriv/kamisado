package kamisado_util;

import javax.swing.JFrame;

import kamisado_GUI_frames.BoardGUI;
import kamisado_control.GameController;
import kamisado_logic.Board;
import kamisado_logic.Player;
import kamisado_logic.PlayerColor;

public class GameFactory {

	public GameFactory() {
		
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerNormalLocalGame(String name1, String name2, int limit, JFrame menuFrame) {
		Player p1 = new Player(PlayerColor.WHITE, name1, false);
		Player p2 = new Player(PlayerColor.BLACK, name2, false);
		Board b = new Board(p1, p2, limit, false);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerSpeedLocalGame(String name1, String name2, int limit, JFrame menuFrame){
		Player p1 = new Player(PlayerColor.WHITE, name1, false);
		Player p2 = new Player(PlayerColor.BLACK, name2, false);
		Board b = new Board(p1, p2, limit, true);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
	}

}
