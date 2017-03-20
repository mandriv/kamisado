package kamisado_control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPressListener implements KeyListener {

	public KeyPressListener() {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		String chuj = KeyEvent.getKeyText(ke.getKeyCode());
		System.out.println(chuj);
	}

	@Override
	public void keyReleased(KeyEvent ke) {

	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub

	}

}
