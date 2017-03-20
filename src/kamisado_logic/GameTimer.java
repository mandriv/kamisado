package kamisado_logic;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JProgressBar;

import kamisado_control.GameController;

public class GameTimer {
	
    Timer timer;
    GameController control;
    JProgressBar progressBar;
    int seconds;

    public GameTimer(int seconds, GameController gc, JProgressBar guiTimerProgressBar) {
        timer = new Timer();
        control = gc;
        this.seconds = seconds;
        progressBar = guiTimerProgressBar;
	}
    
    public void start() {
    	timer.schedule(new RemindTask(), seconds*1000);
    	timer.scheduleAtFixedRate(new ProgressTask(), 0, 1000);
    }
    
    public void cancel() {
    	timer.cancel();
    }

    class RemindTask extends TimerTask {
        public void run() {
            control.board.switchSide();
            control.addCurrentStateToHistory();
            control.handleEndRound();
            timer.cancel(); //Terminate the timer thread
        }
    }
    
    class ProgressTask extends TimerTask {
    	int i = progressBar.getValue();
		public void run() {
			progressBar.setValue(++i);
		}
    }

}