package net.ruytaro.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBoard {

	Board board;

	@BeforeEach
	public void setUp() throws Exception {
		board = new Board();
	}

	@Nested
	@DisplayName("Game initialization...")
	class Game {
		@Test
		@DisplayName("Until init")
		public void checkConstructor() throws Exception {
			GameStatus status = board.getStatus();
			assertEquals(status, GameStatus.PREPARING);
		}
		@Test
		@DisplayName("once initialized")
		public void checkInit() throws Exception {
			board.initBoard();
			GameStatus status = board.getStatus();
			assertEquals(status, GameStatus.READY);
		}

	}
	
}
