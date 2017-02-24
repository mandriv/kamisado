package kamisado_logic;

import java.util.ArrayList;

public class BoardGrid {
	
	private Square[][] board;
	private PlayerColor gameState;
	private GameColor currentTowerColor;
	private int[] moveCount;
	private MoveValidator validator;

	public BoardGrid() {
		board =  new Square[8][8];
		
		for(int i=0;i<8;i++)
			board[0][i] = new Square(GameColor.ORANGE+i,new Tower(GameColor.ORANGE+i,PlayerColor.BLACK));
		
		int a=0,b=1,c=2,d=3,e=4,f=5,g=6,h=7;
		for(int i=1;i<7;i++){
			board[i][a] = new Square(GameColor.RED);
			a = (a + 3) % 8;
			board[i][b] = new Square(GameColor.ORANGE);
			b = (b + 1) % 8;
			board[i][c] = new Square(GameColor.PINK);
			c = (c + 7) % 8;
			board[i][d] = new Square(GameColor.GREEN);
			d = (d + 5) % 8;
			board[i][e] = new Square(GameColor.BLUE);
			e = (e + 11) % 8;
			board[i][f] = new Square(GameColor.YELLOW);
			f = (f + 1) % 8;
			board[i][g] = new Square(GameColor.BROWN);
			g = (g + 7) % 8;
			board[i][h] = new Square(GameColor.PURPLE);
			h = (h + 5) % 8;
		}
		
		for(int i=0;i<8;i++)
			board[7][i] = new Square(GameColor.BROWN-i,new Tower(GameColor.BROWN-i,PlayerColor.WHITE));
		
		gameState = new PlayerColor(PlayerColor.WHITE);
		currentTowerColor = new GameColor(GameColor.ANY);
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE]=0;
		moveCount[PlayerColor.BLACK]=0;
		validator = new MoveValidator(this);
		markPossibleMoves();
		
	}
	
	public Square getSquare(int row, int column){
		if(row>=0 && row<=7 && column>=0 && column<=7)
			return board[row][column];
		return null;
	}
	
	public int getSquareRowCoord(Square square){
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				if(board[x][y].equals(square))
					return x;
			}
		}
		return -1;
	}
	
	public int getSquareColCoord(Square square){
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				if(board[x][y].equals(square))
					return y;
			}
		}
		return -1;
	}
	
	public ArrayList<Square> getTilesAsList(){
		ArrayList<Square> tiles = new ArrayList<>();
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				tiles.add(getSquare(i, j));
			}
		}
		return tiles;
	}
	
	public ArrayList<Square> getOccupiedTilesAsList(){
		ArrayList<Square> tiles = new ArrayList<>();
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				if(getSquare(i,j).isOccupied())
					tiles.add(getSquare(i, j));
			}
		}
		return tiles;
	}
	
	public boolean hasFocusedSquare(){
		for(Square square : getTilesAsList()){
			if(square.isFocused())
				return true;
		}
		return false;
	}
	
	public Square getFocusedSquare(){
		for(Square square : getTilesAsList()){
			if(square.isFocused())
				return square;
		}
		return null;
	}
	
	public Square getCurrentMovableTower(){
		for(Square square : getOccupiedTilesAsList()){
			if(square.getTower().getColorValue() == getCurrentTowerColorValue() &&
					square.getTower().getOwner() == getCurrentPlayerValue())
				return square;
		}
		return null;
	}
	
	public void markPossibleMoves(){
		//Set false to all tiles
		for(Square sq : getTilesAsList()){
			sq.setPossible(false);
		}
		for(Square sq : validator.getPossibleMoveSquares()){
			sq.setPossible(true);
		}
	}
	
	public PlayerColor getGameState(){
		return gameState;
	}
	
	public int getCurrentTowerColorValue(){
		return currentTowerColor.getValue();
	}
	
	public int getCurrentPlayerMoveCount(){
		return moveCount[gameState.getWhoseTurn()];
	}
	
	public int getCurrentPlayerValue(){
		return gameState.getWhoseTurn();
	}
	
	public boolean makeMove(Square srcSq, Square destSq){
		if(!validator.isLegalMove(srcSq, destSq))
			return false;
		Tower t = srcSq.getTower();
		srcSq.clearSquare();
		destSq.setTower(t);	
		moveCount[gameState.getWhoseTurn()]++;
		currentTowerColor.setValue(destSq.getColor());
		gameState.changePlayer();
		if(validator.isGameEnd())
			handleEndGame();
		if(validator.isDeadlock())
			handleDeadLock();
		return true;
	}
	
	public void handleDeadLock(){
		moveCount[gameState.getWhoseTurn()]++;
		gameState.changePlayer();
	}
	
	public void handleEndGame(){
		if (validator.getWinnigPlayerValue() == PlayerColor.BLACK)
			System.out.println("Hurray! Black player wins!");
		if (validator.getWinnigPlayerValue() == PlayerColor.WHITE)
			System.out.println("Hurray! White player wins!");
		resetGrid();
	}
	
	public void resetGrid(){
		board =  new Square[8][8];
		
		for(int i=0;i<8;i++)
			board[0][i] = new Square(GameColor.ORANGE+i,new Tower(GameColor.ORANGE+i,PlayerColor.BLACK));
		
		int a=0,b=1,c=2,d=3,e=4,f=5,g=6,h=7;
		for(int i=1;i<7;i++){
			board[i][a] = new Square(GameColor.RED);
			a = (a + 3) % 8;
			board[i][b] = new Square(GameColor.ORANGE);
			b = (b + 1) % 8;
			board[i][c] = new Square(GameColor.PINK);
			c = (c + 7) % 8;
			board[i][d] = new Square(GameColor.GREEN);
			d = (d + 5) % 8;
			board[i][e] = new Square(GameColor.BLUE);
			e = (e + 11) % 8;
			board[i][f] = new Square(GameColor.YELLOW);
			f = (f + 1) % 8;
			board[i][g] = new Square(GameColor.BROWN);
			g = (g + 7) % 8;
			board[i][h] = new Square(GameColor.PURPLE);
			h = (h + 5) % 8;
		}
		
		for(int i=0;i<8;i++)
			board[7][i] = new Square(GameColor.BROWN-i,new Tower(GameColor.BROWN-i,PlayerColor.WHITE));
		
		gameState = new PlayerColor(PlayerColor.WHITE);
		currentTowerColor = new GameColor(GameColor.ANY);
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE]=0;
		moveCount[PlayerColor.BLACK]=0;
		validator = new MoveValidator(this);
		markPossibleMoves();
	}

}
