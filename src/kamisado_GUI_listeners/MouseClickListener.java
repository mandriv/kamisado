package kamisado_GUI_listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import kamisado_GUI_frames.BoardGUI;
import kamisado_logic.Board;
import kamisado_logic.Square;

public class MouseClickListener implements MouseListener {

	private BoardGUI gui;
	private Board boardGrid;

	public MouseClickListener(BoardGUI gui) {
		this.gui = gui;
		boardGrid = gui.boardGrid;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		boolean hasFocused = boardGrid.hasFocusedSquare();
		Square focusedSquare = boardGrid.getFocusedSquare();
		Square clickedSquare = findTileByCoords(x, y);

		//if second click (1. On tower 2. On Empty space
		if (hasFocused && !clickedSquare.isOccupied() ) {
			if (boardGrid.makeMove(focusedSquare, clickedSquare)) {
				focusedSquare.defocus();
			}
		}
		
		Square movableSquare = boardGrid.getCurrentMovableSquare();
		
		//if not first move
		if(movableSquare != null) {
			movableSquare.setFocused();
		} else {
			if(clickedSquare.isOccupied()) {
				if(hasFocused) {
					focusedSquare.defocus();
					clickedSquare.setFocused();
				} else {
					clickedSquare.setFocused();
				}
			}
				
					
		}
		
		boardGrid.markPossibleMoves();
		gui.repaint();
	}

	private Square findTileByCoords(int x, int y) {
		for (Square tile : boardGrid.getTilesAsList()) {
			if (tile.getX() <= x && tile.getX() + tile.getImage().getWidth(null) >= x && tile.getY() <= y
					&& tile.getY() + tile.getImage().getHeight(null) >= y)
				return tile;
		}
		return null;
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
