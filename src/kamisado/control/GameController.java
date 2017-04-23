package kamisado.control;

import java.sql.Time;

import javax.activity.InvalidActivityException;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.plaf.SliderUI;

import kamisado.GUIframes.BoardGUI;
import kamisado.GUIframes.EndGameFrame;
import kamisado.GUIframes.EndRoundFrame;
import kamisado.logic.AI;
import kamisado.logic.Board;
import kamisado.logic.GameTimer;
import kamisado.logic.Move;
import kamisado.logic.PlayerColor;
import kamisado.logic.Square;
import kamisado.logic.State;
import kamisado.logic.StateHistory;

public class GameController {

	public final int speedModeTime = 10; //seconds

	@SuppressWarnings("unused")
	private EndRoundFrame endRoundFrame;
	public Board board;
	public StateHistory history;
	private GameTimer timer;
	private JProgressBar progressBar;
	private JFrame guiFrame;
	private JFrame menuFrame;
	private BoardGUI boardGUI;
	private boolean focusOnBtns;
	private boolean lockInput;
	public AI ai;

	public GameController(Board board, JFrame menuFrame) throws Exception {
		this.board = board;
		this.menuFrame = menuFrame;
		history = new StateHistory();
		addCurrentStateToHistory();
		timer = new GameTimer(speedModeTime, this, progressBar);
		focusOnBtns = false;
		lockInput = false;
		ai = new AI(this);
		if(board.isCurrentPlayerAI()){
			ai.requestMove(board, board.getCurrentPlayerAIDif());
		}
	}

	public void addCurrentStateToHistory() {
		history.addState(new State(board));
	}

	public void undo() throws InvalidActivityException {
		board = history.getPreviousState().getBoard();

		Square movableSquare = board.getCurrentMovableSquare();
		//if not first move
		if(movableSquare != null) 
			movableSquare.setFocused();

		board.markPossibleMoves();
	}

	public void redo() throws InvalidActivityException {
		board = history.getNextState().getBoard();

		Square movableSquare = board.getCurrentMovableSquare();
		//if not first move
		if(movableSquare != null) 
			movableSquare.setFocused();

		board.markPossibleMoves();
	}

	public void mouseAction(int x, int y, boolean isClicked) {
		if(!lockInput) {
			Square square = board.findSquareByCoords(x, y);
			if(square != null) {
				if(isClicked)
					action(square);
				else
					hover(square);
			}
		}
	}

	public void keyAction(String key) {
		if(!lockInput) {
			switch(key) {
			case "LEFT": case "RIGHT":
			case "UP"  : case "DOWN":
				hover(key);
				break;
			case "ENTER": case "SPACE":
				action(board.getHoveredSquare());
				break;
			}
		}
	}

	private void hover(String dir) {
		if(board.hasHoveredSquare()) {
			int row = board.getSquareRowCoord(board.getHoveredSquare());
			int col = board.getSquareColCoord(board.getHoveredSquare());
			if(dir.equals("LEFT") && col!=0) {
				board.getHoveredSquare().setHovered(false);
				hover(board.boardArray[row][col-1]);
			}
			if(dir.equals("UP") && row!=0) {
				board.getHoveredSquare().setHovered(false);
				hover(board.boardArray[row-1][col]);
			}
			if(dir.equals("DOWN") && row!=7) {
				board.getHoveredSquare().setHovered(false);
				hover(board.boardArray[row+1][col]);
			}
			if(dir.equals("RIGHT")) {
				if(col == 7) {
					focusOnBtns = true;
					boardGUI.focusOnFirstButton();
					board.getHoveredSquare().setHovered(false);
				} else {
					board.getHoveredSquare().setHovered(false);
					hover(board.boardArray[row][col+1]);				
				}
			}
		} else {
			if(!focusOnBtns) {
				if(board.getCurrentMovableSquare() == null) {
					if(board.getCurrentPlayerValue() == PlayerColor.BLACK) {
						hover(board.boardArray[0][0]);
					} else {
						hover(board.boardArray[7][0]);
					}
				}
			} else {
				if(dir.equals("LEFT")) {
					focusOnBtns = false;
					hover(board.boardArray[4][7]);
					boardGUI.exitBtns();
				}
				if(dir.equals("UP")) {

				}
				if(dir.equals("DOWN")) {

				}
			}
		}
	}

