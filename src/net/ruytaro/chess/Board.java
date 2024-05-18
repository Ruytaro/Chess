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

	private Piece[][] board = new Piece[8][8];
	private List<Piece> death = new ArrayList<Piece>();
	private GameStatus status = GameStatus.PREPARING;

	public void initBoard() {
		placePieces(Color.WHITE);
		placePieces(Color.BLACK);
		setStatus(GameStatus.READY);
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
			board[i][p] = new Pawn(c);
		}
		board[0][j] = new Rook(c);
		board[1][j] = new Knight(c);
		board[2][j] = new Bishop(c);
		board[3][j] = new Queen(c);
		board[4][j] = new King(c);
		board[5][j] = new Bishop(c);
		board[6][j] = new Knight(c);
		board[7][j] = new Rook(c);
	}

	public String showBoard() {
		String t = "   A B C D E F G H\n\n";
		for (int i = 0; i < 8; i++) {
			int n = 8 - i;
			t += n + "  ";
			for (int j = 0; j < 8; j++) {
				Piece p = board[j][n - 1];
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
		int[] dest = calculateOffset(position);
		Piece p = board[position[2]][position[3]];
		boolean eat = false;
		if (p != null) {
			if (p.getPlayer().equals(target.getPlayer())) {
				return false;
			} else {
				eat = true;
			}
		}
		if (target.canMakeMove(dest, eat))
			return true;
		return false;
	}

	private int[] calculateOffset(int[] position) {
		int[] dest = { position[2] - position[0], position[3] - position[1] };
		return dest;
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
		}
		return target;
	}

	public boolean movePiece(char[] movement) {
		int[] position = validateInput(movement);
		if (position == null)
			return false;
		if (!canMakeMovement(position))
			return false;
		makeMove(position);
		return true;

	}

	private void makeMove(int[] position) {
		if (board[position[2]][position[3]] != null)
			death.add(board[position[2]][position[3]]);
		board[position[0]][position[1]].move();
		board[position[2]][position[3]] = board[position[0]][position[1]];
		board[position[0]][position[1]] = null;
		return;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}
}
