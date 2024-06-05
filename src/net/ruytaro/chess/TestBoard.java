package net.ruytaro.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoard {

	Board game;

	@BeforeEach
	public void setUp() throws Exception {
		game = new Board();
	}

	@Nested
	@DisplayName("Test game...")
	class Game {
		@Test
		@DisplayName("Until init")
		public void checkConstructor() throws Exception {
			GameStatus status = game.getStatus();
			assertEquals(status, GameStatus.PREPARING);
		}

		@Test
		@DisplayName("once initialized")
		public void checkInit() throws Exception {
			game.initBoard();
			GameStatus status = game.getStatus();
			assertEquals(status, GameStatus.READY);
		}

		@Test
		@DisplayName("play a test game")
		public void playGame() throws Exception {
			game.initBoard();
			game.start();
			String[] moves = { "a2a4", "b7b5", "a4b5", "a7a6", "b5a6", "a8a6", "a1a6", "b8a6", "b2b4", "a6b4",
					"c2c3", "b4a2", "c1b2", null,  };
			int m = 0;
			while (game.getStatus().equals(GameStatus.ONGOING)) {
				//System.out.println(game.showBoard());
				String move = moves[m];
				if (move == null)
					break;
				System.out.println(move);
				assertEquals(game.movePiece(move.toUpperCase()), true);
				m++;
			}
		}

	}

}
