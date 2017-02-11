package kamisado_logic;

import java.util.ArrayList;

public class BoardGrid {
	
	private Square[][] board;
	public FocusableSquare focus;

	public BoardGrid() {
		board =  new Square[8][8];
		
		for(int i=0;i<8;i++)
			board[0][i] = new Square(GameColor.ORANGE+i,new Tower(GameColor.ORANGE+i,Player.BLACK));
		
		int a=0,b=1,c=2,d=3,e=4,f=5,g=6,h=7;
		for(int i=1;i<7;i++){
			board[i][a] = new Square(GameColor.RED);
			a = (a + 3) % 8;
			board[i][b] = new Square(GameColor.ORANGE);
			b = (b + 1) % 8;
			board[i][c] = new Square(GameColor.PINK);
			c = (c + 7) % 8;
			board[i][d] = new Square(GameColor.GREEN);
			d = (d + 5) % 8;
			board[i][e] = new Square(GameColor.BLUE);
			e = (e + 11) % 8;
			board[i][f] = new Square(GameColor.YELLOW);
			f = (f + 1) % 8;
			board[i][g] = new Square(GameColor.BROWN);
			g = (g + 7) % 8;
			board[i][h] = new Square(GameColor.PURPLE);
			h = (h + 5) % 8;
		}
		
		for(int i=0;i<8;i++)
			board[7][i] = new Square(GameColor.BROWN-i,new Tower(GameColor.BROWN-i,Player.WHITE));
		
		focus = new FocusableSquare(null, false);
	}
	
	public Square getSquare(int x, int y){
		return board[x][y];
	}
	
	public ArrayList<Square> getTilesAsList(){
		ArrayList<Square> tiles = new ArrayList<>();
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				tiles.add(getSquare(i, j));
			}
		}
		return tiles;
	}

	public void setFocused(Square square){
		focus.setFocused(square);
	}
	
	public Square getFocused(){
		return focus.getSquare();
	}
	
	public void defocus(){
		focus.defocus();
	}
	
	public boolean hasFocusedSquare(){
		return focus.isFocused();
	}
	
	public void makeMove(Square srcSq, Square destSq){
		Tower t = srcSq.getTower();	
		srcSq.clearSquare();
		destSq.setTower(t);	
	}

}
