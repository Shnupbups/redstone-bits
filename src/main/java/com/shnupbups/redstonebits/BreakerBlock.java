package com.shnupbups.redstonebits;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BreakerBlock extends BlockWithEntity {
	public static final DirectionProperty FACING;
	public static final BooleanProperty TRIGGERED;
	public static final BooleanProperty BREAKING;

	public BreakerBlock(Block.Settings block$Settings_1) {
		super(block$Settings_1);
		this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH).with(TRIGGERED, false).with(BREAKING, false));
	}

	public BlockEntity createBlockEntity(BlockView blockView_1) {
		return new BreakerBlockEntity();
	}

	public int getTickRate(ViewableWorld viewableWorld_1) {
		return 4;
	}

	public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		if (world_1.isClient) {
			return true;
		} else {
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof BreakerBlockEntity) {
				ContainerProviderRegistry.INSTANCE.openContainer(RedstoneBits.BREAKER_CONTAINER, playerEntity_1, buf -> buf.writeBlockPos(blockPos_1));
			}
			return true;
		}
	}

	public void neighborUpdate(BlockState blockState_1, World world_1, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1) {
		boolean boolean_2 = world_1.isReceivingRedstonePower(blockPos_1) || world_1.isReceivingRedstonePower(blockPos_1.up());
		boolean boolean_3 = blockState_1.get(TRIGGERED);
		if (boolean_2 && !boolean_3) {
			world_1.getBlockTickScheduler().schedule(blockPos_1, this, this.getTickRate(world_1));
			world_1.setBlockState(blockPos_1, blockState_1.with(TRIGGERED, true), 4);
		} else if (!boolean_2 && boolean_3) {
			world_1.setBlockState(blockPos_1, blockState_1.with(TRIGGERED, false), 4);
		}
		if(isBreaking(world_1, blockPos_1) && blockPos_2 == getBreakPos(world_1, blockPos_1)) {
			cancelBreak(world_1, blockPos_1);
		}
	}

	public boolean startBreak(World world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		BlockState state = world.getBlockState(pos);
		BlockState breakState = world.getBlockState(pos.add(state.get(FACING).getVector()));
		boolean isBreakable = !(breakState.isAir() || breakState.getHardness(world,pos)<0);
		if(be instanceof BreakerBlockEntity && isBreakable) {
			((BreakerBlockEntity)be).startBreak();
		}
		return isBreakable;
	}

	public void cancelBreak(World world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if(be instanceof BreakerBlockEntity) {
			((BreakerBlockEntity)be).cancelBreak();
		}
	}

	public boolean isBreaking(World world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if(be instanceof BreakerBlockEntity) {
			return ((BreakerBlockEntity)be).isBreaking();
		} return false;
	}

	public BlockPos getBreakPos(World world, BlockPos pos) {
		return pos.add(world.getBlockState(pos).get(Properties.FACING).getVector());
	}

	public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		if(this.isBreaking(world_1, blockPos_1)) this.cancelBreak(world_1, blockPos_1);
		else this.startBreak(world_1, blockPos_1);
		boolean boolean_2 = world_1.isReceivingRedstonePower(blockPos_1) || world_1.isReceivingRedstonePower(blockPos_1.up());
		boolean boolean_3 = blockState_1.get(TRIGGERED);
		if (!boolean_2 && boolean_3) {
			world_1.setBlockState(blockPos_1, blockState_1.with(TRIGGERED, false), 4);
		}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerFacing().getOpposite());
	}

	public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
		if (itemStack_1.hasDisplayName()) {
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof BreakerBlockEntity) {
				((BreakerBlockEntity)blockEntity_1).setCustomName(itemStack_1.getDisplayName());
			}
		}

	}

	public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof BreakerBlockEntity) {
				ItemScatterer.spawn(world_1, blockPos_1, ((BreakerBlockEntity)blockEntity_1));
				world_1.updateHorizontalAdjacent(blockPos_1, this);
			}

			super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		}
	}

	public boolean hasComparatorOutput(BlockState blockState_1) {
		return true;
	}

	public int getComparatorOutput(BlockState blockState_1, World world_1, BlockPos blockPos_1) {
		return Container.calculateComparatorOutput(world_1.getBlockEntity(blockPos_1));
	}

	public BlockRenderType getRenderType(BlockState blockState_1) {
		return BlockRenderType.MODEL;
	}

	public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
		return blockState_1.with(FACING, blockRotation_1.rotate(blockState_1.get(FACING)));
	}

	public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
		return blockState_1.rotate(blockMirror_1.getRotation(blockState_1.get(FACING)));
	}

	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.with(FACING, TRIGGERED, BREAKING);
	}

	static {
		FACING = FacingBlock.FACING;
		TRIGGERED = Properties.TRIGGERED;
		BREAKING = ModProperties.BREAKING;
	}
}
