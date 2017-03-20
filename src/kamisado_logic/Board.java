package kamisado_logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.activity.InvalidActivityException;

import kamisado_GUI_frames.EndRoundFrame;

public class Board {

	public Square[][] boardArray;
	private PlayerColor gameState;
	private GameColor currentTowerColor;
	private int[] moveCount;
	private MoveValidator validator;
	private GameTimer timer;
	private String[] name;
	private int[] score;
	private int round;
	private int roundLimit;
	private boolean speedMode;
	public boolean endRound;
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
	
	//Normal constructor
	public Board(String name1, String name2, int limit, boolean speed) {
		boardArray = new Square[8][8];
		
		createSquares();
		
		for (int i = 0; i < 8; i++) {
			Square square = boardArray[0][i];
			square.setTower(new Tower(GameColor.ORANGE + i, PlayerColor.BLACK));
		}
		for (int i = 0; i < 8; i++) {
			Square square = boardArray[7][i];
			square.setTower(new Tower(GameColor.BROWN - i, PlayerColor.WHITE));			
		}
		
		gameState = new PlayerColor(PlayerColor.WHITE);
		currentTowerColor = new GameColor(GameColor.ANY);
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE] = 0;
		moveCount[PlayerColor.BLACK] = 0;
		validator = new MoveValidator(this);
		markPossibleMoves();
		
		name = new String[2];
		name[PlayerColor.WHITE] = name1;
		name[PlayerColor.BLACK] = name2;
		
		score = new int[2];
		score[PlayerColor.WHITE] = 0;
		score[PlayerColor.BLACK] = 0;
		
		round = 1;
		roundLimit = limit;
		
