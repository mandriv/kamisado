package kamisado_logic;

public class GameColor {
	
	public static final int ORANGE = 0;
	public static final int BLUE = 1;
	public static final int PURPLE = 2;
	public static final int PINK = 3;
	public static final int YELLOW = 4;
	public static final int RED = 5;
	public static final int GREEN = 6;
	public static final int BROWN = 7;

	int value;
	
	public GameColor(int x) {
		if(x<0 || x>7)
			throw new IllegalArgumentException("Value has to be between 0 and 7!");
		value = x;

	}

	@Override
	public String toString() {
		switch(value){
			case 0 : return "orange";
			case 1 : return "blue";
			case 2 : return "purple";
			case 3 : return "pink";
			case 4 : return "yellow";
			case 5 : return "red";
			case 6 : return "green";
			case 7 : return "brown";
		}
		throw new IllegalStateException();
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int x){
		value = x;
	}
	
}
