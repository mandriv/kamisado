package kamisado.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONObject;

public class Stats {
	
	public static ArrayList<GameStat> getStats() {
		ArrayList<GameStat> list = new ArrayList<>();

		try {
			File file = new File(System.getProperty("user.dir") + File.separator + "kamisado.log");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(new GameStat(new JSONObject(line)));
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static int getNumberOfGames() {
		return getStats().size();
	}
	
	public static int getNumberOfWins() {
		int n = 0;
		for(GameStat stat : getStats()) {
			if(!stat.getWinner().isAI())
				n++;
		}
		return n;
	}
	
	public static GameStat getBiggestWin() {
		int n = 0;
		GameStat win = null;
		for(GameStat stat : getStats()) {
			if(!stat.getWinner().isAI()){
				if(stat.getWinner().getScore() > n) {
					n = stat.getWinner().getScore();
					win = stat;
				}
			}
		}
		return win;
	}
	
	public static GameStat getLongestGame() {
		int n = 0;
		GameStat game = null;
		for(GameStat stat : getStats()) {
			if (stat.getHuman().getMoveCount() > n) {
				n = stat.getHuman().getMoveCount();
				game = stat;
			}
		}
		return game;
	}
	
	

}
