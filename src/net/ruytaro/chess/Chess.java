package net.ruytaro.chess;

public class Chess {
	
	public static void main(String[] args) {
		Board game = new Board();
		game.initBoard();
		System.out.print(game.showBoard());
	}
}
