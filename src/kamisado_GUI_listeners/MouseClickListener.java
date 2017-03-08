package kamisado_GUI_listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import kamisado_GUI_frames.BoardGui;
import kamisado_logic.BoardGrid;
import kamisado_logic.Square;

public class MouseClickListener implements MouseListener {

	private BoardGui gui;
	private BoardGrid boardGrid;

	public MouseClickListener(BoardGui gui) {
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

		if (!hasFocused && clickedSquare.isOccupied()) {
			// check if the right player is clicking
			if (clickedSquare.getTower().getOwner() == boardGrid.getGameState().getWhoseTurn())
				clickedSquare.setFocused();
		} else if (hasFocused && !clickedSquare.isOccupied()) {
			if (boardGrid.makeMove(focusedSquare, clickedSquare))
				focusedSquare.defocus();
		} else if (hasFocused && clickedSquare.isOccupied()) {
			focusedSquare.defocus();
			clickedSquare.setFocused();
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
