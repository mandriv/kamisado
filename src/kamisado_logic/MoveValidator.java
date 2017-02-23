package kamisado_logic;

import java.util.ArrayList;

public class MoveValidator {

	private BoardGrid boardGrid;
	
	public MoveValidator(BoardGrid bg) {
		boardGrid = bg;
	}
	
	public ArrayList<Square> getPossibleMoveSquares(){
		ArrayList<Square> list = new ArrayList<>();
		//first move, no focused
		if(boardGrid.getCurrentPlayerValue() == Player.WHITE && boardGrid.getCurrentPlayerMoveCount()==0){
			if(!boardGrid.hasFocusedSquare()){
				for(int i=1;i<8;i++){
					for(int j=0;j<8;j++){
						list.add(boardGrid.getSquare(i, j));
					}
				}	
			}else{
				Square focused = boardGrid.getFocusedSquare();
				int col = boardGrid.getSquareColCoord(focused);
				int row = boardGrid.getSquareRowCoord(focused);
				//forward moves (white)
				for(int i = row-1; i>=1; i--){
					list.add(boardGrid.getSquare(i,col));
				}
				//skew moves left
				int j = col-1;
				for(int i = row-1; i>=1; i--){
					if(j<0)
						break;
					list.add(boardGrid.getSquare(i,j));
					j--;
				}
				//skew moves right
				j = col+1;
				for(int i = row-1; i>=1; i--){
					if(j>7)
						break;
					list.add(boardGrid.getSquare(i,j));
					j++;
				}
			}
			
		}
		//next moves
		else{
			Square moveSquare = boardGrid.getCurrentMovableTower();
			int col = boardGrid.getSquareColCoord(moveSquare);
			int row = boardGrid.getSquareRowCoord(moveSquare); 
			//black moves
			if(moveSquare.getTower().getOwner() == Player.BLACK){
				//moves down
				for(int i = row+1; i<=7; i++){
					if(boardGrid.getSquare(i, col).isOccupied())
						break;
					list.add(boardGrid.getSquare(i,col));
				}
				//skew moves left down
				int j = col-1;
				for(int i = row+1; i<=7; i++){
					if(j<0)
						break;
					if(boardGrid.getSquare(i, j).isOccupied())
						break;
					list.add(boardGrid.getSquare(i,j));
					j--;
				}
				//skew moves right down
				j = col+1;
				for(int i = row+1; i<=7; i++){
					if(j>7)
						break;
					if(boardGrid.getSquare(i, j).isOccupied())
						break;
					list.add(boardGrid.getSquare(i,j));
					j++;
				}
			}
			//white moves
			else{
				//forward moves
				for(int i = row-1; i>=0; i--){
					if(boardGrid.getSquare(i, col).isOccupied())
						break;
					list.add(boardGrid.getSquare(i,col));
				}
				//skew moves left forward
				int j = col-1;
				for(int i = row-1; i>=0; i--){
					if(j<0)
						break;
					if(boardGrid.getSquare(i, j).isOccupied())
						break;
					list.add(boardGrid.getSquare(i,j));
					j--;
				}
				//skew moves right forward
				j = col+1;
				for(int i = row-1; i>=0; i--){
					if(j>7)
						break;
					if(boardGrid.getSquare(i, j).isOccupied())
						break;
					list.add(boardGrid.getSquare(i,j));
					j++;
				}
			}
		}
		return list;
		
	}
	
	//Probably trash it
	public boolean isLegalMove(Square srcSq, Square destSq){
		if(!(srcSq.isOccupied() && !destSq.isOccupied()))
			return false;
		if(srcSq.getTower().getColorValue()!=boardGrid.getCurrentTowerColorValue() && boardGrid.getCurrentTowerColorValue()!=GameColor.ANY)
			return false;
		if(!destSq.isPossible())
			return false;
		return true;
	}
	
	public boolean isDeadlock(){
		return getPossibleMoveSquares().isEmpty();
	}
	
	public boolean isGameEnd(){
		if(boardGrid.getCurrentPlayerMoveCount()!=0){
			//check if any of white towers is at the end of grid
			for(Square square : boardGrid.getOccupiedTilesAsList()){
				if(boardGrid.getSquareRowCoord(square)==0 && square.getTower().getOwner()==Player.WHITE)
					return true;
				if(boardGrid.getSquareRowCoord(square)==7 && square.getTower().getOwner()==Player.BLACK)
					return true;
				
			}
			return false;
		}
		return false;
	}
	
	public int getWinnigPlayerValue(){
		for(Square square : boardGrid.getOccupiedTilesAsList()){
			if(boardGrid.getSquareRowCoord(square)==0 && square.getTower().getOwner()==Player.WHITE)
				return square.getTower().getOwner();
			if(boardGrid.getSquareRowCoord(square)==7 && square.getTower().getOwner()==Player.BLACK)
				return square.getTower().getOwner();
			
		}
		return -1;
	}

}
