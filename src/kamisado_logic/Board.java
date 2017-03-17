package kamisado_logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kamisado_GUI_frames.EndRoundFrame;

public class Board {

	private Square[][] board;
	private PlayerColor gameState;
	private GameColor currentTowerColor;
	private int[] moveCount;
	private MoveValidator validator;
	private GameTimer timer;
	private String[] name;
	private int[] score;
	private int round;

	/*
	 * __BOARD COORDS______________________________
	 * |0,0|0,1|0,2|0,3|0,4|0,5|0,6|0,7|
	 * |1,0|1,1|1,2|1,3|1,4|1,5|1,6|1,7|
	 * |2,0|2,1|2,2|2,3|2,4|2,5|2,6|2,7|
	 * |3,0|3,1|3,2|3,3|3,4|3,5|3,6|3,7|
	 * |4,0|4,1|4,2|4,3|4,4|4,5|4,6|4,7|
	 * |5,0|5,1|5,2|5,3|5,4|5,5|5,6|5,7|
	 * |6,0|6,1|6,2|6,3|6,4|6,5|6,6|6,7|
	 * |7,0|7,1|7,2|7,3|7,4|7,5|7,6|7,7|
	 * --------------------------------
	*/
	
	public Board(boolean isSpeedMode) {
		board = new Square[8][8];
		
		createSquares();
		
		for (int i = 0; i < 8; i++) {
			Square square = board[0][i];
			square.setTower(new Tower(GameColor.ORANGE + i, PlayerColor.BLACK));
		}
		for (int i = 0; i < 8; i++) {
			Square square = board[7][i];
			square.setTower(new Tower(GameColor.BROWN - i, PlayerColor.WHITE));			
		}
		
		gameState = new PlayerColor(PlayerColor.WHITE);
		currentTowerColor = new GameColor(GameColor.ANY);
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE] = 0;
		moveCount[PlayerColor.BLACK] = 0;
		validator = new MoveValidator(this);
		markPossibleMoves();
		
		if(isSpeedMode){
			timer = new GameTimer(5);
		}
		name = new String[2];
		name[PlayerColor.WHITE] = "Jerry";
		name[PlayerColor.BLACK] = "Tom";
		
		score = new int[2];
		score[PlayerColor.WHITE] = 0;
		score[PlayerColor.BLACK] = 0;
		
