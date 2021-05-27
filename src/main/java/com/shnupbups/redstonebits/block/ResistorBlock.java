package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ComparatorBlockEntity;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.ModSoundEvents;
import com.shnupbups.redstonebits.blockentity.ResistorBlockEntity;
import com.shnupbups.redstonebits.properties.ModProperties;
import com.shnupbups.redstonebits.properties.ResistorMode;

import java.util.Random;

public class ResistorBlock extends AbstractRedstoneGateBlock implements AdvancedRedstoneConnector, BlockEntityProvider {
	public static final EnumProperty<ResistorMode> MODE = ModProperties.RESISTOR_MODE;
	
	public ResistorBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(MODE, ResistorMode.HALVE));
	}
	
	@Override
	protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof ResistorBlockEntity ? ((ResistorBlockEntity)blockEntity).getOutputSignal() : 0;
	}
	
	@Override
	protected int getUpdateDelayInternal(BlockState state) {
		return 2;
	}
	
	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, MODE, POWERED);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.getAbilities().allowModifyWorld) {
			return ActionResult.PASS;
		} else {
			state = state.cycle(MODE);
			float f = state.get(MODE).getDivisor();
			world.playSound(player, pos, ModSoundEvents.BLOCK_RESISTOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
			world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
			this.update(world, pos, state);
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

	@Override
	protected void updatePowered(World world, BlockPos pos, BlockState state) {
		if (!world.getBlockTickScheduler().isTicking(pos, this)) {
			TickPriority tickPriority = this.isTargetNotAligned(world, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
			world.getBlockTickScheduler().schedule(pos, this, 2, tickPriority);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ResistorBlockEntity(pos, state);
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
		int i = this.calculateOutputSignal(world, pos, state);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		int j = 0;
		if (blockEntity instanceof ResistorBlockEntity resistorBlockEntity) {
			j = resistorBlockEntity.getOutputSignal();
			resistorBlockEntity.setOutputSignal(i);
		}

		if (j != i) {
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

	public int calculateOutputSignal(World world, BlockPos pos, BlockState state) {
		return state.get(MODE).resistPower(getPower(world, pos, state));
	}
}
