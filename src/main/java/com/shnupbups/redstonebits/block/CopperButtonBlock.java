package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.ModSoundEvents;

import java.util.Random;

public class CopperButtonBlock extends ModButtonBlock implements Oxidizable {
	private final Oxidizable.OxidizationLevel oxidizationLevel;
	private final Block nextStage;

	public CopperButtonBlock(AbstractBlock.Settings settings, int pressTicks) {
		super(settings, pressTicks);
		this.oxidizationLevel = Oxidizable.OxidizationLevel.values()[Oxidizable.OxidizationLevel.values().length - 1];
		this.nextStage = this;
	}

	public CopperButtonBlock(AbstractBlock.Settings settings, Oxidizable.OxidizationLevel oxidizationLevel, Block nextStage, int pressTicks) {
		super(settings, pressTicks);
		this.oxidizationLevel = oxidizationLevel;
		this.nextStage = nextStage;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return this.nextStage != this;
	}

	@Override
	public Oxidizable.OxidizationLevel getDegradationLevel() {
		return this.oxidizationLevel;
	}

	@Override
	public BlockState getDegradationResult(BlockState state) {
		return this.nextStage.getDefaultState();
	}
}
