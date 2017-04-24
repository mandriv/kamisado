package kamisado.logic;

import static org.junit.Assert.*;

import org.json.JSONObject;
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
	public void moveTest() {
		Board b = new Board(new JSONObject("{\"pointsLimit\":3,\"random\":false,\"player1\":{\"score\":0,\"color\":0,\"name\":\"Jack\",\"aiDifficulty\":0,\"moveCount\":1},\"round\":1,\"currentPlayer\":0,\"player2\":{\"score\":0,\"color\":1,\"name\":\"Computer\",\"aiDifficulty\":1,\"moveCount\":13},\"currentTowerColor\":6,\"speedMode\":false,\"board\":[[{\"color\":0,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":0}},{\"color\":1,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":1}},{\"color\":2,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":2}},{\"color\":3,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":3}},{\"color\":4},{\"color\":5,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":5}},{\"color\":6,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":6}},{\"color\":7,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":7}}],[{\"color\":5},{\"color\":0},{\"color\":3},{\"color\":6,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":4}},{\"color\":1},{\"color\":4},{\"color\":7},{\"color\":2}],[{\"color\":6},{\"color\":3},{\"color\":0},{\"color\":5},{\"color\":2},{\"color\":7},{\"color\":4},{\"color\":1}],[{\"color\":3},{\"color\":2},{\"color\":1},{\"color\":0},{\"color\":7},{\"color\":6},{\"color\":5},{\"color\":4}],[{\"color\":4,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":4}},{\"color\":5},{\"color\":6},{\"color\":7},{\"color\":0},{\"color\":1},{\"color\":2},{\"color\":3}],[{\"color\":1},{\"color\":4},{\"color\":7},{\"color\":2},{\"color\":5},{\"color\":0},{\"color\":3},{\"color\":6}],[{\"color\":2},{\"color\":7},{\"color\":4},{\"color\":1},{\"color\":6},{\"color\":3},{\"color\":0},{\"color\":5}],[{\"color\":7,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":7}},{\"color\":6,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":6}},{\"color\":5,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":5}},{\"color\":4},{\"color\":3,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":3}},{\"color\":2,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":2}},{\"color\":1,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":1}},{\"color\":0,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":0}}]]}"));
		b.defocusAll();
		b.getCurrentMovableSquare().setFocused();
		b.markPossibleMoves();
		boolean a = b.makeMove(b.getSquare(7, 1), b.getSquare(5, 1));
		assertEquals("?",true, a);
	}
	
	@Test
	public void moveTest2() {
		Board b = new Board(new JSONObject("{\"pointsLimit\":3,\"random\":false,\"player1\":{\"score\":0,\"color\":0,\"name\":\"Jack\",\"aiDifficulty\":0,\"moveCount\":1},\"round\":1,\"currentPlayer\":0,\"player2\":{\"score\":0,\"color\":1,\"name\":\"Computer\",\"aiDifficulty\":1,\"moveCount\":13},\"currentTowerColor\":6,\"speedMode\":false,\"board\":[[{\"color\":0,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":0}},{\"color\":1,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":1}},{\"color\":2,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":2}},{\"color\":3,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":3}},{\"color\":4},{\"color\":5,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":5}},{\"color\":6,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":6}},{\"color\":7,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":7}}],[{\"color\":5},{\"color\":0},{\"color\":3},{\"color\":6,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":4}},{\"color\":1},{\"color\":4},{\"color\":7},{\"color\":2}],[{\"color\":6},{\"color\":3},{\"color\":0},{\"color\":5},{\"color\":2},{\"color\":7},{\"color\":4},{\"color\":1}],[{\"color\":3},{\"color\":2},{\"color\":1},{\"color\":0},{\"color\":7},{\"color\":6},{\"color\":5},{\"color\":4}],[{\"color\":4,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":4}},{\"color\":5},{\"color\":6},{\"color\":7},{\"color\":0},{\"color\":1},{\"color\":2},{\"color\":3}],[{\"color\":1},{\"color\":4},{\"color\":7},{\"color\":2},{\"color\":5},{\"color\":0},{\"color\":3},{\"color\":6}],[{\"color\":2},{\"color\":7},{\"color\":4},{\"color\":1},{\"color\":6},{\"color\":3},{\"color\":0},{\"color\":5}],[{\"color\":7,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":7}},{\"color\":6,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":6}},{\"color\":5,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":5}},{\"color\":4},{\"color\":3,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":3}},{\"color\":2,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":2}},{\"color\":1,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":1}},{\"color\":0,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":0}}]]}"));
		b.defocusAll();
		b.getCurrentMovableSquare().setFocused();
		b.markPossibleMoves();
		b.makeMove(b.getSquare(7, 1), b.getSquare(5, 1));
		b.defocusAll();
		b.getCurrentMovableSquare().setFocused();
		b.markPossibleMoves();
		boolean a = b.makeMove(b.getSquare(1, 3), b.getSquare(7, 3));
		assertEquals("?",true, a);
	}
	
	@Test
	public void pointsTest() {
		Board b = new Board(new JSONObject("{\"pointsLimit\":3,\"random\":false,\"player1\":{\"score\":0,\"color\":0,\"name\":\"Jack\",\"aiDifficulty\":0,\"moveCount\":1},\"round\":1,\"currentPlayer\":0,\"player2\":{\"score\":0,\"color\":1,\"name\":\"Computer\",\"aiDifficulty\":1,\"moveCount\":13},\"currentTowerColor\":6,\"speedMode\":false,\"board\":[[{\"color\":0,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":0}},{\"color\":1,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":1}},{\"color\":2,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":2}},{\"color\":3,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":3}},{\"color\":4},{\"color\":5,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":5}},{\"color\":6,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":6}},{\"color\":7,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":7}}],[{\"color\":5},{\"color\":0},{\"color\":3},{\"color\":6,\"tower\":{\"owner\":1,\"sumo\":0,\"color\":4}},{\"color\":1},{\"color\":4},{\"color\":7},{\"color\":2}],[{\"color\":6},{\"color\":3},{\"color\":0},{\"color\":5},{\"color\":2},{\"color\":7},{\"color\":4},{\"color\":1}],[{\"color\":3},{\"color\":2},{\"color\":1},{\"color\":0},{\"color\":7},{\"color\":6},{\"color\":5},{\"color\":4}],[{\"color\":4,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":4}},{\"color\":5},{\"color\":6},{\"color\":7},{\"color\":0},{\"color\":1},{\"color\":2},{\"color\":3}],[{\"color\":1},{\"color\":4},{\"color\":7},{\"color\":2},{\"color\":5},{\"color\":0},{\"color\":3},{\"color\":6}],[{\"color\":2},{\"color\":7},{\"color\":4},{\"color\":1},{\"color\":6},{\"color\":3},{\"color\":0},{\"color\":5}],[{\"color\":7,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":7}},{\"color\":6,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":6}},{\"color\":5,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":5}},{\"color\":4},{\"color\":3,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":3}},{\"color\":2,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":2}},{\"color\":1,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":1}},{\"color\":0,\"tower\":{\"owner\":0,\"sumo\":0,\"color\":0}}]]}"));
		b.defocusAll();
		b.getCurrentMovableSquare().setFocused();
		b.markPossibleMoves();
		b.makeMove(b.getSquare(7, 1), b.getSquare(5, 1));
		b.defocusAll();
		b.getCurrentMovableSquare().setFocused();
		b.markPossibleMoves();
		b.makeMove(b.getSquare(1, 3), b.getSquare(7, 3));
		assertEquals("?",1, b.currentPlayer.getScore());
	}

}
