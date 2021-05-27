package com.shnupbups.redstonebits.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class CopperPressurePlateBlock extends ModWeightedPressurePlateBlock implements Oxidizable {
	private final Oxidizable.OxidizationLevel oxidizationLevel;

	public CopperPressurePlateBlock(Oxidizable.OxidizationLevel oxidizationLevel, int weight, Settings settings) {
		super(weight, settings);
		this.oxidizationLevel = oxidizationLevel;
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
	public OxidizationLevel getDegradationLevel() {
		return this.oxidizationLevel;
	}
}
