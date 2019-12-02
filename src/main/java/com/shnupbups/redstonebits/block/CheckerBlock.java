package com.shnupbups.redstonebits.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;

import java.util.Random;

public class CheckerBlock extends BlockWithEntity {
	public static final DirectionProperty FACING;
	public static final BooleanProperty POWERED;
	
	static {
		FACING = Properties.FACING;
		POWERED = Properties.POWERED;
	}
	
	public CheckerBlock(Block.Settings block$Settings_1) {
		super(block$Settings_1);
		this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.SOUTH).with(POWERED, false));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(FACING, POWERED);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState blockState_1) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
		return blockState_1.with(FACING, blockRotation_1.rotate(blockState_1.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
		return blockState_1.rotate(blockMirror_1.getRotation(blockState_1.get(FACING)));
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof CheckerBlockEntity) {
			boolean matches = ((CheckerBlockEntity) be).matches();
			if (state.get(POWERED) != matches) {
				world.setBlockState(pos, state.with(POWERED, matches), 2);
			}
		}
		this.updateNeighbors(world, pos, state);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(FACING) == direction_1 && !blockState_1.get(POWERED)) {
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
		Direction direction_1 = blockState_1.get(FACING);
		BlockPos blockPos_2 = blockPos_1.offset(direction_1.getOpposite());
		world_1.updateNeighbor(blockPos_2, this, blockPos_1);
		world_1.updateNeighborsExcept(blockPos_2, this, direction_1);
	}
	
	@Override
	public boolean emitsRedstonePower(BlockState blockState_1) {
		return true;
	}
	
	@Override
	public int getStrongRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return blockState_1.getWeakRedstonePower(blockView_1, blockPos_1, direction_1);
	}
	
	@Override
	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return blockState_1.get(POWERED) && blockState_1.get(FACING) == direction_1 ? 15 : 0;
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			if (!world_1.isClient() && blockState_1.get(POWERED) && !world_1.getBlockTickScheduler().isScheduled(blockPos_1, this)) {
				BlockState blockState_3 = blockState_1.with(POWERED, false);
				world_1.setBlockState(blockPos_1, blockState_3, 18);
				this.updateNeighbors(world_1, blockPos_1, blockState_3);
			}
			
		}
	}
	
	@Override
	public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			if (!world_1.isClient && blockState_1.get(POWERED) && world_1.getBlockTickScheduler().isScheduled(blockPos_1, this)) {
				this.updateNeighbors(world_1, blockPos_1, blockState_1.with(POWERED, false));
			}
			
		}
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerFacing().getOpposite().getOpposite());
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView blockView_1) {
		return new CheckerBlockEntity();
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity_1 = world.getBlockEntity(pos);
			if (blockEntity_1 instanceof CheckerBlockEntity) {
				player.openContainer((CheckerBlockEntity) blockEntity_1);
			}
		}
		return ActionResult.SUCCESS;
	}
}
