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
			int n = 8 - i;
			t += n + "  ";
			for (int j = 0; j < 8; j++) {
				Piece p = board[n - 1][j];
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

	private boolean canMakeMovement(int[] position) {
		Piece target = board[position[0]][position[1]];
		if (target == null)
			return false;
		System.out.println(target);
		boolean eat = board[position[2]][position[3]] != null;
		int[] dest = { position[3] - position[1], position[2] - position[0] };
		if (target.canMakeMove(dest,eat))
			return true;
		return false;
	}

	private int[] validateInput(char[] position) {
		if (position.length != 4)
			return null;
		char[] temp = { 'A', '1', 'A', '1' };
		int[] target = new int[4];
		for (int i = 0; i < 4; i++) {
			int t = position[i] - temp[i];
			if (t > 7 || t < 0)
				return null;
			target[i] = t;
			System.out.print(t + " ");
		}
		return fixInput(target);

	}

	private int[] fixInput(int[] target) {
		int[] fixed = { target[1], target[0], target[3], target[2] };
		return fixed;
	}

	public boolean movePiece(char[] movement) {
		int[] position = validateInput(movement);
		if (position == null)
			return false;
		if (canMakeMovement(position)) {
			makeMove(position);
			return true;
		}
		return false;
	}

	private void makeMove(int[] position) {
		if (board[position[2]][position[3]] != null)
			death.add(board[position[2]][position[3]]);
		board[position[0]][position[1]].move();
		board[position[2]][position[3]] = board[position[0]][position[1]];
		board[position[0]][position[1]] = null;
		return;
	}
}
