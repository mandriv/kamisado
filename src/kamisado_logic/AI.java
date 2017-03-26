package kamisado_logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

import javax.activity.InvalidActivityException;
import kamisado_control.GameController;

public class AI {

	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int HARD = 3;
	private int n = 1;

	private GameController control;
	
	public AI(GameController gc) throws Exception {
		control = gc;
	}

	public void requestMove(Board board, int difficulty) {
		switch(difficulty) {
		case EASY:   makeEasyMove(board);   break;
		case NORMAL: makeMediumMove(board); break;
		case HARD:   makeHardMove(board);   break;
		}
	}

	private void makeEasyMove(Board board) {
		Square srcSq, destSq;
		if(board.getCurrentMovableSquare() == null) {
			ArrayList<Square> occupiedTiles = board.getOccupiedTilesByCurrentPlayer();
			srcSq = occupiedTiles.get(getRandomInt(0, occupiedTiles.size()-1));
		} else {
			srcSq = board.getCurrentMovableSquare();
		}
		new Timer().schedule( 
				new java.util.TimerTask() {
					@Override
					public void run() {
						srcSq.setFocused();
						board.markPossibleMoves();
					}
				}, 
				1000 
				);

		srcSq.setFocused();
		board.markPossibleMoves();
		ArrayList<Square> possibleSquares = board.getPossibleTiles();
		destSq = possibleSquares.get(getRandomInt(0, possibleSquares.size()-1));

		new Timer().schedule( 
				new java.util.TimerTask() {
					@Override
					public void run() {
						board.makeMove(srcSq, destSq);
						srcSq.defocus();
						board.getCurrentMovableSquare().setFocused();
						board.markPossibleMoves();
					}
				}, 
				2000 
				);

	}

	private void makeMediumMove(Board board) {
		Move move;
		if(board.getCurrentMovableSquare() == null) {
			move = getBestMove(board, 3);
		} else{
			move = getBestMove(board, 5);
		}
		Square srcSq = board.getSquare(move.srcRow, move.srcRow);
		Square dstSq = board.getSquare(move.dstRow, move.dstRow);
		
		if(board.hasFocusedSquare()){
			board.getFocusedSquare().defocus();
		}
		srcSq.setFocused();
		board.markPossibleMoves();
		
		control.requestMove(srcSq, dstSq);
		
		srcSq.defocus();
		board.getCurrentMovableSquare().setFocused();
		board.markPossibleMoves();
	}

	private void makeHardMove(Board board) {
		// TODO Auto-generated method stub

	}

	public void requestFill(Board board, int difficulty) {
		switch(difficulty) {
		case EASY:   makeEasyFill(board);   break;
		}
	}

	private void makeEasyFill(Board board) {
		if(getRandomInt(0,1) % 2 == 0) {
			board.fillFromLeft();
		} else {
			board.fillFromRight();
		}
	}
	private Move getBestMove(Board currentBoard, int depthOfSearch) {
		n = 1;
		System.out.println("=================");
		System.out.println("getting best move");
		System.out.println("thinking...");
		double startTime = System.currentTimeMillis();
		HashMap<Move, Double> hashMap = new HashMap<>();
		Board board = new Board(currentBoard);
		List<Move> validMoves = getPossibleMoves(board);
		for(Move m: validMoves){
			System.out.println(m);
		}
		double bestResult = Double.NEGATIVE_INFINITY;
		Move bestMove = null;

		for (Move move : validMoves) {

			executeMove(board, move);

			double evaluationResult = -1 * negaMax(depthOfSearch, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			
			hashMap.put(move, evaluationResult);
			
			board = new Board(currentBoard);

			if( evaluationResult > bestResult){
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
		Board board = new Board(givenBoard);

		if (depth == 0 || isGameOver(board)){
			return eval(board);
		}

		List<Move> moves = getPossibleMoves(board);
		double currentMax = Double.NEGATIVE_INFINITY;

		for(Move currentMove : moves){		
			executeMove(board, currentMove);
			double score = -1 * negaMax(depth - 1, board, -beta, -alpha);
			board = new Board(givenBoard);

			if( score > currentMax){
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
		if(isGameOver(board)) {
			if(board.getCurrentPlayerValue() == PlayerColor.WHITE){
				return Double.POSITIVE_INFINITY;
			} 
			return Double.NEGATIVE_INFINITY;
		}

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
					board.getFocusedSquare().defocus();
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
				board.getFocusedSquare().defocus();
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
		return MoveValidator.isGameEnd(board);
	}

	private int getRandomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	private void executeMove(Board board, Move move) {
		int srcRow = move.srcRow;
		int srcCol = move.srcCol;
		int dstRow = move.dstCol;
		int dstCol = move.dstCol;

		Square srcSq =  board.getSquare(srcRow, srcCol);
		Square destSq = board.getSquare(dstRow, dstCol);


		if(board.hasFocusedSquare()){
			board.getFocusedSquare().defocus();
		}
		srcSq.setFocused();
		board.markPossibleMoves();
		board.makeRawMove(srcSq, destSq);

	}

}
