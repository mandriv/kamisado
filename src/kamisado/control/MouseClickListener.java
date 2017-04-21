package kamisado.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickListener implements MouseListener {

	private GameController control;

	public MouseClickListener(GameController gc) {
		control = gc;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		control.mouseAction(x, y, true);
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
