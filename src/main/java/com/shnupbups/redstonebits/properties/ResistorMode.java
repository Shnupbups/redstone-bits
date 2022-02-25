package com.shnupbups.redstonebits.properties;

import net.minecraft.util.StringIdentifiable;

public enum ResistorMode implements StringIdentifiable {
	ONE_POINT_FIVE(1.5f),
	HALVE(2),
	THIRD(3);

	public final float divisor;

	ResistorMode(float divisor) {
		this.divisor = divisor;
	}

	public float getDivisor() {
		return divisor;
	}

	public int resistPower(int power) {
		float f = (float) power;
		float f2 = f / divisor;
		return Math.round(f2);
	}

	@Override
	public String asString() {
		return toString().toLowerCase();
	}
}