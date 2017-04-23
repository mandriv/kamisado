package kamisado.util;

import kamisado.GUIframes.MainMenu;

public class StartGame implements Runnable {

	private SoundTrack soundTrack;
	private MainMenu menu; // NO_UCD (unused code)

	public StartGame(SoundTrack st) {
		soundTrack = st;
	}

	//This gets called anyway
	@Override
	public void run() {
		menu = new MainMenu(soundTrack);
	}

}
