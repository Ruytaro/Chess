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
			String[] moves = { "a2a4", "b7b5", "a4b5", "a7a6", "b5a6", "a8a6", "a1a6", "b8a6", "b2b4", "a6b4", "c2c3",
					"b4a2", "c1a3", "a2c3", "d2d4", "c3d1", "e1d1", "h7h5", "g2g4", "h5g4", "h2h3", "g4h3", "h1h3",
					"h8h3", "g1f3", "h3h1", "d1d2", "h1f1", "a3c1", "f1d1", "d2c2", "d1c1", "c2b2", "c1b1", "b2a2",
					"b1b4", "f3d2", "b4a4", "a2b2", "a4a2", "b2b3", "a2d2", "b3c3", "d7d5", "c3b3", "c8a6", "b3a3",
					"d2a2", "a3b3", "a2b2", "b3c3", "b2c2","" };
			int m = 0;
			System.out.println(game.showBoard());
			while (game.getStatus().equals(GameStatus.ONGOING)) {
				String move = moves[m].toUpperCase();
				if (move == null)
					break;
				System.out.println(move);
				System.out.print(game.movePiece(move));
				System.out.println(game.showBoard());
				m++;
			}
			System.out.printf("\nThe winner is : %s\n",game.getWinner());
		}

	}

}
