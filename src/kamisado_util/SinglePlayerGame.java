package kamisado_util;

import kamisado_gui.BoardGui;
import kamisado_logic.BoardGrid;

public class SinglePlayerGame {

	BoardGui gui;
	BoardGrid boardGrid;
	
	
	public SinglePlayerGame() {
		boardGrid = new BoardGrid();
		gui = new BoardGui(boardGrid);
	}


}
