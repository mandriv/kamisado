package kamisado_logic;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Board {

	public Square[][] boardArray;
	private GameColor currentTowerColor;
	private Player currentPlayer;
	public Player player1;
	public Player player2;
	private int round;
	private int pointsLimit;
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
	public Board(Player whitePlayer, Player blackPlayer, int limit, boolean speed) {
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
		
		currentPlayer = whitePlayer;
		currentTowerColor = new GameColor(GameColor.ANY);
		player1 = whitePlayer;
		player2 = blackPlayer;
		round = 1;
		pointsLimit = limit;
		speedMode = speed;

		markPossibleMoves();
	}
	/*
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
	*/
	
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
					Tower t = new Tower(originalTower.getColorValue(), originalTower.getOwnerColorValue());
					getSquare(i, j).setTower(t);
				}
			}
		}

		currentPlayer = original.currentPlayer;
		currentTowerColor = new GameColor(original.getCurrentTowerColorValue());
		player1 = original.player1;
		player2 = original.player2;		
		round = original.getRoundNumber();
		pointsLimit = original.getPointsLimit();	
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
	
	public boolean hasHoveredSquare() {
		for (Square square : getSquaresAsList()) {
			if (square.isHovered())
				return true;
		}
		return false;
	}

	public Square getHoveredSquare() {
		for (Square square : getSquaresAsList()) {
			if (square.isHovered())
				return square;
		}
		return null;
	}
	

	public Square getCurrentMovableSquare() {
		for (Square square : getOccupiedSquaresAsList()) {
			if (square.getTower().getColorValue() == getCurrentTowerColorValue()
					&& square.getTower().getOwnerColorValue() == currentPlayer.getColorValue())
				return square;
		}
		return null;
	}

	public void markPossibleMoves() {
		// Set false to all tiles
		for (Square sq : getSquaresAsList()) {
			sq.setPossible(false);
		}
		for (Square sq : MoveValidator.getPossibleMoveSquares(this)) {
			sq.setPossible(true);
		}
	}
	
	public ArrayList<Square> getOccupiedTilesByCurrentPlayer() {
		ArrayList<Square> tiles = new ArrayList<>();
		for (Square sq : getOccupiedSquaresAsList()) {
			if(sq.getTower().getOwnerColorValue() == currentPlayer.getColorValue())
				tiles.add(sq);
				
		}
		return tiles;
	}
	
	public ArrayList<Square> getOccupiedTilesByWhitePlayer() {
		ArrayList<Square> tiles = new ArrayList<>();
		for (Square sq : getOccupiedSquaresAsList()) {
			if(sq.getTower().getOwnerColorValue() == PlayerColor.WHITE)
				tiles.add(sq);
				
		}
		return tiles;
	}
	
	public ArrayList<Square> getOccupiedTilesByBlackPlayer() {
		ArrayList<Square> tiles = new ArrayList<>();
		for (Square sq : getOccupiedSquaresAsList()) {
			if(sq.getTower().getOwnerColorValue() == PlayerColor.BLACK)
				tiles.add(sq);
				
		}
		return tiles;
	}
	
	public ArrayList<Square> getPossibleTiles() {
		return MoveValidator.getPossibleMoveSquares(this);
	}

	public int getCurrentTowerColorValue() {
		return currentTowerColor.getValue();
	}

	public int getRoundNumber() {
		return round;
	}
	
	public int getPointsLimit() {
		return pointsLimit;
	}
	
	public int getCurrentPlayerValue() {
		return currentPlayer.getColorValue();
	}
	
	public int getCurrentPlayerScore() {
		return currentPlayer.getScore();
	}
	
	public String getCurrentPlayerName() {
		return currentPlayer.getName();
	}
	
	public boolean isCurrentPlayerAI() {
		return currentPlayer.isAI();
	}
	
	public int getCurrentPlayerAIDif() {
		return currentPlayer.getDifficulty();
	}
	
	public int getScore(Player player) {
		return player.getScore();
	}
	
	public boolean isSpeedMode() {
		return speedMode;
	}
	
	public boolean makeMove(Square srcSq, Square destSq) {
		if (!MoveValidator.isLegalMove(this, srcSq, destSq))
			return false;
		Tower t = srcSq.getTower();
		srcSq.clearSquare();
		destSq.setTower(t);
		currentTowerColor.setValue(destSq.getColor());
		if (MoveValidator.isGameEnd(this))
			endRound = true;
		else {
			currentPlayer.incrementMoveCount();
			switchSide();
		}
			
		/*
		if (validator.isDeadlock())
			handleDeadLock();
			*/
		return true;
	}
	
	public boolean makeRawMove(Square srcSq, Square destSq) {
		Tower t = srcSq.getTower();
		srcSq.clearSquare();
		destSq.setTower(t);
		currentTowerColor.setValue(destSq.getColor());
		if (MoveValidator.isGameEnd(this))
			endRound = true;
		else {
			currentPlayer.incrementMoveCount();
			switchSide();
		}
		return true;
	}
	
	public void switchSide() {
		if(currentPlayer == player1)
			currentPlayer = player2;
		else
			currentPlayer = player1;
	}
	
	public void nextRound() {
		
		currentTowerColor.setValue(GameColor.ANY);
		player1.resetMoveCount();
		player2.resetMoveCount();
		currentPlayer.incrementScore();
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
					if(t.getOwnerColorValue() == PlayerColor.WHITE) {
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
					if(t.getOwnerColorValue() == PlayerColor.BLACK) {
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
					if(t.getOwnerColorValue() == PlayerColor.WHITE) {
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
					if(t.getOwnerColorValue() == PlayerColor.BLACK) {
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
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i <=7 ; i++){
			for(int j = 0; j <= 7 ; j++) {
				Square s = getSquare(i, j);
				if(s.isOccupied()) {
					if(s.getTower().getOwnerColorValue() == PlayerColor.BLACK) {
						builder.append("b");
					} else {
						builder.append("w");
					}
					builder.append(s.getTower().getColorValue());
				} else {
					builder.append("  ");
				}
				builder.append("|");
			}
			builder.append("\n");
		}
		return builder.toString();
	}



}
