package com.shnupbups.redstonebits;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class CheckerBlock extends BlockWithEntity {
	public static final DirectionProperty FACING;
	public static final BooleanProperty POWERED;

	public CheckerBlock(Block.Settings block$Settings_1) {
		super(block$Settings_1);
		this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateFactory.getDefaultState()).with(FACING, Direction.SOUTH)).with(POWERED, false));
	}

	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.with(FACING, POWERED);
	}

	public BlockRenderType getRenderType(BlockState blockState_1) {
		return BlockRenderType.MODEL;
	}

	public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
		return (BlockState)blockState_1.with(FACING, blockRotation_1.rotate((Direction)blockState_1.get(FACING)));
	}

	public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
		return blockState_1.rotate(blockMirror_1.getRotation((Direction)blockState_1.get(FACING)));
	}

	public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		if ((Boolean)blockState_1.get(POWERED) && !((CheckerBlockEntity)world_1.getBlockEntity(blockPos_1)).matches()) {
			world_1.setBlockState(blockPos_1, (BlockState)blockState_1.with(POWERED, false), 2);
		} else if(!(Boolean)blockState_1.get(POWERED) && ((CheckerBlockEntity)world_1.getBlockEntity(blockPos_1)).matches()) {
			world_1.setBlockState(blockPos_1, (BlockState)blockState_1.with(POWERED, true), 2);
		}

		this.updateNeighbors(world_1, blockPos_1, blockState_1);
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(FACING) == direction_1 && !(Boolean)blockState_1.get(POWERED)) {
			this.scheduleTick(iWorld_1, blockPos_1);
		}

		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
	}

	private void scheduleTick(IWorld iWorld_1, BlockPos blockPos_1) {
		if (!iWorld_1.isClient() && !iWorld_1.getBlockTickScheduler().isScheduled(blockPos_1, this)) {
			iWorld_1.getBlockTickScheduler().schedule(blockPos_1, this, 2);
		}

	}

	protected void updateNeighbors(World world_1, BlockPos blockPos_1, BlockState blockState_1) {
		Direction direction_1 = (Direction)blockState_1.get(FACING);
		BlockPos blockPos_2 = blockPos_1.offset(direction_1.getOpposite());
		world_1.updateNeighbor(blockPos_2, this, blockPos_1);
		world_1.updateNeighborsExcept(blockPos_2, this, direction_1);
	}

	public boolean emitsRedstonePower(BlockState blockState_1) {
		return true;
	}

	public int getStrongRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return blockState_1.getWeakRedstonePower(blockView_1, blockPos_1, direction_1);
	}

	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return (Boolean)blockState_1.get(POWERED) && blockState_1.get(FACING) == direction_1 ? 15 : 0;
	}

	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			if (!world_1.isClient() && (Boolean)blockState_1.get(POWERED) && !world_1.getBlockTickScheduler().isScheduled(blockPos_1, this)) {
				BlockState blockState_3 = (BlockState)blockState_1.with(POWERED, false);
				world_1.setBlockState(blockPos_1, blockState_3, 18);
				this.updateNeighbors(world_1, blockPos_1, blockState_3);
			}

		}
	}

	public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			if (!world_1.isClient && (Boolean)blockState_1.get(POWERED) && world_1.getBlockTickScheduler().isScheduled(blockPos_1, this)) {
				this.updateNeighbors(world_1, blockPos_1, (BlockState)blockState_1.with(POWERED, false));
			}

		}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		return (BlockState)this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerFacing().getOpposite().getOpposite());
	}

	static {
		FACING = Properties.FACING;
		POWERED = Properties.POWERED;
	}

	public BlockEntity createBlockEntity(BlockView blockView_1) {
		return new CheckerBlockEntity();
	}

	public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		if (world_1.isClient) {
			return true;
		} else {
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof CheckerBlockEntity) {
				playerEntity_1.openContainer((CheckerBlockEntity)blockEntity_1);
			}
			return true;
		}
	}
}
