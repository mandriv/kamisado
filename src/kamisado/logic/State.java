package kamisado.logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class State {
	
	private Board savedBoard;
	
	public State(Board board) {
		savedBoard = new Board(board);
	}
	
	public Board getBoard() {
		return savedBoard;
	}

}
