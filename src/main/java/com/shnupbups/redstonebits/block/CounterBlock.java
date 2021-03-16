package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.properties.ModProperties;

import java.util.Random;

public class CounterBlock extends AbstractRedstoneGateBlock implements AdvancedRedstoneConnector {
	public static final IntProperty POWER = Properties.POWER;
	public static final BooleanProperty BACKWARDS = ModProperties.BACKWARDS;
	
	public CounterBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(BACKWARDS, false).with(POWER, 0));
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
		builder.add(FACING, POWER, POWERED, BACKWARDS);
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!this.isLocked(world, pos, state)) {
			boolean bl = state.get(POWERED);
			boolean backwards = state.get(BACKWARDS);
			int c = state.get(POWER);
			boolean bl2 = this.hasPower(world, pos, state);
			if (bl && !bl2) {
				world.setBlockState(pos, state.with(POWERED, false), 2);
			} else if (!bl) {
				int nc = backwards ? c-1<0?15:c-1 : c+1>15?0:c+1;
				world.setBlockState(pos, state.with(POWERED, true).with(POWER, nc), 2);
				if (!bl2) {
					world.getBlockTickScheduler().schedule(pos, this, this.getUpdateDelayInternal(state), TickPriority.HIGH);
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
			float f = backwards ? 0.55F : 0.5F;
			world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
			world.setBlockState(pos, state.with(BACKWARDS, !backwards), 2);
			return ActionResult.SUCCESS;
		}
	}
	
	@Override
	public boolean connectsToRedstoneInDirection(BlockState state, Direction direction) {
		if(direction != null) {
			Direction facing = state.get(FACING);
			return direction == facing || direction.getOpposite() == facing;
		}
		return true;
	}
}
