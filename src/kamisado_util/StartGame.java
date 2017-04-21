package kamisado_util;

import kamisado_GUI_frames.MainMenu;

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
