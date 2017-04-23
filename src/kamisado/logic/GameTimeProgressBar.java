package kamisado.logic;

import java.util.Timer;
import javax.swing.JProgressBar;

public class GameTimeProgressBar extends JProgressBar{
	
	private static final long serialVersionUID = 1L;
	
	Timer timer;

	public GameTimeProgressBar(int seconds) {
		super(0, seconds);
		setValue(0);
	}

}
