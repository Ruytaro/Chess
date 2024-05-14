package net.ruytaro.chess;

import java.util.ArrayList;
import java.util.List;

import net.ruytaro.chess.pieces.Bishop;
import net.ruytaro.chess.pieces.King;
import net.ruytaro.chess.pieces.Knight;
import net.ruytaro.chess.pieces.Pawn;
import net.ruytaro.chess.pieces.Piece;
import net.ruytaro.chess.pieces.Queen;
import net.ruytaro.chess.pieces.Rook;

public class Board {

	Piece[][] board = new Piece[8][8];
	List<Piece> death = new ArrayList<Piece>();
	GameStatus status = GameStatus.PREPARING;

	public void initBoard() {
		placePieces(Color.WHITE);
		placePieces(Color.BLACK);
		status = GameStatus.READY;
	}

	public void placePieces(Color c) {
		int p, j;
		if (c.equals(Color.WHITE)) {
			p = 1;
			j = 0;
		} else {
			p = 6;
			j = 7;
		}
		for (int i = 0; i < 8; i++) {
			board[p][i] = new Pawn(c);
		}
		board[j][0] = new Rook(c);
		board[j][1] = new Knight(c);
		board[j][2] = new Bishop(c);
		board[j][3] = new Queen(c);
		board[j][4] = new King(c);
		board[j][5] = new Bishop(c);
		board[j][6] = new Knight(c);
		board[j][7] = new Rook(c);
	}

	public String showBoard() {
		String t = "   A B C D E F G H\n\n";
		for (int i = 0; i < 8; i++) {
			int n = 8-i;
			t += n + "  ";
			for (int j = 0; j < 8; j++) {
				Piece p = board[n-1][j];
				if (p instanceof Piece) {
					t += p + " ";
				} else {
					t += "· ";
				}
			}
			t += " " + n + "\n";
		}
		t += "\n   A B C D E F G H\n";
		return t;
	}

	public Board() {

	}
}
