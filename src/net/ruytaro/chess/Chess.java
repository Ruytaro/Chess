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
		Color[] turn = { Color.WHITE, Color.BLACK };
		boolean quit = false;
		do {
			for (Color player : turn) {
				System.out.print(game.showBoard());
				boolean ok;
				do {
					String in = getInput(sc, player);
					if (in.equals("QUIT")) {
						quit = true;
					}
					ok = game.movePiece(in.toCharArray());
				} while (!(ok || quit));
				if (quit)
					break;
			}
		} while (!quit);
		sc.close();
	}

	private static String getInput(Scanner sc, Color p) {
		String h = "=";
		if (p.equals(Color.BLACK))
			h = ">";
		System.out.print(h);
		return sc.nextLine().toUpperCase();
	}
}
