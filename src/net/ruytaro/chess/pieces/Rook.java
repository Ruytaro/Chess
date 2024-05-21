package net.ruytaro.chess.pieces;

import net.ruytaro.chess.Color;

public class Rook extends Piece {

	public Rook(Color player) {
		super(player);
		this.rep = "r";
	}

	@Override
	public boolean canMakeMove(int[] dest, boolean eats) {
		int aux = (10 + dest[0]) * 100 + (10 + dest[1]);
		Boolean temp = movements.get(aux);
		if (temp == null)
			return false;
		return true;
	}

	@Override
	public void setMovements(Color p) {
		for (int i = 1; i < 8; i++) {
			int aux = (10 + i) * 100 + 10;
			movements.put(aux, true);
			aux = (10 - i) * 100 + 10;
			movements.put(aux, true);
			aux = 1010 + i;
			movements.put(aux, true);
			aux = 1010 - i;
			movements.put(aux, true);
		}
	}

	@Override
	public void move() {
		moved = true;
	}

}
