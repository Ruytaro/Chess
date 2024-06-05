package net.ruytaro.chess;

import java.util.ArrayList;
import java.util.Arrays;
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
	private List<String> movements = new ArrayList<String>();
	private Color player;
	private Color winner;

	Color[] turn = { Color.WHITE, Color.BLACK };

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
		setPlayer(turn[0]);
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

	// builds the string with the board
	public String showBoard() {
		String legend = "   A B C D E F G H\n";
		String t = legend + "\n";
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
		t += "\n" + legend;
		return t;
	}

	// gets the player's king position
	private int[] getKingPosition(Color player) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece piece = board[x][y];
				if (piece == null)
					continue;
				if (piece instanceof King && piece.getPlayer().equals(player)) {
					int[] position = { 0, 0, x, y };
					return position;
				}
			}
		}
		return null;
	}

	// checks if the movement is allowed
	private boolean canMakeMovement(int[] move, Color player) {
		Piece source = board[move[0]][move[1]];
		if (!validSource(source, player))
			return false;
		int[] offset = calculateOffset(move);
		Piece destination = board[move[2]][move[3]];
		if (!validDestination(destination, player))
			return false;
		if (!source.canMakeMove(offset, destination != null))
			return false;
		if (source instanceof Knight)
			return true;
		return pathClear(move);
	}

	private boolean validSource(Piece target, Color player) {
		if (target == null)
			return false;
		if (target.getPlayer().equals(player))
			return true;
		return false;
	}

	private boolean validDestination(Piece target, Color player) {
		if (target == null)
			return true;
		if (target.getPlayer().equals(player))
			return false;
		return true;
	}

	// checks if the path to move is clear
	private boolean pathClear(int[] position) {
		int maxX = Math.max(position[0], position[2]);
		int maxY = Math.max(position[1], position[3]);
		int minX = Math.min(position[0], position[2]);
		int minY = Math.min(position[1], position[3]);

		if (maxX == minX) {
			for (int y = minY + 1; y < maxY; y++) {
				if (board[minX][y] != null)
					if (!skipCell(position, minX, y))
						return false;
			}
		} else if (maxY == minY) {
			for (int x = minX + 1; x < maxX; x++) {
				if (board[x][minY] != null)
					if (!skipCell(position, x, minY))
						return false;
			}
		} else {
			if ((position[0] - position[1]) == (position[2] - position[3])) {
				int df = position[3] - position[2];
				for (int x = minX; x <= maxX; x++) {
					int y = x + df;
					if (!skipCell(position, x, y)) {
						if (board[x][y] != null) {
							return false;
						}
					}
				}
			} else {

				int df = maxY - minX;
				for (int x = minX; x <= maxX; x++) {
					int y = df + x;
					if (!skipCell(position, x, y)) {
						if (board[x][y] != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	// skips the start and end cell of the move
	private boolean skipCell(int[] temp, int x, int y) {
		if (x == temp[2] && y == temp[3])
			return true;
		if (x == temp[0] && y == temp[1])
			return true;
		return false;
	}

	// calculate the move delta
	private int[] calculateOffset(int[] position) {
		int[] dest = { position[2] - position[0], position[3] - position[1] };
		return dest;
	}

	// parses and validates the input string
	private int[] validateInput(char[] position) {
		char[] pat = { 'A', '1', 'A', '1' };
		int l = position.length;
		if (l < 4 || l > 5)
			return null;
		int[] movement = { 0, 0, 0, 0, 0 };
		for (int p = 0; p < l; p++) {
			int t = 0;
			if (p < 4)
				t = position[p] - pat[p];
			if (t < 0 || t > 7)
				return null;
			if (p == 4)
				t = position[p];
			movement[p] = t;
		}
		return movement;
	}

	// tries to move the pieces
	public boolean movePiece(String movement) {
		int[] move = validateInput(movement.toCharArray());
		if (move == null)
			return false;
		if (!canMakeMovement(move, getPlayer()))
			return false;
		Piece[][] temp = Arrays.copyOf(board, board.length);
		makeMove(move);
		if (isCheck(getPlayer())) {
			board = Arrays.copyOf(temp, temp.length);
			return false;
		}
		movements.add(movement);
		setPlayer(getOponent(getPlayer()));
		if (isCheck(getPlayer()) && hasLost(getPlayer())) {
			setWinner(getOponent(getPlayer()));
			setStatus(GameStatus.END);
		}
		return true;
	}

	private boolean hasLost(Color player) {
		int[] king = getKingPosition(player);
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				int[] move = { king[2], king[3], x, y };
				if (canMakeMovement(move, player)) {
					Piece[][] temp = Arrays.copyOf(board, board.length);
					makeMove(move);
					boolean lost = isCheck(getPlayer());
					board = Arrays.copyOf(temp, temp.length);
					if (!lost)
						return false;
				}
			}
		}
		return true;
	}

	// search for Checks
	private boolean isCheck(Color player) {
		boolean ilegal = false;
		int[] move = getKingPosition(player);
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece p = board[x][y];
				if (p == null)
					continue;
				if (!p.getPlayer().equals(player)) {
					move[0] = x;
					move[1] = y;
					if (canMakeMovement(move, getOponent(player)))
						ilegal = true;
				}
			}
		}
		return ilegal;
	}

	private Color getOponent(Color player) {
		if (player.equals(Color.WHITE))
			return Color.BLACK;
		return Color.WHITE;
	}

	// actually moves the piece on the board
	private boolean makeMove(int[] position) {
		if (board[position[2]][position[3]] != null)
			death.add(board[position[2]][position[3]]);
		board[position[0]][position[1]].move();
		Piece t = board[position[0]][position[1]];
		if (isPromotion(t, position))
			t = getPromotedPiece(position[4]);
		board[position[2]][position[3]] = t;
		board[position[0]][position[1]] = null;
		return true;
	}

	private Piece getPromotedPiece(int piece) {
		switch (piece) {
		case 'Q':
			return new Queen(player);
		case 'R':
			return new Rook(player);
		case 'B':
			return new Bishop(player);
		case 'K':
			return new Knight(player);
		default:
			return new Queen(player);
		}
	}

	private boolean isPromotion(Piece p, int[] position) {
		if (!(p instanceof Pawn))
			return false;
		if (player.equals(Color.WHITE) && position[3] == 7)
			return true;
		if (player.equals(Color.BLACK) && position[3] == 0)
			return true;
		return false;
	}

	public GameStatus getStatus() {
		return status;
	}

	private void setStatus(GameStatus status) {
		this.status = status;
	}

	public Color getWinner() {
		return winner;
	}

	public void setWinner(Color winner) {
		this.winner = winner;
	}

	public String getMovements() {
		String moves = "";
		for (String move : movements) {
			moves += move + "\n";
		}
		return moves;
	}

	public void start() {
		status = GameStatus.ONGOING;
		player = Color.WHITE;
	}

	public void end() {
		status = GameStatus.END;
	}
}
