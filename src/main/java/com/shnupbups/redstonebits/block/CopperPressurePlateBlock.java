package com.shnupbups.redstonebits.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class CopperPressurePlateBlock extends ModWeightedPressurePlateBlock implements Oxidizable {
	private final Oxidizable.OxidationLevel oxidationLevel;

	public CopperPressurePlateBlock(Oxidizable.OxidationLevel oxidationLevel, int weight, Settings settings) {
		super(weight, settings);
		this.oxidationLevel = oxidationLevel;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	@Override
	public OxidationLevel getDegradationLevel() {
		return this.oxidationLevel;
	}
}
