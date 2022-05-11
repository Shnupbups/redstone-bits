package com.shnupbups.redstonebits.block;

public class AdderBlock extends AdderOrCounterBlock {
	public AdderBlock(Settings settings) {
		super(settings);
	}

	@Override
	public int getPowerChange(int receivedPower) {
		return receivedPower;
	}
}
