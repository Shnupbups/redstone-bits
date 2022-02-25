package com.shnupbups.redstonebits.block;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

import com.shnupbups.redstonebits.init.ModBlockEntities;
import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;
import com.shnupbups.redstonebits.container.CheckerScreenHandler;

public class CheckerBlock extends BlockWithEntity implements AdvancedRedstoneConnector {
	public static final DirectionProperty FACING = Properties.FACING;
	public static final IntProperty POWER = Properties.POWER;

	public CheckerBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.SOUTH).with(POWER, 0));
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockEntities.CHECKER, world.isClient ? null : CheckerBlockEntity::serverTick);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWER);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof CheckerBlockEntity) {
			int matches = ((CheckerBlockEntity) be).matches();
			if (state.get(POWER) != matches) {
				world.setBlockState(pos, state.with(POWER, matches), Block.NOTIFY_LISTENERS + Block.NO_REDRAW);
			}
		}
		this.updateNeighbors(world, pos, state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos pos2) {
		if (state.get(FACING) == direction) {
			this.scheduleTick(world, pos);
		}

		return super.getStateForNeighborUpdate(state, direction, newState, world, pos, pos2);
	}

	private void scheduleTick(WorldAccess world, BlockPos pos) {
		if (!world.isClient() && !world.getBlockTickScheduler().isQueued(pos, this)) {
			world.createAndScheduleBlockTick(pos, this, 2);
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
	public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.getWeakRedstonePower(world, pos, direction);
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.get(FACING) == direction ? state.get(POWER) : 0;
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!state.isOf(oldState.getBlock())) {
			if (!world.isClient() && state.get(POWER) > 0 && !world.getBlockTickScheduler().isQueued(pos, this)) {
				BlockState newState = state.with(POWER, 0);
				world.setBlockState(pos, newState, Block.NOTIFY_LISTENERS + Block.NO_REDRAW + Block.FORCE_STATE);
				this.updateNeighbors(world, pos, newState);
			}
		}
		super.onBlockAdded(state, world, pos, oldState, notify);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!state.isOf(oldState.getBlock())) {
			if (!world.isClient && state.get(POWER) > 0 && world.getBlockTickScheduler().isQueued(pos, this)) {
				this.updateNeighbors(world, pos, state.with(POWER, 0));
			}
		}
		super.onStateReplaced(state, world, pos, oldState, notify);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CheckerBlockEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CheckerBlockEntity) {
				player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public boolean connectsToRedstoneInDirection(BlockState state, Direction direction) {
		if (direction != null) {
			Direction facing = state.get(FACING);
			return direction == facing;
		}
		return false;
	}

	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new ExtendedScreenHandlerFactory() {
			@Override
			public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
				buf.writeBlockPos(pos);
			}

			@Override
			public Text getDisplayName() {
				return new TranslatableText(getTranslationKey());
			}

			@Override
			public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
				if (world.getBlockEntity(pos) instanceof CheckerBlockEntity blockEntity) {
					return new CheckerScreenHandler(syncId, playerInventory, blockEntity);
				} else return new CheckerScreenHandler(syncId, playerInventory);
			}
		};
	}
}
