package kamisado_logic;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
	
    Timer timer;
    int seconds;

    public GameTimer(int seconds) {
        timer = new Timer();
        this.seconds = seconds;
	}
    
    public void start() {
    	timer.schedule(new RemindTask(), seconds*1000);
    }
    

    class RemindTask extends TimerTask {
        public void run() {
            System.out.format(seconds+" seconds has passed");
            timer.cancel(); //Terminate the timer thread
        }
    }

    public static void main(String args[]) {
        GameTimer t = new GameTimer(5);
        t.start();
    }
}