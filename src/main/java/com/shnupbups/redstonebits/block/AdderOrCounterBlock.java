package com.shnupbups.redstonebits.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.tick.ScheduledTickView;
import net.minecraft.world.tick.TickPriority;

import com.shnupbups.redstonebits.init.RBSoundEvents;
import com.shnupbups.redstonebits.properties.RBProperties;

public abstract class AdderOrCounterBlock extends AbstractRedstoneBitsGateBlock {
	public static final IntProperty POWER = Properties.POWER;
	public static final BooleanProperty BACKWARDS = RBProperties.BACKWARDS;
	public static final BooleanProperty LOCKED = Properties.LOCKED;

	public AdderOrCounterBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(BACKWARDS, false).with(POWER, 0).with(LOCKED, false));
	}

	@Override
	protected abstract MapCodec<? extends AdderOrCounterBlock> getCodec();

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
		super.appendProperties(builder);
		builder.add(POWER, BACKWARDS);
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
				if (newPower > 15) newPower -= 16;
				else if (newPower < 0) newPower += 16;
				world.setBlockState(pos, state.with(POWERED, true).with(POWER, newPower), Block.NOTIFY_LISTENERS);
				if (!hasPower) {
					world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.HIGH);
				}
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!player.getAbilities().allowModifyWorld) {
			return ActionResult.PASS;
		} else {
			boolean backwards = state.get(BACKWARDS);
			float pitch = backwards ? 0.55F : 0.5F;
			world.playSound(player, pos, RBSoundEvents.BLOCK_ADDER_CLICK, SoundCategory.BLOCKS, 0.3F, pitch);
			world.setBlockState(pos, state.with(BACKWARDS, !backwards), Block.NOTIFY_ALL);
			return ActionResult.SUCCESS;
		}
	}

    public abstract int getPowerChange(int receivedPower);
}
