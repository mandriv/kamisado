package kamisado_util;

import javax.swing.JFrame;

import kamisado_GUI_frames.BoardGUI;
import kamisado_control.GameController;
import kamisado_logic.Board;

public class GameFactory {

	public GameFactory() {
		
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerNormalLocalGame(String name1, String name2, int limit, JFrame menuFrame){
		Board b = new Board(name1, name2, limit, false);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
	}
	
	@SuppressWarnings("unused")
	public static void createPlayerVsPlayerSpeedLocalGame(String name1, String name2, int limit, JFrame menuFrame){
		Board b = new Board(name1, name2, limit, true);
		GameController gc = new GameController(b, menuFrame);
		BoardGUI bgui = new BoardGUI(gc);
	}

}
