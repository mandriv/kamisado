package kamisado_util;

import kamisado_gui.MainMenu;

public class StartGame implements Runnable{
	
	private SoundTrack soundTrack;
	
	public StartGame(SoundTrack st) {
		soundTrack = st;
	}

	@Override
	public void run() {
		MainMenu menu = new MainMenu(soundTrack);
	}

}
