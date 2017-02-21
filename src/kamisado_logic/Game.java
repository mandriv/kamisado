package kamisado_logic;

import kamisado_gui.BoardGui;

public class Game {

	BoardGui gui;
	BoardGrid boardGrid;
	
	
	public Game() {
		boardGrid = new BoardGrid();
		gui = new BoardGui(boardGrid);
	}


}
