package kamisado_gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import kamisado_logic.*;

public class MouseClickListener implements MouseListener{
	
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
		
		if(!hasFocused && clickedSquare.isOccupied()){
			//check if the right player is clicking
			if(clickedSquare.getTower().getOwner()==boardGrid.getGameState().getWhoseTurn())
				clickedSquare.setFocused();
		}
		else if(hasFocused && !clickedSquare.isOccupied()){
			boardGrid.makeMove(focusedSquare, clickedSquare);
			focusedSquare.defocus();
		}
		else if(hasFocused && clickedSquare.isOccupied()){
			focusedSquare.defocus();
			clickedSquare.setFocused();
		}
		
		gui.repaint();
	}
	
    private Square findTileByCoords(int x, int y){
    	for(Square tile : boardGrid.getTilesAsList()){
    		if (tile.getX() <= x
    		 && tile.getX()+tile.getImage().getWidth(null) >= x
    	     && tile.getY() <= y
    	     && tile.getY()+tile.getImage().getHeight(null) >= y)
    			return tile;
    	}
    	return null;
    }
	
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