	private void hover(Square square) {
		if(board.hasHoveredSquare())
			board.getHoveredSquare().setHovered(false);
		square.setHovered(true);
		board.markPossibleMoves();
		focusOnBtns = false;
		boardGUI.requestFocusInWindow();
	}

	private void action(Square desiredSquare) {
		boolean hasFocused = board.hasFocusedSquare();
		Square focusedSquare = board.getFocusedSquare();

		//if second click (1. On tower 2. On Empty space
		if (hasFocused && !desiredSquare.isOccupied() ) {
			requestMove(focusedSquare, desiredSquare);
		}

		Square movableSquare = board.getCurrentMovableSquare();

		//if not first move
		if(movableSquare != null) {
			movableSquare.setFocused();
		} else {
			if(desiredSquare.isOccupied()) {
				if(hasFocused) {
					focusedSquare.defocus();
					if (desiredSquare.getTower().getOwnerColorValue() == board.getCurrentPlayerValue())
						desiredSquare.setFocused();
				} else {
					if (desiredSquare.getTower().getOwnerColorValue() == board.getCurrentPlayerValue())
						desiredSquare.setFocused();
				}
			}


		}

		board.markPossibleMoves();
	}

	public boolean requestMove(Square srcSq, Square dstSq) {
		if (board.makeMove(srcSq, dstSq)) {
			if(board.isSpeedMode()){
				resetProgressBar();
				restartTimer();
			}
			addCurrentStateToHistory();
			srcSq.defocus();
			if(board.endRound) {
				if(!board.isCurrentPlayerAI())
					handleEndRound();
				else {
					ai.requestFill(board, board.getCurrentPlayerAIDif());
				}
			}
			if(board.isCurrentPlayerAI()) {
				ai.requestMove(board, board.getCurrentPlayerAIDif());
			}
			return true;
		}
		return false;
	}
	
	public boolean requestMove(Move move) {
		
		Square srcSq = board.getSquare(move.srcRow, move.srcCol);
		board.defocusAll();
		srcSq.setFocused();
		board.markPossibleMoves();
		
		if (board.makeMove(move)) {
			if(board.isSpeedMode()){
				resetProgressBar();
				restartTimer();
			}
			addCurrentStateToHistory();
			srcSq.defocus();
			board.defocusAll();
			board.getCurrentMovableSquare().setFocused();
			board.markPossibleMoves();
			if(board.endRound) {
				handleEndRound();
			}
			if(board.isCurrentPlayerAI()) {
				ai.requestMove(board, board.getCurrentPlayerAIDif());
			}
			return true;
		}
		
		return false;
	}

	public void handleEndRound() {
		if(board.isSpeedMode()) {
			timer.cancel();
			resetProgressBar();
		}
		if(board.getCurrentPlayerScore() + 1 == board.getPointsLimit()) {
			handleEndGame();
		} else {
			if(board.isCurrentPlayerAI()) {
				ai.requestFill(board, board.getCurrentPlayerAIDif());
				board.endRound = false;
				board.nextRound();
				board.defocusAll();
			} else
				endRoundFrame = new EndRoundFrame(this, board.getCurrentPlayerName());
		}
	}

	private void handleEndGame() {
		@SuppressWarnings("unused")
		EndGameFrame endGameFrame = new EndGameFrame(board.getCurrentPlayerName(), this);
	}

	public void restartTimer() {
		timer.cancel();
		timer = new GameTimer(speedModeTime, this, progressBar);
		timer.start();
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public void setGUIframe(JFrame frame) {
		guiFrame = frame;
	}

	public JFrame getGUIframe() {
		return guiFrame;
	}

	public void setGUI(BoardGUI gui) {
		boardGUI = gui;
	}

	public JFrame getMenuFrame() {
		return menuFrame;
	}

	public void resetProgressBar() {
		progressBar.setValue(0);
	}

	public void syncBoard() {
		board = new Board(history.getCurrentState().getBoard());
	}

	public void resetBoard() {
		board = new Board(board.player1, board.player2, board.getPointsLimit(), board.isSpeedMode(), board.randomBoard);
	}

	public void switchBoard(Board newBoard) {
		board = new Board(newBoard);
	}

	public void lock() {
		lockInput = true;
	}

	public void unlock() {
		lockInput = false;
	}

}