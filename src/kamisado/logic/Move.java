package kamisado.logic;

public class Move {

	public int srcRow;
	public int srcCol;
	public int dstRow;
	public int dstCol;
	
	public Move(int srcRow, int srcCol, int dstRow, int dstCol) {
		this.srcRow = srcRow;
		this.srcCol = srcCol;
		this.dstRow = dstRow;
		this.dstCol = dstCol;
	}
	
	public String toString() {
		return "["+srcRow+"]["+srcCol+"] to ["+dstRow+"]["+dstCol+"]";
	}

}
