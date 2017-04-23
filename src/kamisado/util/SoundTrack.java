package kamisado.util;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundTrack implements Runnable {

	private URL mainThemeURL;
	private AudioInputStream ais;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip clip;

	public SoundTrack() {
		try {
			mainThemeURL = getClass().getResource("/kamisado_media/sound/mainTheme.wav");
			ais = AudioSystem.getAudioInputStream(mainThemeURL);
			format = ais.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playMainThemeOnLoop() {
		try {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mute() {
		clip.stop();
	}

	@Override
	public void run() {
	}

}
