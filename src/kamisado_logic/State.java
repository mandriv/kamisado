package kamisado_logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class State {
	
	private String tokenizedBoard; 
	private Board board;
	
	public State(Board board) {
		this.board = board;
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i <=7 ; i++){
			for(int j = 0; j <= 7 ; j++) {
				Square s = board.getSquare(i, j);
				if(s.isOccupied()) {
					if(s.getTower().getOwner() == PlayerColor.BLACK) {
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
	
	public void saveToFile() {
		String path = System.getProperty("user.home") + File.separator + "Kamisado Saves" + File.separator + "save1.txt";
		File file = new File(path);
		
		try {
			file.getParentFile().mkdirs(); 
			file.createNewFile();
		    PrintWriter writer = new PrintWriter(file);
		    writer.print(tokenizedBoard);
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Board getBoard() {
		return board;
	}

}
