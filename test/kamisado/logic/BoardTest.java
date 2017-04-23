package kamisado.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import kamisado.logic.Board;
import kamisado.logic.Player;
import kamisado.logic.PlayerColor;

public class BoardTest {

	@Test
	public void equalityTest1() {
		Player p1 = new Player(PlayerColor.WHITE, "name1");
		Player p2 = new Player(PlayerColor.BLACK, "name2");
		Board b = new Board(p1, p2, 1, false, false);
		Board c = new Board(p1, p2, 1, false, false);
		assertEquals("Both boards should be the same\n",b,c);
	}
	
	@Test
	public void equalityTest2() {
		Player p1 = new Player(PlayerColor.WHITE, "name1");
		Player p2 = new Player(PlayerColor.BLACK, "name2");
		Board b = new Board(p1, p2, 1, false, false);
		Board c = new Board(b);
		assertEquals("Both boards should be the same\n",b,c);
	}
	
	@Test
	public void randomBoardTest() {
		Player p1 = new Player(PlayerColor.WHITE, "name1");
		Player p2 = new Player(PlayerColor.BLACK, "name2");
		Board b = new Board(p1, p2, 1, false, true);
		assertEquals("Both boards should be the same\n",b, 1);
	}

}
