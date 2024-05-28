package net.ruytaro.chess;

import java.util.Scanner;

import net.ruytaro.chess.pieces.Pawn;
import net.ruytaro.chess.pieces.Queen;

public class Chess {

	public static void main(String[] args) {
		Board game = new Board();
		// game.initBoard();
		game.initBoard(new Pawn(Color.WHITE));
		playGame(game);
	}

	public static void playGame(Board game) {
		Scanner sc = new Scanner(System.in);
		Color[] turn = { Color.WHITE};//, Color.BLACK };
		game.setStatus(GameStatus.ONGOING);

		boolean quit = false;
		do {
			for (Color player : turn) {
				game.setPlayer(player);
				if (quit) {
					game.setStatus(GameStatus.END);
					break;
				}
				System.out.print(game.showBoard());
				boolean ok = false;
				while (!ok) {
					String in = getInput(sc, player);
					if (in.equals("QUIT")) {
						quit = true;
						break;
					}
					ok = game.movePiece(in.toCharArray());
				}
			}
		} while (gameOngoing(game));
		sc.close();
		System.out.printf("Player %s wins!!", game.getPlayer());
	}

	private static boolean gameOngoing(Board game) {
		return game.getStatus().equals(GameStatus.ONGOING);
	}

	private static String getInput(Scanner sc, Color p) {
		String h = "=";
		if (p.equals(Color.BLACK))
			h = ">";
		System.out.print(h);
		return sc.nextLine().toUpperCase();
	}
}
