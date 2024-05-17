package net.ruytaro.chess;

public class Chess {

	public static void main(String[] args) {
		Board game = new Board();
		game.initBoard();
		System.out.print(game.showBoard());
		String move = "A2A4";
		if (game.movePiece(move.toCharArray()))
			System.out.print(game.showBoard());
		move = "B7B5";
		if (game.movePiece(move.toCharArray()))
			System.out.print(game.showBoard());
		move = "A4B5";
		if (game.movePiece(move.toCharArray()))
			System.out.print(game.showBoard());
		move = "A4A5";
		if (game.movePiece(move.toCharArray()))
			System.out.print(game.showBoard());
	}
}
