package kamisado_logic;

import kamisado_gui.BoardGui;

public class Game {

	BoardGui gui;
	BoardGrid boardGrid;
	
	
	public Game() {
		boardGrid = new BoardGrid();
		gui = new BoardGui(boardGrid);
	}
	
	//TO DO: create exceptions and conditions (or maybe boolean instead of void?)
	// create a method which checks whether a move is valid or not
	public void makeMove(Square srcSq, Square destSq){
		Tower t = srcSq.getTower();	
		srcSq.clearSquare();
		destSq.setTower(t);	
		gui.repaint();
	}


}