		speedMode = speed;

	}
	
	//Saved from file game constructor
	public Board(String saveName) throws IOException {
		String path = System.getProperty("user.home") + File.separator + "Kamisado Saves" + File.separator + saveName;
		File file = new File(path);
		String tokenizedState;
		BufferedReader br = new BufferedReader(new FileReader(file));
		tokenizedState = br.readLine();
		
		
		System.out.println(tokenizedState);
		
		boardArray = new Square[8][8];

		createSquares();
		
		String[] tokens = tokenizedState.split(",");
		int l = 0;
		for(int i = 0; i <= 7 ; i++) {
			for(int j = 0; j <= 7 ; j ++) {
				Square sq = boardArray[i][j];
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

		gameState = new PlayerColor(Integer.parseInt(br.readLine()));
		currentTowerColor = new GameColor(Integer.parseInt(br.readLine()));
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE] = Integer.parseInt(br.readLine());
		moveCount[PlayerColor.BLACK] = Integer.parseInt(br.readLine());
		validator = new MoveValidator(this);
		markPossibleMoves();

		name = new String[2];
		name[PlayerColor.WHITE] = br.readLine();
		name[PlayerColor.BLACK] = br.readLine();
		
		score = new int[2];
		score[PlayerColor.WHITE] = Integer.parseInt(br.readLine());
		score[PlayerColor.BLACK] = Integer.parseInt(br.readLine());
		
		round =  Integer.parseInt(br.readLine());
		
		br.close();
		
	}
	
	//Copy  constructor (for history)
	public Board(Board original) {
		boardArray = new Square[8][8];
		
		createSquares();

		//tower positions
		for(int i = 0; i <= 7 ; i++) {
			for(int j = 0 ; j <=7 ; j++) {
				Square sq = original.getSquare(i, j);
				if(sq.isOccupied()) {
					Tower originalTower = sq.getTower();
					Tower t = new Tower(originalTower.getColorValue(), originalTower.getOwner());
					getSquare(i, j).setTower(t);
				}
			}
		}
		
		gameState = new PlayerColor(original.getCurrentPlayerValue());
		currentTowerColor = new GameColor(original.getCurrentTowerColorValue());
		moveCount = new int[2];
		moveCount[PlayerColor.WHITE] = original.getMoveCount(PlayerColor.WHITE);
		moveCount[PlayerColor.BLACK] = original.getMoveCount(PlayerColor.BLACK);
		validator = new MoveValidator(this);
		
		name = new String[2];
		name[PlayerColor.WHITE] = original.getPlayerNames(PlayerColor.WHITE);
		name[PlayerColor.BLACK] = original.getPlayerNames(PlayerColor.BLACK);
		
		score = new int[2];
		score[PlayerColor.WHITE] = original.getScore(PlayerColor.WHITE);
		score[PlayerColor.BLACK] = original.getScore(PlayerColor.BLACK);
		
		round = original.getRoundNumber();
		roundLimit = original.getRoundLimit();
		
		speedMode = original.speedMode;
	}
	
	public void createSquares() {
		
		int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
		for (int i = 0; i <= 7; i++) {
			boardArray[i][a] = new Square(GameColor.ORANGE);
			a = (a + 1) % 8;
			boardArray[i][b] = new Square(GameColor.BLUE);
			b = (b + 11) % 8;
			boardArray[i][c] = new Square(GameColor.PURPLE);
			c = (c + 5) % 8;
			boardArray[i][d] = new Square(GameColor.PINK);
			d = (d + 7) % 8;
			boardArray[i][e] = new Square(GameColor.YELLOW);
			e = (e + 1) % 8;
			boardArray[i][f] = new Square(GameColor.RED);
			f = (f + 3) % 8;
			boardArray[i][g] = new Square(GameColor.GREEN);
			g = (g + 5) % 8;
			boardArray[i][h] = new Square(GameColor.BROWN);
			h = (h + 7) % 8;

		}

	}

	public Square getSquare(int row, int column) {
		if (row >= 0 && row <= 7 && column >= 0 && column <= 7)
			return boardArray[row][column];
		return null;
	}
	
	public Square findSquareByCoords(int x, int y) {
		for (Square tile : getSquaresAsList()) {
			if (tile.getX() <= x && tile.getX() + tile.getImage().getWidth(null) >= x && tile.getY() <= y
					&& tile.getY() + tile.getImage().getHeight(null) >= y)
				return tile;
		}
		return null;
	}

	public int getSquareRowCoord(Square square) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (boardArray[x][y].equals(square))
					return x;
			}
		}
		return -1;
	}

	public int getSquareColCoord(Square square) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (boardArray[x][y].equals(square))
					return y;
			}
		}
		return -1;
	}

	public ArrayList<Square> getSquaresAsList() {
		ArrayList<Square> tiles = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tiles.add(getSquare(i, j));
			}
		}
		return tiles;
	}

	public ArrayList<Square> getOccupiedSquaresAsList() {
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
		for (Square square : getSquaresAsList()) {
			if (square.isFocused())
				return true;
		}
		return false;
	}

	public Square getFocusedSquare() {
		for (Square square : getSquaresAsList()) {
			if (square.isFocused())
				return square;
		}
		return null;
	}

	public Square getCurrentMovableSquare() {
		for (Square square : getOccupiedSquaresAsList()) {
			if (square.getTower().getColorValue() == getCurrentTowerColorValue()
					&& square.getTower().getOwner() == getCurrentPlayerValue())
				return square;
		}
		return null;
	}

	public void markPossibleMoves() {
		// Set false to all tiles
		for (Square sq : getSquaresAsList()) {
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
	
	public String getCurrentPlayerName() {
		return name[getCurrentPlayerValue()];
	}
	
	public int getMoveCount(int playerColor) {
		return moveCount[playerColor];
	}

	public int getCurrentPlayerValue() {
		return gameState.getWhoseTurn();
	}

	public int getRoundNumber() {
		return round;
	}
	
	public int getRoundLimit() {
		return roundLimit;
	}
	
	public String getPlayerNames(int color) {
		return name[color];
	}
	
	public int getScore(int playerColor) {
		return score[playerColor];
	}
	
	public boolean isSpeedMode() {
		return speedMode;
	}
	
	public boolean makeMove(Square srcSq, Square destSq) {
		if (!validator.isLegalMove(srcSq, destSq))
			return false;
		Tower t = srcSq.getTower();
		srcSq.clearSquare();
		destSq.setTower(t);
		currentTowerColor.setValue(destSq.getColor());
		if (validator.isGameEnd())
			endRound = true;
		else {
			moveCount[gameState.getWhoseTurn()]++;
			switchSide();
		}
			
		/*
		if (validator.isDeadlock())
			handleDeadLock();
			*/
		return true;
	}
	
	public void switchSide() {
		gameState.changePlayer();
	}
	
	public void nextRound() {
		
		currentTowerColor.setValue(GameColor.ANY);
		moveCount[PlayerColor.BLACK] = 0;
		moveCount[PlayerColor.WHITE] = 0;
		score[getCurrentPlayerValue()]++;
		round++;
		switchSide();
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
