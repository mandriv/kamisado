package kamisado_gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import kamisado_logic.BoardGrid;

public class KeyPressListener implements KeyListener {

	private BoardGui gui;
	private BoardGrid boardGrid;

	public KeyPressListener(BoardGui gui) {
		this.gui = gui;
		boardGrid = gui.boardGrid;
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
