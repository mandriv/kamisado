package kamisado_GUI_listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import kamisado_GUI_frames.BoardGUI;
import kamisado_logic.Board;

public class KeyPressListener implements KeyListener {

	private BoardGUI gui;
	private Board boardGrid;

	public KeyPressListener(BoardGUI gui) {
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
