package kamisado_gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import kamisado_logic.*;

public class MouseClickListener implements MouseListener{
	
	BoardGui gui;
	BoardGrid boardGrid;
	ArrayList<Square> tiles;

	public MouseClickListener(BoardGui gui) {
		this.gui = gui;
		boardGrid = gui.boardGrid;
		tiles = boardGrid.getTilesAsList();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//EXCEPTION if out of bounds! upiekszyc to też
		Square clickedTile = findTileByCoords(x, y);
		if(!boardGrid.hasFocusedSquare() && clickedTile.isOccupied()){
			boardGrid.setFocused(clickedTile);
		}
		else if(boardGrid.hasFocusedSquare() && !clickedTile.isOccupied()){
			boardGrid.makeMove(boardGrid.getFocused(), clickedTile);
			boardGrid.defocus();
			gui.repaint();
		}
		else if(boardGrid.hasFocusedSquare() && clickedTile.isOccupied()){
			boardGrid.defocus();
		}
	}
	
    private Square findTileByCoords(int x, int y){
    	for(Square tile : tiles){
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
