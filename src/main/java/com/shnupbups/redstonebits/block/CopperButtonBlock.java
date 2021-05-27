package com.shnupbups.redstonebits.block;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CopperButtonBlock extends ModButtonBlock implements Oxidizable {
	private final Oxidizable.OxidizationLevel oxidizationLevel;

	public CopperButtonBlock(Oxidizable.OxidizationLevel oxidizationLevel, int pressTicks, AbstractBlock.Settings settings) {
		super(pressTicks, settings);
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

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if((player.getStackInHand(hand).isOf(Items.HONEYCOMB) || player.getStackInHand(hand).isIn(FabricToolTags.AXES)) && state.get(POWERED)) return ActionResult.CONSUME;
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
