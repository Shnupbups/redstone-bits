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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;

import java.util.Random;

public class CheckerBlock extends BlockWithEntity {
	public static final DirectionProperty FACING;
	public static final BooleanProperty POWERED;
	
	static {
		FACING = Properties.FACING;
		POWERED = Properties.POWERED;
	}
	
	public CheckerBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.SOUTH).with(POWERED, false));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation blockRotation_1) {
		return state.with(FACING, blockRotation_1.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, BlockMirror blockMirror_1) {
		return state.rotate(blockMirror_1.getRotation(state.get(FACING)));
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
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState state2, WorldAccess iworld, BlockPos pos, BlockPos pos2) {
		if (state.get(FACING) == direction && !state.get(POWERED)) {
			this.scheduleTick(iworld, pos);
		}
		
		return super.getStateForNeighborUpdate(state, direction, state2, iworld, pos, pos2);
	}
	
	private void scheduleTick(WorldAccess iworld, BlockPos pos) {
		if (!iworld.isClient() && !iworld.getBlockTickScheduler().isScheduled(pos, this)) {
			iworld.getBlockTickScheduler().schedule(pos, this, 2);
		}
	}
	
	protected void updateNeighbors(World world, BlockPos pos, BlockState state) {
		Direction direction = state.get(FACING);
		BlockPos pos2 = pos.offset(direction.getOpposite());
		world.updateNeighbor(pos2, this, pos);
		world.updateNeighborsExcept(pos2, this, direction);
	}
	
	@Override
	public boolean emitsRedstonePower(BlockState state) {
		return true;
	}
	
	@Override
	public int getStrongRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction direction) {
		return state.getWeakRedstonePower(view, pos, direction);
	}
	
	@Override
	public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction direction) {
		return state.get(POWERED) && state.get(FACING) == direction ? 15 : 0;
	}
	
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState state2, boolean bool) {
		if (state.getBlock() != state2.getBlock()) {
			if (!world.isClient() && state.get(POWERED) && !world.getBlockTickScheduler().isScheduled(pos, this)) {
				BlockState blockState_3 = state.with(POWERED, false);
				world.setBlockState(pos, blockState_3, 18);
				this.updateNeighbors(world, pos, blockState_3);
			}
		}
		super.onBlockAdded(state, world, pos, state2, bool);
	}
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState state2, boolean bool) {
		if (state.getBlock() != state2.getBlock()) {
			if (!world.isClient && state.get(POWERED) && world.getBlockTickScheduler().isScheduled(pos, this)) {
				this.updateNeighbors(world, pos, state.with(POWERED, false));
			}
		}
		super.onStateReplaced(state, world, pos, state2, bool);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return new CheckerBlockEntity();
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity_1 = world.getBlockEntity(pos);
			if (blockEntity_1 instanceof CheckerBlockEntity) {
				player.openHandledScreen((CheckerBlockEntity) blockEntity_1);
			}
		}
		return ActionResult.SUCCESS;
	}
}
