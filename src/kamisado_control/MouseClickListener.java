package kamisado_control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import kamisado_GUI_frames.BoardGUI;
import kamisado_logic.Board;
import kamisado_logic.Square;

public class MouseClickListener implements MouseListener {

	private BoardGUI gui;
	private GameController control;

	public MouseClickListener(GameController gc) {
		control = gc;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		control.mouseAction(x, y);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
