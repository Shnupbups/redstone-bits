package com.shnupbups.redstonebits.block;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModButtonBlock extends ButtonBlock {

	public ModButtonBlock(Settings settings, BlockSetType blockSetType, int pressTicks, boolean wooden) {
		super(settings, blockSetType, pressTicks, wooden);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		super.onStateReplaced(state, world, pos, newState, moved);
		if (!moved && !state.isOf(newState.getBlock())) {
			if (newState.isIn(BlockTags.BUTTONS) && state.get(POWERED) && newState.get(POWERED)) {
				world.setBlockState(pos, newState.with(POWERED, false));
			}
		}
	}
}
