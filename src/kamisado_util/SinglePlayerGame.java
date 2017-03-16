package kamisado_util;

import kamisado_GUI_frames.BoardGUI;
import kamisado_logic.Board;

public class SinglePlayerGame {

	BoardGUI gui;
	Board boardGrid;

	public SinglePlayerGame() {
		boardGrid = new Board(true);
		gui = new BoardGUI(boardGrid);
	}

}
