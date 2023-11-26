package com.shnupbups.redstonebits.block;

import com.mojang.serialization.MapCodec;

public class CounterBlock extends AdderOrCounterBlock {
	public static final MapCodec<CounterBlock> CODEC = createCodec(CounterBlock::new);

	public CounterBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends CounterBlock> getCodec() {
		return CODEC;
	}

	@Override
	public int getPowerChange(int receivedPower) {
		return 1;
	}
}
