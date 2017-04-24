package kamisado.logic;

import java.util.ArrayList;

public class MoveValidator {

	public MoveValidator() {
	}
	
	public static ArrayList<Square> getPossibleMoveSquares(Board board) {
				
		ArrayList<Square> list = new ArrayList<>();
		
		if(board.endRound) //no moves if endround
			return list;

		Square moveSquare = board.getCurrentMovableSquare();

		//if this is first move
		if(moveSquare == null) {
			//if player clicked already in first move
			if(board.hasFocusedSquare()) {
				moveSquare = board.getFocusedSquare();
			} else {
				if(board.hasHoveredSquare()) {
					Square hoveredSquare = board.getHoveredSquare();
					if(hoveredSquare.isOccupied()) {
						if(board.getHoveredSquare().getTower().getOwnerColorValue() == board.getCurrentPlayerValue()) {
							moveSquare = board.getHoveredSquare(); //hover on your tower
						} else { //hover on enemy tower
							return list;
						}
							
					} else { //hover on unoccupied
						return list;
					}	
				} else { //no hover
					return list;
				}
			}
		}

		int col = board.getSquareColCoord(moveSquare);
		int row = board.getSquareRowCoord(moveSquare);
		Tower t = moveSquare.getTower();
		// black moves
		if (t.getOwnerColorValue() == PlayerColor.BLACK) {
			
			// moves down
			int x;
			int y = row;
			for(int i = 1; i <= t.getRange(); i++) {
				if (y+i > 7)
					break;
				if (board.getSquare(y+i, col).isOccupied()){
					if(t.getSumoLevel() > 0 && isPushPossible(t, board.getSquare(y+i, col), board))
						list.add(board.getSquare(y+i, col));
					break;
				}
				list.add(board.getSquare(y+i, col));
			}
			
			// skew moves left down
			y = row;
			x = col;
			for(int i = 1; i <= t.getRange(); i++) {
				if (x-i < 0 || y+i > 7 || board.getSquare(y+i, x-i).isOccupied())
					break;
				list.add(board.getSquare(y+i, x-i));
			}
			
			// skew moves right down
			y = row;
			x = col;
			for(int i = 1; i <= t.getRange(); i++) {
				if (x+i > 7 || y+i > 7 || board.getSquare(y+i, x+i).isOccupied())
					break;
				list.add(board.getSquare(y+i, x+i));
			}

		}
		// white moves
		else {
			// forward moves
			int x;
			int y = row;
			for(int i = 1; i <= t.getRange(); i++) {
				if (y-i < 0)
					break;
				if (board.getSquare(y-i, col).isOccupied()){
					if(t.getSumoLevel() > 0 && isPushPossible(t, board.getSquare(y-i, col), board)) {
						list.add(board.getSquare(y-i, col));
					}	
					break;
				} else
				list.add(board.getSquare(y-i, col));
			}
			
			// skew moves left forward
			y = row;
			x = col;
			for(int i = 1; i <= t.getRange(); i++) {
				if (x-i < 0 || y-i < 0 || board.getSquare(y-i, x-i).isOccupied())
					break;
				list.add(board.getSquare(y-i, x-i));
			}
			
			// skew moves right forward
			y = row;
			x = col;
			for(int i = 1; i <= t.getRange(); i++) {
				if (x+i > 7 || y-i < 0 || board.getSquare(y-i, x+i).isOccupied())
					break;
				list.add(board.getSquare(y-i, x+i));
			}
			
		}
		return list;

	}
	
	private static boolean isPushPossible(Tower sumoTower, Square dstSq, Board board) {
		int n = 1;
		
		int row = board.getSquareRowCoord(dstSq);
		int col = board.getSquareColCoord(dstSq);
		
		
		for(int i = 1; i <= 3; i++) {
			if(sumoTower.getOwnerColorValue() == PlayerColor.BLACK) {
				if(row+i > 7)
					break;
				if(board.getSquare(row+i, col).isOccupied())
					n++;
				else
					if(n <= sumoTower.getSumoLevel())
						return true;
			} else {
				if(row-i < 0)
					break;
				if(board.getSquare(row-i, col).isOccupied())
					n++;
				else
					if(n <= sumoTower.getSumoLevel())
						return true;
			}
		}
		
		return false;
	}
	
