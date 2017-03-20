package kamisado_util;

import kamisado_GUI_frames.BoardGUI;
import kamisado_control.GameController;
import kamisado_logic.Board;

public class GameFactory {

	public GameFactory() {
		
	}
	
	public static void createPlayerVsPlayerNormalLocalGame(String name1, String name2, int limit){
		Board b = new Board(name1, name2, limit, false);
		GameController gc = new GameController(b);
		BoardGUI bgui = new BoardGUI(gc);
	}
	
	public static void createPlayerVsPlayerSpeedLocalGame(String name1, String name2, int limit){
		Board b = new Board(name1, name2, limit, true);
		GameController gc = new GameController(b);
		BoardGUI bgui = new BoardGUI(gc);
	}

}
