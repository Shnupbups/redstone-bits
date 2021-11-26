package com.shnupbups.redstonebits.block;

import java.util.Random;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import com.shnupbups.redstonebits.blockentity.RedstoneGateBlockEntity;

public class InverterBlock extends AbstractRedstoneGateBlock implements AdvancedRedstoneConnector, BlockEntityProvider {
	public static final BooleanProperty LOCKED = Properties.LOCKED;

	public InverterBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(LOCKED, false));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = super.getPlacementState(ctx);
		return state.with(LOCKED, this.isLocked(ctx.getWorld(), ctx.getBlockPos(), state));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!world.isClient() && direction.getAxis() != state.get(FACING).getAxis()) {
			return state.with(LOCKED, this.isLocked(world, pos, state));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof RedstoneGateBlockEntity ? ((RedstoneGateBlockEntity) blockEntity).getOutputSignal() : 0;
	}

	@Override
	protected int getUpdateDelayInternal(BlockState state) {
		return 2;
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, LOCKED);
	}

	@Override
	public boolean connectsToRedstoneInDirection(BlockState state, Direction direction) {
		if (direction != null) {
			Direction facing = state.get(FACING);
			return direction == facing || direction.getOpposite() == facing;
		}
		return true;
	}

	@Override
	protected void updatePowered(World world, BlockPos pos, BlockState state) {
		if (!world.getBlockTickScheduler().isTicking(pos, this)) {
			TickPriority tickPriority = this.isTargetNotAligned(world, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
			world.createAndScheduleBlockTick(pos, this, 2, tickPriority);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new RedstoneGateBlockEntity(pos, state);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.update(world, pos, state);
	}

	@Override
	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
	}

	private void update(World world, BlockPos pos, BlockState state) {
		if (!isLocked(world, pos, state)) {
			int output = this.calculateOutputSignal(world, pos, state);
			BlockEntity blockEntity = world.getBlockEntity(pos);
			int currentOutput = 0;
			if (blockEntity instanceof RedstoneGateBlockEntity resistorBlockEntity) {
				currentOutput = resistorBlockEntity.getOutputSignal();
				resistorBlockEntity.setOutputSignal(output);
			}

			if (currentOutput != output) {
				boolean bl = this.hasPower(world, pos, state);
				boolean bl2 = state.get(POWERED);
				if (bl2 && !bl) {
					world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
				} else if (!bl2 && bl) {
					world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_LISTENERS);
				}

				this.updateTarget(world, pos, state);
			}
		}
	}

	public int calculateOutputSignal(World world, BlockPos pos, BlockState state) {
		return 15 - getPower(world, pos, state);
	}

	@Override
	public boolean isLocked(WorldView world, BlockPos pos, BlockState state) {
		return this.getMaxInputLevelSides(world, pos, state) > 0;
	}

	@Override
	protected boolean isValidInput(BlockState state) {
		return AbstractRedstoneGateBlock.isRedstoneGate(state);
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		if (state.get(FACING) == direction) {
			return this.getOutputLevel(world, pos, state);
		}
		return 0;
	}
}
