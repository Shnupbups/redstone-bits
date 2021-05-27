package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.ModSoundEvents;

public class ModButtonBlock extends AbstractButtonBlock {
	private final int pressTicks;

	public ModButtonBlock(int pressTicks, AbstractBlock.Settings settings) {
		super(false, settings);
		this.pressTicks = pressTicks;
	}

	@Override
	protected SoundEvent getClickSound(boolean powered) {
		return powered ? ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON : ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF;
	}

	@Override
	public int getPressTicks() {
		return pressTicks;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		super.onStateReplaced(state, world, pos, newState, moved);
		if (!moved && !state.isOf(newState.getBlock())) {
			if(newState.isIn(BlockTags.BUTTONS) && state.get(POWERED) && newState.get(POWERED)) {
				world.setBlockState(pos, newState.with(POWERED, false));
			}
		}
	}
}
