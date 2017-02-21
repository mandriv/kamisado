package kamisado_logic;

public class Player {

	public static final int WHITE = 0;
	public static final int BLACK = 1;
	
	int player;
	
	public Player(int x) {
		if (x!=0 && x!=1)
			throw new IllegalArgumentException("Invalid value! Only 0 and 1 supported x="+x);
		player = x;
	}
	
	public String toString(){
		if(player == 0)
			return "white";
		return "black";
	}
	
	public int getWhoseTurn(){
		return player;
	}
	
	public void changePlayer(){
		player = (player + 1) % 2;
	}

}