	public static int numberOfPossibleMovesForSquare(Board board, Square square) {
		ArrayList<Square> list = new ArrayList<>();
		if(square.isOccupied()) {
			int col = board.getSquareColCoord(square);
			int row = board.getSquareRowCoord(square);
			Tower t = square.getTower();
			if (t.getOwnerColorValue() == PlayerColor.BLACK) {
				// moves down
				int x;
				int y = row;
				for(int i = 1; i <= t.getRange(); i++) {
					if (y+i > 7)
						break;
					if (board.getSquare(y+i, col).isOccupied()){
						if(t.getSumoLevel() > 0 && isPushPossible(t, board.getSquare(y+i, col), board))
							list.add(board.getSquare(y+i, col));
						break;
					}
					list.add(board.getSquare(y+i, col));
				}
				// skew moves left down
				y = row;
				x = col;
				for(int i = 1; i <= t.getRange(); i++) {
					if (x-i < 0 || y+i > 7 || board.getSquare(y+i, x-i).isOccupied())
						break;
					list.add(board.getSquare(y+i, x-i));
				}			
				// skew moves right down
				y = row;
				x = col;
				for(int i = 1; i <= t.getRange(); i++) {
					if (x+i > 7 || y+i > 7 || board.getSquare(y+i, x+i).isOccupied())
						break;
					list.add(board.getSquare(y+i, x+i));
				}
			}
			// white moves
			else {
				int x;
				int y = row;
				for(int i = 1; i <= t.getRange(); i++) {
					if (y-i < 0)
						break;
					if (board.getSquare(y-i, col).isOccupied()){
						if(t.getSumoLevel() > 0 && isPushPossible(t, board.getSquare(y-i, col), board)) {
							list.add(board.getSquare(y-i, col));
						}	
						break;
					} else
					list.add(board.getSquare(y-i, col));
				}
				
				// skew moves left forward
				y = row;
				x = col;
				for(int i = 1; i <= t.getRange(); i++) {
					if (x-i < 0 || y-i < 0 || board.getSquare(y-i, x-i).isOccupied())
						break;
					list.add(board.getSquare(y-i, x-i));
				}
				
				// skew moves right forward
				y = row;
				x = col;
				for(int i = 1; i <= t.getRange(); i++) {
					if (x+i > 7 || y-i < 0 || board.getSquare(y-i, x+i).isOccupied())
						break;
					list.add(board.getSquare(y-i, x+i));
				}
			}
		}
		return list.size();
	}

	public static boolean isLegalMove(Board board, Square srcSq, Square destSq) {
		if (!srcSq.isOccupied()) {
			System.out.println("illegal move type 1");
			return false;
		}

		if (srcSq.getTower().getColorValue() != board.getCurrentTowerColorValue()
				&& board.getCurrentTowerColorValue() != GameColor.ANY) {
			System.out.println("illegal move type 2");
			System.out.println(board);
			return false;
		}
		if (!destSq.isPossible()){
			System.out.println("illegal move type 3");
			return false;
		}
		return true;
	}
	
	public static boolean isLegalMove(Board board, Move move) {
		
		Square srcSq = board.getSquare(move.srcRow, move.srcCol);
		Square destSq = board.getSquare(move.dstRow, move.dstCol);
		
		return isLegalMove(board, srcSq, destSq);
	}
	
