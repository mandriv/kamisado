package kamisado_control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import kamisado_logic.Square;

public class MouseListener implements MouseMotionListener {
	
	private GameController control;
	private Square previousSquare;

	public MouseListener(GameController gc) {
		control = gc;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		control.mouseAction(x, y, false);
	}

}
