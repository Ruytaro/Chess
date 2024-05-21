package net.ruytaro.chess;

import java.util.LinkedList;
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
	private List<Piece> death = new LinkedList<Piece>();
	private GameStatus status = GameStatus.PREPARING;
	private List<int[]> movements = new LinkedList<int[]>();
	private Color player;

	public Color getPlayer() {
		return player;
	}

	public void setPlayer(Color player) {
		this.player = player;
	}

	public void initBoard() {
		placePieces(Color.WHITE);
		placePieces(Color.BLACK);
		setStatus(GameStatus.READY);
	}

	public void initBoard(Piece p) {
		board[3][4] = p;
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

	// checks if the movement is allowed
	private boolean canMakeMovement(int[] move) {
		Piece target = board[move[0]][move[1]];
		if (target == null)
			return false;
		if (!target.getPlayer().equals(getPlayer()))
			return false;
		int[] offset = calculateOffset(move);
		Piece destPlace = board[move[2]][move[3]];
		boolean eat = false;
		if (destPlace != null) {
			if (destPlace.getPlayer().equals(target.getPlayer())) {
				return false;
			} else {
				eat = true;
			}
		}
		if (target.canMakeMove(offset, eat)) {
			if (target instanceof Knight)
				return true;
			return pathClear(move, eat);
		}
		return false;
	}

	// checks if the path to move is clear
	private boolean pathClear(int[] position, boolean eats) {
		int maxX = Math.max(position[0], position[2]);
		int maxY = Math.max(position[1], position[3]);
		int minX = Math.min(position[0], position[2]);
		int minY = Math.min(position[1], position[3]);

		if (maxX == minX) {
			for (int y = minY + 1; y < maxY; y++) {
				System.out.printf("%d:%d -> %s\n", minX, y, board[minX][y]);
				if (board[minX][y] != null)
					if (!skipCell(eats, position, minX, y))
						return false;
			}
		} else if (maxY == minY) {
			for (int x = minX + 1; x < maxX; x++) {
				System.out.printf("%d:%d -> %s\n", x, minY, board[x][minY]);
				if (board[x][minY] != null)
					if (!skipCell(eats, position, x, minY))
						return false;
			}
		} else {
			int df = minY - minX;
			for (int x = minX; x <= maxX; x++) {
				System.out.printf("%d:%d -> %s\n", x, x + df, board[x][x + df]);
				if (board[x][x + df] != null) {
					if (!skipCell(eats, position, x, x + df))
						return false;
				}
			}
		}
		return true;
	}

	// skips the end movement cell
	private boolean skipCell(boolean eats, int[] temp, int x, int y) {
		return (eats || (x == temp[2] && y == temp[3]) || (x == temp[0] && y == temp[1]));
	}

	// calculate the movement relative delta
	private int[] calculateOffset(int[] position) {
		int[] dest = { position[2] - position[0], position[3] - position[1] };
		return dest;
	}

	// parses and validates the input string
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

	// tries to move the pieces
	public boolean movePiece(char[] movement) {
		int[] move = validateInput(movement);
		if (move == null)
			return false;
		if (!canMakeMovement(move))
			return false;
		movements.add(move);
		makeMove(move);
		return true;

	}

	// actually moves the piece on the board
	private void makeMove(int[] position) {
		if (board[position[2]][position[3]] != null) {
			Piece p = board[position[2]][position[3]];
			if (p instanceof King)
				status = GameStatus.END;
			death.add(p);
			board[position[2]][position[3]] = null;

		}
		board[position[0]][position[1]].move();
		board[position[2]][position[3]] = board[position[0]][position[1]];
		board[position[0]][position[1]] = null;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}
}
