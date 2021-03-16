package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.sound.SoundEvent;

import com.shnupbups.redstonebits.ModSoundEvents;

public class ModButtonBlock extends AbstractButtonBlock {
	private final int pressTicks;

	public ModButtonBlock(AbstractBlock.Settings settings, int pressTicks) {
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
}
