package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.properties.ModProperties;
import com.shnupbups.redstonebits.properties.ResistorMode;

public class ResistorBlock extends AbstractRedstoneGateBlock implements AdvancedRedstoneConnector {
	public static final EnumProperty<ResistorMode> MODE = ModProperties.RESISTOR_MODE;
	
	public ResistorBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(MODE, ResistorMode.HALVE));
	}
	
	@Override
	protected int getOutputLevel(BlockView view, BlockPos pos, BlockState state) {
		if(view instanceof World) {
			return state.get(MODE).resistPower(getPower((World)view, pos, state));
		}
		return 0;
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
			world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
			world.setBlockState(pos, state, 2);
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
