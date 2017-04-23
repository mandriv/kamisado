package kamisado.util;

import kamisado.logic.Board;
import kamisado.logic.Player;
import kamisado.logic.PlayerColor;

public class ThreadDriver { // NO_UCD (unused code)

	public static void main(String[] args) {
		
		SoundTrack st = new SoundTrack(); 
		Thread music = new Thread(st);
		Thread game = new Thread(new StartGame(st));
		game.start();
		music.start();
		
	}
	
}
