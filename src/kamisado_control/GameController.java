package kamisado_control;

import javax.activity.InvalidActivityException;
import javax.swing.JProgressBar;

import kamisado_GUI_frames.EndRoundFrame;
import kamisado_logic.Board;
import kamisado_logic.GameTimer;
import kamisado_logic.Square;
import kamisado_logic.State;
import kamisado_logic.StateHistory;

public class GameController {
	
	public final int speedModeTime = 5; //seconds

	public Board board;
	public StateHistory history;
	private GameTimer timer;
	private EndRoundFrame endFrame;
	private JProgressBar progressBar;

	public GameController(Board board) {
		this.board = board;
		history = new StateHistory();
		addCurrentStateToHistory();
		timer = new GameTimer(speedModeTime, this, progressBar);
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
	
	public void mouseAction(int x, int y) {
		boolean hasFocused = board.hasFocusedSquare();
		Square focusedSquare = board.getFocusedSquare();
		Square clickedSquare = board.findSquareByCoords(x, y);

		//if second click (1. On tower 2. On Empty space
		if (hasFocused && !clickedSquare.isOccupied() ) {
			requestMove(focusedSquare, clickedSquare);
		}
		
		Square movableSquare = board.getCurrentMovableSquare();
		
		//if not first move
		if(movableSquare != null) {
			movableSquare.setFocused();
		} else {
			if(clickedSquare.isOccupied()) {
				if(hasFocused) {
					focusedSquare.defocus();
					if (clickedSquare.getTower().getOwner() == board.getCurrentPlayerValue())
						clickedSquare.setFocused();
				} else {
					if (clickedSquare.getTower().getOwner() == board.getCurrentPlayerValue())
						clickedSquare.setFocused();
				}
			}
				
					
		}
		
		board.markPossibleMoves();
		
	}
	
	private boolean requestMove(Square srcSq, Square dstSq) {
		if (board.makeMove(srcSq, dstSq)) {
			if(board.isSpeedMode()){
				resetProgressBar();
				restartTimer();
			}
			addCurrentStateToHistory();
			srcSq.defocus();
			if(board.endRound) {
				handleEndRound();	
			}
			return true;
		}
		return false;
	}

	public void handleEndRound() {
		timer.cancel();
		resetProgressBar();
		if(board.getRoundNumber() == board.getRoundLimit()) {
			handleEndGame();
		} else {
			endFrame = new EndRoundFrame(this, board.getCurrentPlayerName());
		}
	}
	
	private void handleEndGame() {
		System.out.println("end");
	}
	
	public void restartTimer() {
		System.out.println("wolany");
		timer.cancel();
		timer = new GameTimer(speedModeTime, this, progressBar);
		timer.start();
	}
	
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	public void resetProgressBar() {
		progressBar.setValue(0);
	}
	
	public void syncBoard() {
		board = new Board(history.getCurrentState().getBoard());
	}

}
