package com.shnupbups.redstonebits.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class AnalogRedstoneReceiverBlock extends Block {
	public static final IntProperty POWER = Properties.POWER;

	public AnalogRedstoneReceiverBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWER, 0));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(POWER, ctx.getWorld().getReceivedRedstonePower(ctx.getBlockPos()));
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClient) {
			int power = state.get(POWER);
			int receivedPower = world.getReceivedRedstonePower(pos);
			if (power != receivedPower) {
				if (power > 0 && receivedPower == 0) {
					world.createAndScheduleBlockTick(pos, this, 4);
				} else {
					world.setBlockState(pos, state.with(POWER, world.getReceivedRedstonePower(pos)), 2);
				}
			}

		}
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(POWER) > 0 && !world.isReceivingRedstonePower(pos)) {
			world.setBlockState(pos, state.with(POWER, 0), Block.NOTIFY_LISTENERS);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}
}
