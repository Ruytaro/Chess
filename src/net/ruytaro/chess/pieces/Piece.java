package net.ruytaro.chess.pieces;

import net.ruytaro.chess.Color;

public abstract class Piece {
	Color player;
	String rep;

	public Piece(Color player) {
		this.player = player;
	}

	public Color getPlayer() {
		return player;
	}

	public int[] getMovements() {
		return null;
	}

	@Override
	public String toString() {
		if (player.equals(Color.WHITE))
			return rep.toUpperCase();
		return rep.toLowerCase();
	}
}
