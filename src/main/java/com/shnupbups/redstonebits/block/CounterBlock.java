package com.shnupbups.redstonebits.block;

public class CounterBlock extends AdderOrCounterBlock {
	public CounterBlock(Settings settings) {
		super(settings);
	}

	@Override
	public int getPowerChange(int receivedPower) {
		return 1;
	}
}
