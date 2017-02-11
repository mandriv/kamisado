package kamisado_logic;

public class FocusableSquare{
	
	boolean focused;
	Square square;
	
	public FocusableSquare(Square square, boolean focused){
		this.focused = focused;
		this.square = square;
	}
	
	public void setFocused(Square sq){
		square = sq;
		focused = true;
	}
	
	public void defocus(){
		square = null;
		focused = false;
	}
	
	public boolean isFocused(){
		return focused;
	}
	
	public Square getSquare(){
		return square;
	}

}