	public static boolean isThreat(Board board, Square srcSq) {
		if(srcSq.isOccupied()) {
			int row = board.getSquareRowCoord(srcSq);
			int col = board.getSquareColCoord(srcSq);
			if(srcSq.getTower().getOwnerColorValue() == PlayerColor.WHITE) {
				// forward moves
				for (int i = row - 1; i >= 0; i--) {
					if (board.getSquare(i, col).isOccupied())
						break;
					if (i == 0)
						return true;
				}
				// skew moves left forward
				int j = col - 1;
				for (int i = row - 1; i >= 0; i--) {
					if (j < 0)
						break;
					if (board.getSquare(i, j).isOccupied())
						break;
					if (i == 0)
						return true;
					j--;
				}
				// skew moves right forward
				j = col + 1;
				for (int i = row - 1; i >= 0; i--) {
					if (j > 7)
						break;
					if (board.getSquare(i, j).isOccupied())
						break;
					if (i == 0)
						return true;
					j++;
				}
			} else {
				// moves down
				for (int i = row + 1; i <= 7; i++) {
					if (board.getSquare(i, col).isOccupied())
						break;
					if (i == 7)
						return true;
				}
				// skew moves left down
				int j = col - 1;
				for (int i = row + 1; i <= 7; i++) {
					if (j < 0)
						break;
					if (board.getSquare(i, j).isOccupied())
						break;
					if (i == 7)
						return true;
					j--;
				}
				// skew moves right down
				j = col + 1;
				for (int i = row + 1; i <= 7; i++) {
					if (j > 7)
						break;
					if (board.getSquare(i, j).isOccupied())
						break;
					if (i == 7)
						return true;
					j++;
				}
			}
		}
		return false;
	}

	public static boolean isBlocked(Board board) {
		return getPossibleMoveSquares(board).isEmpty();
	}
	
	public static boolean isDeadlock(Board board) {
		if(getPossibleMoveSquares(board).isEmpty()) {
			Board boardCopy = new Board(board);
			boardCopy.switchSide();
			return getPossibleMoveSquares(boardCopy).isEmpty();
		}
		return false;
	}

	public static boolean isRoundEnd(Board board) {
		// check if any of white towers is at the end of grid
		for (Square square : board.getOccupiedSquaresAsList()) {
			if (board.getSquareRowCoord(square) == 0 && square.getTower().getOwnerColorValue() == PlayerColor.WHITE)
				return true;
			if (board.getSquareRowCoord(square) == 7 && square.getTower().getOwnerColorValue() == PlayerColor.BLACK)
				return true;

		}
		return false;
	}

	public static int getWinnigPlayerValue(Board board) {
		for (Square square : board.getOccupiedSquaresAsList()) {
			if (board.getSquareRowCoord(square) == 0 && square.getTower().getOwnerColorValue() == PlayerColor.WHITE)
				return square.getTower().getOwnerColorValue();
			if (board.getSquareRowCoord(square) == 7 && square.getTower().getOwnerColorValue() == PlayerColor.BLACK)
				return square.getTower().getOwnerColorValue();
		}
		return -1;
	}
	
	public static Tower getWinningTower(Board board) {
		for (Square square : board.getOccupiedSquaresAsList()) {
			if (board.getSquareRowCoord(square) == 0 && square.getTower().getOwnerColorValue() == PlayerColor.WHITE)
				return square.getTower();
			if (board.getSquareRowCoord(square) == 7 && square.getTower().getOwnerColorValue() == PlayerColor.BLACK)
				return square.getTower();
		}
		return null;
	}
	
	//not tested
	public static Tower getDeadlockWinningTower(Board board, Square dstSq) {
		if(dstSq == null)
			return null;
		int winner = (dstSq.getTower().getOwnerColorValue() + 1) % 2;
		int color =  dstSq.getTower().getColorValue();
		for(Square square : board.getOccupiedSquaresAsList()) {
			if(square.getTower().getColorValue() == color && square.getTower().getOwnerColorValue() == winner)
				return square.getTower();
		}
		return null;
	}

}
