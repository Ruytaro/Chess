package net.ruytaro.chess.pieces;

import net.ruytaro.chess.Color;

public class Knight extends Piece {

	public Knight(Color player) {
		super(player);
		this.rep = "n";
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
		int aux = (10 + 1) * 100 + (10 + 2);
		movements.put(aux, true);
		aux = (10 + 2) * 100 + (10 + 1);
		movements.put(aux, true);
		aux = (10 - 1) * 100 + (10 + 2);
		movements.put(aux, true);
		aux = (10 - 2) * 100 + (10 + 1);
		movements.put(aux, true);
		aux = (10 - 1) * 100 + (10 - 2);
		movements.put(aux, true);
		aux = (10 - 2) * 100 + (10 - 1);
		movements.put(aux, true);
		aux = (10 + 1) * 100 + (10 - 2);
		movements.put(aux, true);
		aux = (10 + 2) * 100 + (10 - 1);
		movements.put(aux, true);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
