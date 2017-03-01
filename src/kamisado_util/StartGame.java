package kamisado_util;

import kamisado_gui.MainMenu;

public class StartGame implements Runnable {

	private SoundTrack soundTrack;
	MainMenu menu;

	public StartGame(SoundTrack st) {
		soundTrack = st;
	}

	@Override
	public void run() {
		menu = new MainMenu(soundTrack);
	}

}
