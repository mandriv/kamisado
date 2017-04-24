package kamisado.logic;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;

import kamisado.util.UniqueRandom2DArrayFactory;


public class Board {

	public Square[][] boardArray;
	private GameColor currentTowerColor;
	public Player currentPlayer;
	public Player player1;
	public Player player2;
	private int round;
	private int pointsLimit;
	private boolean speedMode;
	public boolean endRound;
	public boolean randomBoard;
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
	public Board(Player whitePlayer, Player blackPlayer, int limit, boolean speed, boolean randomBoard) {
		boardArray = new Square[8][8];

		if(!randomBoard)
			createSquares();
		else {
			createRandomSquares();
			randomBoard = true;
		}			

		for (int i = 0; i < 8; i++) {
			Square square = boardArray[0][i];
			square.setTower(new Tower(square.getColor(), PlayerColor.BLACK));
		}
		for (int i = 0; i < 8; i++) {
			Square square = boardArray[7][i];
			square.setTower(new Tower(square.getColor(), PlayerColor.WHITE));			
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

	//for loading from json
	public Board(JSONObject json){
		boardArray = new Square[8][8];
		JSONArray boardArrJSON = json.getJSONArray("board");
		for(int i = 0; i <= 7; i++) {
			JSONArray rowJSON = boardArrJSON.getJSONArray(i);
			for(int j = 0; j <= 7; j++) {
				JSONObject squareJSON = rowJSON.getJSONObject(j);
				boardArray[i][j] = new Square(squareJSON.getInt("color"));
				if(squareJSON.has("tower")) {
					JSONObject towerJSON = squareJSON.getJSONObject("tower");
					Tower t = new Tower(towerJSON.getInt("color"), towerJSON.getInt("owner"), towerJSON.getInt("sumo"));
					getSquare(i, j).setTower(t);
				}
			}
		}
		JSONObject player1JSON = json.getJSONObject("player1");
		JSONObject player2JSON = json.getJSONObject("player2");
		player1 = new Player(player1JSON.getInt("color"), player1JSON.getString("name"), player1JSON.getInt("aiDifficulty"),
				             player1JSON.getInt("moveCount"), player1JSON.getInt("score"));
		player2 = new Player(player2JSON.getInt("color"), player2JSON.getString("name"), player2JSON.getInt("aiDifficulty"),
	             player2JSON.getInt("moveCount"), player2JSON.getInt("score"));
		
		if(json.getInt("currentPlayer") == 0)
			currentPlayer = player1;
		else
			currentPlayer = player2;
		currentTowerColor = new GameColor(json.getInt("currentTowerColor"));
		round = json.getInt("round");
		pointsLimit = json.getInt("pointsLimit");
		speedMode = json.getBoolean("random");

	}

	//Copy  constructor
	public Board(Board original) {
		boardArray = new Square[8][8];

		//squares
		for(int i = 0; i <= 7 ; i++) {
			for(int j = 0 ; j <=7 ; j++) {
				Square sq = original.getSquare(i, j);
				boardArray[i][j] = new Square(sq.getColor());
				if(sq.isOccupied()) {
					Tower originalTower = sq.getTower();
					Tower t = new Tower(originalTower.getColorValue(), originalTower.getOwnerColorValue(),
							             originalTower.getSumoLevel());
					getSquare(i, j).setTower(t);
				}
			}
		}
		player1 = new Player(original.player1);
		player2 = new Player(original.player2);
		if(original.currentPlayer == original.player1)
			currentPlayer = player1;
		else
			currentPlayer = player2;
		currentTowerColor = new GameColor(original.getCurrentTowerColorValue());
		round = original.getRoundNumber();
		pointsLimit = original.getPointsLimit();	
		speedMode = original.speedMode;
		randomBoard = original.randomBoard;
	}

	private void createSquares() {

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
	
	private void createRandomSquares() {
		int[][] colorsArr = UniqueRandom2DArrayFactory.get8x8UniqueNumbersRandomArray();
		
		for (int i = 0; i <= 7; i++){
			for(int j = 0; j <= 7; j++){
				boardArray[i][j] = new Square(colorsArr[i][j]);
			}
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

	public void defocusAll() {
		for (Square square : getSquaresAsList()) {
			if (square.isFocused())
				square.defocus();
		}
	}
	
	public void dehoverAll() {
		for (Square square : getSquaresAsList()) {
			if (square.isHovered())
				square.dehover();
		}
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

	public boolean isSpeedMode() {
		return speedMode;
	}

	public boolean makeMove(Square srcSq, Square destSq) {
		if (!MoveValidator.isLegalMove(this, srcSq, destSq))
			return false;
		if(!destSq.isOccupied()) {
			Tower t = srcSq.getTower();
			srcSq.clearSquare();
			destSq.setTower(t);
			currentTowerColor.setValue(destSq.getColor());
		} else {
			int nextColor = sumoPush(destSq);
			Tower t = srcSq.getTower();
			srcSq.clearSquare();
			destSq.setTower(t);
			currentTowerColor.setValue(nextColor);
		}
		currentPlayer.incrementMoveCount();
		if (MoveValidator.isRoundEnd(this)) {
			endRound = true;
			MoveValidator.getWinningTower(this).upgrade();
			currentPlayer.addPoints(MoveValidator.getWinningTower(this));
		}
		else {
			switchSide();
			if(MoveValidator.isBlocked(this)) {
				if(MoveValidator.isDeadlock(this)) {
					switchSide();
					endRound = true;
					MoveValidator.getDeadlockWinningTower(this, destSq).upgrade();
					currentPlayer.addPoints(MoveValidator.getDeadlockWinningTower(this, destSq));
				} else {
					switchSide();
				}
			}
		}
		/*
		if (validator.isDeadlock())
			handleDeadLock();
		 */
		return true;
	}

	public boolean makeMove(Move move) {
		Square srcSq = getSquare(move.srcRow, move.srcCol);
		Square destSq = getSquare(move.dstRow, move.dstCol);
		
		return makeMove(srcSq, destSq);
	}
	
	//return color value of the last tile of push
	private int sumoPush(Square firstSq) {
		
		int row = getSquareRowCoord(firstSq);
		int col = getSquareColCoord(firstSq);
		int lastRow = row;
		int i = 1;
		if(getCurrentPlayerValue() == PlayerColor.BLACK) {
			while(getSquare(row+i, col).isOccupied()) {
				i++;
			}
			lastRow = row+i;
			for(int j = 0; j < i; j++) {
				boardArray[row+i-j][col].setTower(getSquare(row+i-j-1, col).getTower());
			}
		} else {
			while(getSquare(row-i, col).isOccupied()) {
				i++;
			}
			lastRow = row-i;
			for(int j = 0; j < i; j++) {
				boardArray[row-i-j][col].setTower(getSquare(row-i-j+1, col).getTower());
			}
		}
		
		firstSq.clearSquare();
		
		return getSquare(lastRow, col).getColor();
		
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
		round++;
		switchSide();
		endRound = false;
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
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		
		JSONArray boardArr = new JSONArray();
		for(int i = 0; i <=7 ; i++){
			JSONArray rowArr = new JSONArray();
			for(int j = 0; j <= 7 ; j++) {
				rowArr.put(boardArray[i][j].getJSON());
			}
			boardArr.put(rowArr);
		}
		
		json.put("board", boardArr);
		json.put("currentPlayer", currentPlayer.getColorValue());
		json.put("currentTowerColor", currentTowerColor.getValue());
		json.put("player1", player1.getJSON());
		json.put("player2", player2.getJSON());
		json.put("round", round);
		json.put("pointsLimit", pointsLimit);
		json.put("speedMode", speedMode);
		json.put("random", randomBoard);
		
		return json;
	}

	public boolean equals(Object b) {
		return toString().equals(b.toString());
	}


}
