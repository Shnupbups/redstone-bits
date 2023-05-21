package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.TickPriority;

import com.shnupbups.redstonebits.init.RBSoundEvents;
import com.shnupbups.redstonebits.properties.RBProperties;

public abstract class AdderOrCounterBlock extends AbstractRedstoneGateBlock implements AdvancedRedstoneConnector {
	public static final IntProperty POWER = Properties.POWER;
	public static final BooleanProperty BACKWARDS = RBProperties.BACKWARDS;
	public static final BooleanProperty LOCKED = Properties.LOCKED;

	public AdderOrCounterBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(BACKWARDS, false).with(POWER, 0).with(LOCKED, false));
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
	public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
		return state.get(FACING) == facing ? this.getOutputLevel(view, pos, state) : 0;
	}

	@Override
	protected int getUpdateDelayInternal(BlockState state) {
		return 2;
	}

	@Override
	protected int getOutputLevel(BlockView view, BlockPos pos, BlockState state) {
		return state.get(POWER);
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWER, POWERED, BACKWARDS, LOCKED);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!this.isLocked(world, pos, state)) {
			boolean powered = state.get(POWERED);
			boolean backwards = state.get(BACKWARDS);
			int power = state.get(POWER);
			boolean hasPower = this.hasPower(world, pos, state);
			if (powered && !hasPower) {
				world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
			} else if (!powered) {
				int newPower = power;
				int receivedPower = this.getPower(world, pos, state);
				int powerChange = getPowerChange(receivedPower);
				if (backwards) powerChange = -powerChange;
				newPower += powerChange;
				if (newPower > 15) newPower -= 15;
				else if (newPower < 0) newPower += 15;
				world.setBlockState(pos, state.with(POWERED, true).with(POWER, newPower), Block.NOTIFY_LISTENERS);
				if (!hasPower) {
					world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.HIGH);
				}
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.getAbilities().allowModifyWorld) {
			return ActionResult.PASS;
		} else {
			boolean backwards = state.get(BACKWARDS);
			float pitch = backwards ? 0.55F : 0.5F;
			world.playSound(player, pos, RBSoundEvents.BLOCK_ADDER_CLICK, SoundCategory.BLOCKS, 0.3F, pitch);
			world.setBlockState(pos, state.with(BACKWARDS, !backwards), Block.NOTIFY_ALL);
			return ActionResult.success(world.isClient());
		}
	}

	@Override
	public boolean connectsToRedstoneInDirection(BlockState state, Direction direction) {
		if (direction != null) {
			Direction facing = state.get(FACING);
			return direction == facing || direction.getOpposite() == facing;
		}
		return false;
	}

	@Override
	public boolean isLocked(WorldView world, BlockPos pos, BlockState state) {
		return this.getMaxInputLevelSides(world, pos, state) > 0;
	}

	@Override
	public boolean getSideInputFromGatesOnly() {
		return true;
	}

	public abstract int getPowerChange(int receivedPower);
}
