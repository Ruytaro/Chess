package net.ruytaro.chess.pieces;

import java.util.HashMap;
import java.util.Map;

import net.ruytaro.chess.Color;

public abstract class Piece {
	protected Color player;
	protected String rep;
	protected boolean moved;
	protected Map<Integer, Boolean> movements = new HashMap<Integer, Boolean>();

	public Piece(Color player) {
		this.player = player;
		setMovements(player);
		moved = false;
	}

	public Color getPlayer() {
		return player;
	}

	public abstract boolean canMakeMove(int[] dest, boolean eats);

	public abstract void setMovements(Color p);

	public abstract void move();

	@Override
	public String toString() {
		if (player.equals(Color.WHITE))
			return rep.toUpperCase();
		return rep.toLowerCase();
	}

}
