package kamisado.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPressListener implements KeyListener {

	GameController control;

	public KeyPressListener(GameController gc) {
		control = gc;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		control.keyAction(whatKey(ke.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent ke) {

	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub

	}

	private String whatKey(int keyCode) {
		switch(keyCode) {
		case 37: return "LEFT";
		case 38: return "UP";
		case 39: return "RIGHT";
		case 40: return "DOWN";
		case 65: return "LEFT";
		case 68: return "RIGHT";
		case 83: return "DOWN";
		case 87: return "UP";
		default: return KeyEvent.getKeyText(keyCode).toUpperCase();
		}
	}

}
