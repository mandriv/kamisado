package kamisado.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import kamisado_logic.Board;
import kamisado_logic.Player;
import kamisado_logic.PlayerColor;

public class BoardTest {

	@Test
	public void equalityTest1() {
		Player p1 = new Player(PlayerColor.WHITE, "name1");
		Player p2 = new Player(PlayerColor.BLACK, "name2");
		Board b = new Board(p1, p2, 1, false);
		Board c = new Board(p1, p2, 1, false);
		assertEquals("Both boards should be the same\n",b,c);
	}
	
	@Test
	public void equalityTest2() {
		Player p1 = new Player(PlayerColor.WHITE, "name1");
		Player p2 = new Player(PlayerColor.BLACK, "name2");
		Board b = new Board(p1, p2, 1, false);
		Board c = new Board(b);
		assertEquals("Both boards should be the same\n",b,c);
	}

}
