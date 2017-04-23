package kamisado.logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class State {
	
	private String tokenizedBoard; 
	private Board savedBoard;
	
	public State(Board board) {
		savedBoard = new Board(board);
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i <=7 ; i++){
			for(int j = 0; j <= 7 ; j++) {
				Square s = board.getSquare(i, j);
				if(s.isOccupied()) {
					if(s.getTower().getOwnerColorValue() == PlayerColor.BLACK) {
						builder.append("b");
					} else {
						builder.append("w");
					}
					builder.append(s.getTower().getColorValue());
				} else {
					builder.append("xx");
				}
				if(!(j ==7 && i == 7)){
					builder.append(",");
				}
			}
		}
		tokenizedBoard = builder.toString();
	}
	
	@Override
	public String toString() {
		return tokenizedBoard;
	}
	
	public void saveToFile(String fileName) {
		String path = System.getProperty("user.home") + File.separator + "Kamisado Saves" + File.separator + fileName + ".txt";
		File file = new File(path);
		
		try {
			file.getParentFile().mkdirs(); 
			file.createNewFile();
		    PrintWriter writer = new PrintWriter(file);
		    writer.println(tokenizedBoard);
		    writer.println(savedBoard.getCurrentPlayerValue());
		    writer.println(savedBoard.getCurrentTowerColorValue());
		    writer.println(savedBoard.player1.getMoveCount());
		    writer.println(savedBoard.player2.getMoveCount());
		    writer.println(savedBoard.player1.getName());
		    writer.println(savedBoard.player2.getName());
		    writer.println(savedBoard.player1.getScore());
		    writer.println(savedBoard.player2.getScore());
		    writer.println(savedBoard.getRoundNumber());
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Board getBoard() {
		return savedBoard;
	}

}
