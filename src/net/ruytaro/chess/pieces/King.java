package net.ruytaro.chess.pieces;

import net.ruytaro.chess.Color;

public class King extends Piece {

	public King(Color player) {
		super(player);
		this.rep = "k";
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
		movements.put(1011, true);
		movements.put(1009, true);
		movements.put(1111, true);
		movements.put(1110, true);
		movements.put(1109, true);
		movements.put(910, true);
		movements.put(911, true);
		movements.put(909, true);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
