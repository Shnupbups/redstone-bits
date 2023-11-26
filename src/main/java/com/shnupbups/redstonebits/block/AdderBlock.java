package com.shnupbups.redstonebits.block;

import com.mojang.serialization.MapCodec;

public class AdderBlock extends AdderOrCounterBlock {
	public static final MapCodec<AdderBlock> CODEC = createCodec(AdderBlock::new);

	public AdderBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends AdderBlock> getCodec() {
		return CODEC;
	}

	@Override
	public int getPowerChange(int receivedPower) {
		return receivedPower;
	}
}
