package kamisado.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

import javax.activity.InvalidActivityException;

import kamisado.control.GameController;

public class AI{

	private static final int EASY = 1;
	private static final int NORMAL = 2;
	private static final int HARD = 3;
	private int n = 1;

	private GameController control;
	private Thread thread;
	
	public AI(GameController gc) throws Exception {
		control = gc;
	}

	public void requestMove(Board board, int difficulty) {
		
		if(difficulty == EASY) {
			thread = new Thread() {
		        public void run() {
		        	control.requestMove(getBestMove(board, 1));
		        }
		    };
		    thread.start();
		} else if (difficulty == NORMAL) {
			thread = new Thread() {
		        public void run() {
		        	if(board.getCurrentMovableSquare() == null) //if first move
		        		control.requestMove(getBestMove(board, 4));
		        	else
		        		control.requestMove(getBestMove(board, 5));
		        }
		    };
		    thread.start();
		} else if (difficulty == HARD) {
			thread = new Thread() {
		        public void run() {
		        	if(board.getCurrentMovableSquare() == null) //if first move
		        		control.requestMove(getBestMove(board, 5));
		        	else
		        		control.requestMove(getBestMove(board, 6));
		        }
		    };
		    thread.start();
		}
	}


	public void requestFill(Board board, int difficulty) {
		if(difficulty == EASY) {
			thread = new Thread() {
		        public void run() {
		        	makeBestFill(board, 2);
		        }
		    };
		    thread.start();
		} else if (difficulty == NORMAL) {
			thread = new Thread() {
		        public void run() {
		        	makeBestFill(board, 5);
		        }
		    };
		    thread.start();
		} else if (difficulty == HARD) {
			thread = new Thread() {
		        public void run() {
		        	makeBestFill(board, 8);
		        }
		    };
		    thread.start();
		}
	}

	
	private void makeBestFill(Board board, int depthOfSearch) {
		
		Board boardCopy = new Board(board);	
		boardCopy.fillFromLeft();
		double evalLeft = -negaMax(depthOfSearch, boardCopy, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		boardCopy = new Board(board);
		boardCopy.fillFromRight();
		double evalRight = -negaMax(depthOfSearch, boardCopy, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		if (evalRight > evalLeft)
			board.fillFromRight();
		else
			board.fillFromLeft();
	}
	
	private Move getBestMove(Board currentBoard, int depthOfSearch) {
		n = 1;
		System.out.println("=================");
		System.out.println("getting best move");
		System.out.println("thinking...");
		double startTime = System.currentTimeMillis();
		
		List<Move> validMoves = getPossibleMoves(currentBoard);
		double bestResult = Double.NEGATIVE_INFINITY;
		Move bestMove = null;
		
		for (Move move : validMoves) {
			
			currentBoard.defocusAll();
			currentBoard.dehoverAll();
			currentBoard.getSquare(move.srcRow, move.srcCol).setFocused();
			currentBoard.getSquare(move.dstRow, move.dstCol).setHovered(true);
			currentBoard.markPossibleMoves();
			
			Board board = new Board(currentBoard);
			
			board.defocusAll();			
			board.getSquare(move.srcRow, move.srcCol).setFocused();						
			board.markPossibleMoves();
			
			board.makeMove(move);	
			
			board.defocusAll();
			
			double evaluationResult = -negaMax(depthOfSearch, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

			System.out.println("Move "+move+" : "+evaluationResult+" pts");
			if( evaluationResult >= bestResult) {
				bestResult = evaluationResult;
				bestMove = move;
			}

		}
		
		System.out.println("done thinking! best move is: "+bestMove+" with "+bestResult+" pts, depth of search: "+depthOfSearch );
		double stopTime = System.currentTimeMillis();
		System.out.println(n+" evaluations in "+(stopTime - startTime)/1000 + "s");
		
		return bestMove;
	}

	private double negaMax(int depth, Board givenBoard, double alpha, double beta) {
		
		if (depth <= 0 || isGameOver(givenBoard)){
			return eval(givenBoard);
		}

		List<Move> validMoves = getPossibleMoves(givenBoard);
		double currentMax = Double.NEGATIVE_INFINITY;

		for(Move move : validMoves){	
			
			Board board = new Board(givenBoard);
			
			board.defocusAll();
			board.getSquare(move.srcRow, move.srcCol).setFocused();
			board.markPossibleMoves();
			
			board.makeMove(move);
			
			board.defocusAll();
			
			double score = -negaMax(depth - 1, board, -beta, -alpha);

			if( score >= currentMax){
				currentMax = score;
			}
			if (score > alpha) {
				alpha = score;
			}
			if (alpha>=beta)
				return alpha;
		}
		return currentMax;
	}

	private double eval(Board board) {
		n++;
		double valueWhite = 0;
		double valueBlack = 0;

		valueWhite += getNumberOfOneMoveWinSquaresForWhitePlayer(board);
		valueBlack += getNumberOfOneMoveWinSquaresForBlackPlayer(board);

		for(Square sq: board.getOccupiedTilesByWhitePlayer()) {
			int row = board.getSquareRowCoord(sq);
			valueWhite += (7-row);
			valueWhite += MoveValidator.numberOfPossibleMovesForSquare(board, sq);
		}

		for(Square sq: board.getOccupiedTilesByBlackPlayer()) {
			int row = board.getSquareRowCoord(sq);
			valueBlack += row;
			valueBlack += MoveValidator.numberOfPossibleMovesForSquare(board, sq);
		}
		
		if(isGameOver(board))
			return Double.NEGATIVE_INFINITY;

		if(board.getCurrentPlayerValue() == PlayerColor.WHITE){
			return valueWhite - valueBlack;
		}
		return valueBlack - valueWhite;
	}

	private int getNumberOfOneMoveWinSquaresForWhitePlayer(Board board) {
		int num = 0;
		for(Square sq: board.getOccupiedTilesByWhitePlayer()) {
			if(MoveValidator.isThreat(board, sq))
				num++;
		}
		return num;
	}

	private int getNumberOfOneMoveWinSquaresForBlackPlayer(Board board) {
		int num = 0;
		for(Square sq: board.getOccupiedTilesByBlackPlayer()) {
			if(MoveValidator.isThreat(board, sq))
				num++;
		}
		return num;
	}

	private List<Move> getPossibleMoves(Board givenBoard) {
		Board board = new Board(givenBoard);
		List<Move> possibleMoves = new ArrayList<>();

		if(board.getCurrentMovableSquare() == null) {
			for(Square square: board.getOccupiedTilesByCurrentPlayer()) {
				if(board.hasFocusedSquare()){
					board.defocusAll();
				}
				square.setFocused();
				board.markPossibleMoves();

				int srcRow = board.getSquareRowCoord(square);
				int srcCol = board.getSquareColCoord(square);

				for(Square square2: board.getPossibleTiles()) {
					int dstRow = board.getSquareRowCoord(square2);
					int dstCol = board.getSquareColCoord(square2);
					Move m = new Move(srcRow, srcCol, dstRow, dstCol);
					possibleMoves.add(m);
				}
			}
		} else {
			Square square = board.getCurrentMovableSquare();
			if(board.hasFocusedSquare()){
				board.defocusAll();
			}
			square.setFocused();
			board.markPossibleMoves();
			for(Square square2: board.getPossibleTiles()) {
				int srcRow = board.getSquareRowCoord(square);
				int srcCol = board.getSquareColCoord(square);
				int dstRow = board.getSquareRowCoord(square2);
				int dstCol = board.getSquareColCoord(square2);
				Move m = new Move(srcRow, srcCol, dstRow, dstCol);
				possibleMoves.add(m);
			}	
		}

		return possibleMoves;
	}


	private boolean isGameOver(Board board) {
		return MoveValidator.isRoundEnd(board);
	}

}
