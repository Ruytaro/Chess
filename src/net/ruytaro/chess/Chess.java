package net.ruytaro.chess;

import java.util.Scanner;

public class Chess {

	public static void main(String[] args) {
		Board game = new Board();
		game.initBoard();
		playGame(game);
	}

	public static void playGame(Board game) {
		Scanner sc = new Scanner(System.in);
		game.start();
		do {
			System.out.print(game.showBoard());
			boolean ok = false;
			while (!ok) {
				String in = getInput(sc, game.getPlayer());
				if (in.equals("QUIT")) {
					game.end();
					break;
				}
				ok = game.movePiece(in);
			}
		} while (gameOngoing(game));
		sc.close();
		System.out.printf("Player %s wins!!", game.getPlayer());
		System.out.print(game.getMovements());
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
