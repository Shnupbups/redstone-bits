package com.shnupbups.redstonebits.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class MediumWeightedPressurePlateBlock extends WeightedPressurePlateBlock implements Oxidizable {
	private final Oxidizable.OxidationLevel oxidationLevel;

	public MediumWeightedPressurePlateBlock(Oxidizable.OxidationLevel oxidationLevel, int weight, Settings settings, SoundEvent depressSound, SoundEvent pressSound) {
		super(weight, settings, depressSound, pressSound);
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
