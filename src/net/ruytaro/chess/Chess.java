package net.ruytaro.chess;

public class Chess {

	public static void main(String[] args) {
		Board game = new Board();
		game.initBoard();
		System.out.print(game.showBoard());
		String move = "A2A4";
		game.movePiece(move.toCharArray());
		move = "A7A5";
		game.movePiece(move.toCharArray());
		move = "A4A5";
		game.movePiece(move.toCharArray());
		move = "B7B5";
		game.movePiece(move.toCharArray());
		move = "A4B5";
		game.movePiece(move.toCharArray());
		System.out.print(game.showBoard());
	}
}
