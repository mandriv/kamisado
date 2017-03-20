package kamisado_control;

import javax.activity.InvalidActivityException;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import kamisado_GUI_frames.EndGameFrame;
import kamisado_GUI_frames.EndRoundFrame;
import kamisado_logic.Board;
import kamisado_logic.GameTimer;
import kamisado_logic.PlayerColor;
import kamisado_logic.Square;
import kamisado_logic.State;
import kamisado_logic.StateHistory;

public class GameController {
	
	public final int speedModeTime = 5; //seconds
	
	@SuppressWarnings("unused")
	private EndRoundFrame endRoundFrame;
	public Board board;
	public StateHistory history;
	private GameTimer timer;
	private JProgressBar progressBar;
	private JFrame guiFrame;
	private JFrame menuFrame;

	public GameController(Board board, JFrame menuFrame) {
		this.board = board;
		this.menuFrame = menuFrame;
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
		if(board.isSpeedMode()) {
			timer.cancel();
			resetProgressBar();
		}
		if(board.getScore(board.getCurrentPlayerValue()) + 1 == board.getPointsLimit()) {
			handleEndGame();
		} else {
			endRoundFrame = new EndRoundFrame(this, board.getCurrentPlayerName());
		}
	}
	
	private void handleEndGame() {
		@SuppressWarnings("unused")
		EndGameFrame endGameFrame = new EndGameFrame(board.getCurrentPlayerName(), this);
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
	
	public void setGUIframe(JFrame frame) {
		guiFrame = frame;
	}
	
	public JFrame getGUIframe() {
		return guiFrame;
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
		board = new Board(board.getPlayerNames(PlayerColor.WHITE),
				          board.getPlayerNames(PlayerColor.BLACK), board.getPointsLimit(), board.isSpeedMode());
	}

}