		round = 1;
	}
	
	public Board(String saveName) throws IOException {
		String path = System.getProperty("user.home") + File.separator + "Kamisado Saves" + File.separator + saveName;
		File file = new File(path);
		String tokenizedState;
		BufferedReader br = new BufferedReader(new FileReader(file));
		tokenizedState = br.readLine();
		br.close();
		
		System.out.println(tokenizedState);
		
		board = new Square[8][8];

		createSquares();
		
		String[] tokens = tokenizedState.split(",");
		int l = 0;
		for(int i = 0; i <= 7 ; i++) {
			for(int j = 0; j <= 7 ; j ++) {
				Square sq = board[i][j];
				if (!tokens[l].equals("xx")){
					System.out.println("adding "+tokens[l]);
					int playerColor;
					if(tokens[l].charAt(0) == 'b') {
						playerColor = PlayerColor.BLACK;
					} else {
						playerColor = PlayerColor.WHITE;
					}
					int towerColor = Integer.parseInt(Character.toString(tokens[l].charAt(1)));
					sq.setTower(new Tower(towerColor, playerColor));
				}
				l++;
			}
		}

		gameState = new PlayerColor(PlayerColor.WHITE);
		currentTowerColor = new GameColor(GameColor.ANY);
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE] = 0;
		moveCount[PlayerColor.BLACK] = 0;
		validator = new MoveValidator(this);
		markPossibleMoves();

		name = new String[2];
		name[PlayerColor.WHITE] = "Jerry";
		name[PlayerColor.BLACK] = "Tom";
		
		score = new int[2];
		score[PlayerColor.WHITE] = 0;
		score[PlayerColor.BLACK] = 0;
		
		round = 1;
	}
	
	private void createSquares() {
		
		int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
		for (int i = 0; i <= 7; i++) {
			board[i][a] = new Square(GameColor.ORANGE);
			a = (a + 1) % 8;
			board[i][b] = new Square(GameColor.BLUE);
			b = (b + 11) % 8;
			board[i][c] = new Square(GameColor.PURPLE);
			c = (c + 5) % 8;
			board[i][d] = new Square(GameColor.PINK);
			d = (d + 7) % 8;
			board[i][e] = new Square(GameColor.YELLOW);
			e = (e + 1) % 8;
			board[i][f] = new Square(GameColor.RED);
			f = (f + 3) % 8;
			board[i][g] = new Square(GameColor.GREEN);
			g = (g + 5) % 8;
			board[i][h] = new Square(GameColor.BROWN);
			h = (h + 7) % 8;

		}

	}

	public Square getSquare(int row, int column) {
		if (row >= 0 && row <= 7 && column >= 0 && column <= 7)
			return board[row][column];
		return null;
	}

	public int getSquareRowCoord(Square square) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y].equals(square))
					return x;
			}
		}
		return -1;
	}

	public int getSquareColCoord(Square square) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y].equals(square))
					return y;
			}
		}
		return -1;
	}

	public ArrayList<Square> getTilesAsList() {
		ArrayList<Square> tiles = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tiles.add(getSquare(i, j));
			}
		}
		return tiles;
	}

	public ArrayList<Square> getOccupiedTilesAsList() {
		ArrayList<Square> tiles = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (getSquare(i, j).isOccupied())
					tiles.add(getSquare(i, j));
			}
		}
		return tiles;
	}

	public boolean hasFocusedSquare() {
		for (Square square : getTilesAsList()) {
			if (square.isFocused())
				return true;
		}
		return false;
	}

	public Square getFocusedSquare() {
		for (Square square : getTilesAsList()) {
			if (square.isFocused())
				return square;
		}
		return null;
	}

	public Square getCurrentMovableSquare() {
		for (Square square : getOccupiedTilesAsList()) {
			if (square.getTower().getColorValue() == getCurrentTowerColorValue()
					&& square.getTower().getOwner() == getCurrentPlayerValue())
				return square;
		}
		return null;
	}

	public void markPossibleMoves() {
		// Set false to all tiles
		for (Square sq : getTilesAsList()) {
			sq.setPossible(false);
		}
		for (Square sq : validator.getPossibleMoveSquares()) {
			sq.setPossible(true);
		}
	}

	public PlayerColor getGameState() {
		return gameState;
	}

	public int getCurrentTowerColorValue() {
		return currentTowerColor.getValue();
	}

	public int getCurrentPlayerMoveCount() {
		return moveCount[gameState.getWhoseTurn()];
	}

	public int getCurrentPlayerValue() {
		return gameState.getWhoseTurn();
	}

	public boolean makeMove(Square srcSq, Square destSq) {
		if (!validator.isLegalMove(srcSq, destSq))
			return false;
		Tower t = srcSq.getTower();
		srcSq.clearSquare();
		destSq.setTower(t);
		currentTowerColor.setValue(destSq.getColor());
		switchSide();
		if (validator.isGameEnd())
			handleEndRound();
		if (validator.isDeadlock())
			handleDeadLock();
		return true;
	}
	
	public void switchSide() {
		moveCount[gameState.getWhoseTurn()]++;
		gameState.changePlayer();
	}

	//TO-DO
	public void handleDeadLock() {
		
	}

	public void handleEndRound() {
		if (validator.getWinnigPlayerValue() == PlayerColor.WHITE){
			score[PlayerColor.WHITE]++;
			System.out.println("Hurray! White player wins!");
			
		} else {
			score[PlayerColor.BLACK]++;
			System.out.println("Hurray! Black player wins!");
		}
		
		round++;
			
		EndRoundFrame endRoundFrame = new EndRoundFrame(this, getPlayerNames(getCurrentPlayerValue()));
		
		//resetGrid();
	}

	public void resetGrid() {
		
		board = new Square[8][8];

		for (int i = 0; i < 8; i++)
			board[0][i] = new Square(GameColor.ORANGE + i, new Tower(GameColor.ORANGE + i, PlayerColor.BLACK));

		int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
		for (int i = 1; i < 7; i++) {
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

		for (int i = 0; i < 8; i++)
			board[7][i] = new Square(GameColor.BROWN - i, new Tower(GameColor.BROWN - i, PlayerColor.WHITE));
		
		gameState = new PlayerColor(PlayerColor.WHITE);
		currentTowerColor = new GameColor(GameColor.ANY);
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE] = 0;
		moveCount[PlayerColor.BLACK] = 0;
		validator = new MoveValidator(this);
	}

	public int getRoundNumber() {
		return round;
	}
	
	public String getPlayerNames(int color) {
		return name[color];
	}
	
	public int getPlayerScore(int color) {
		return score[color];
	}
	
	public void fillFromLeft() {
		List<Tower> whiteTowers = new ArrayList<>();
		List<Tower> blackTowers = new ArrayList<>();

		//white towers
		for(int i = 0 ; i <= 7 ; i++) {
			for(int j = 0 ; j <= 7 ; j++) {
				if(getSquare(i, j).isOccupied()) {
					Tower t = getSquare(i, j).getTower();
					if(t.getOwner() == PlayerColor.WHITE) {
						whiteTowers.add(t);
						getSquare(i, j).clearSquare();
					} 
				}
			}
		}

		//black towers
		for(int i = 7 ; i >= 0 ; i--) {
			for(int j = 0 ; j <= 7 ; j++) {
				if(getSquare(i, j).isOccupied()) {
					Tower t = getSquare(i, j).getTower();
					if(t.getOwner() == PlayerColor.BLACK) {
						blackTowers.add(t);
						getSquare(i, j).clearSquare();
					} 
				}
			}
		}

		for (int i = 0 ; i<=7 ; i++) {
			getSquare(0, i).setTower(blackTowers.get(i));
			getSquare(7, i).setTower(whiteTowers.get(i));
		}

	}
	
	public void fillFromRight() {
		List<Tower> whiteTowers = new ArrayList<>();
		List<Tower> blackTowers = new ArrayList<>();

		//white towers
		for(int i = 0 ; i <= 7 ; i++) {
			for(int j = 7 ; j >= 0 ; j--) {
				if(getSquare(i, j).isOccupied()) {
					Tower t = getSquare(i, j).getTower();
					if(t.getOwner() == PlayerColor.WHITE) {
						whiteTowers.add(t);
						getSquare(i, j).clearSquare();
					} 
				}
			}
		}

		//black towers
		for(int i = 7 ; i >= 0 ; i--) {
			for(int j = 7 ; j >= 0 ; j--) {
				if(getSquare(i, j).isOccupied()) {
					Tower t = getSquare(i, j).getTower();
					if(t.getOwner() == PlayerColor.BLACK) {
						blackTowers.add(t);
						getSquare(i, j).clearSquare();
					} 
				}
			}
		}

		for (int i = 0 ; i<=7 ; i++) {
			getSquare(0, 7-i).setTower(blackTowers.get(i));
			getSquare(7, 7-i).setTower(whiteTowers.get(i));
		}
		
	}
}
