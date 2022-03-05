package com.shnupbups.redstonebits.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperButtonBlock extends ModButtonBlock implements Oxidizable {
	private final Oxidizable.OxidationLevel oxidationLevel;

	public CopperButtonBlock(Oxidizable.OxidationLevel oxidationLevel, int pressTicks, AbstractBlock.Settings settings) {
		super(pressTicks, settings);
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

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if ((player.getStackInHand(hand).isOf(Items.HONEYCOMB) || player.getStackInHand(hand).getItem() instanceof AxeItem) && !state.get(POWERED))
			return ActionResult.PASS;
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
