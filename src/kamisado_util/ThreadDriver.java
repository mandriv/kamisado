package kamisado_util;

import kamisado_mp.MultiplayerClient;

public class ThreadDriver {

	public static void main(String[] args) {
		SoundTrack st = new SoundTrack(); Thread music = new Thread(st);
		Thread game = new Thread(new StartGame(st));
		game.start();
		music.start();
	}
	
}
