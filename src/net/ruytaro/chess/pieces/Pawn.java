package net.ruytaro.chess.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruytaro.chess.Color;

public class Pawn extends Piece {

	public Pawn(Color player) {
		super(player);
		this.rep = "p";
	}

	@Override
	public boolean canMakeMove(int[] dest, boolean eats) {
		int aux = (10 + dest[0]) * 100 + (10 + dest[1]);
		Boolean temp = movements.get(aux);
		if (temp == null)
			return false;
		return temp == eats;
	}

	@Override
	public void setMovements(Color p) {
		int[] mv1= {0,1};
		int[] mv2= {0,2};
		int[] e1= {1,1};
		int[] e2= {-1,1};
		if (player.equals(Color.BLACK)) {
			mv1[1]=-1;
			mv2[1]=-2;
			e1[1]=-1;
			e2[1]=-1;
		}
		movements.put((10 + mv1[0]) * 100 + (10 + mv1[1]), false);
		movements.put((10 + mv2[0]) * 100 + (10 + mv2[1]), false);
		movements.put((10 + e1[0]) * 100 + (10 + e1[1]), true);
		movements.put((10 + e2[0]) * 100 + (10 + e2[1]), true);
	}

	@Override
	public void move() {
		int[] mv = { 0, 2 };
		if (player.equals(Color.BLACK))
			mv[1] = -2;
		int aux = (10 + mv[0]) * 100 + (10 + mv[1]);
		movements.remove(aux);
	}

}
