package com.shnupbups.redstonebits;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final Identifier BLOCK_COPPER_BUTTON_CLICK_ON_ID = RedstoneBits.id("block.copper_button.click_on");
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_ON = Registry.register(Registry.SOUND_EVENT, BLOCK_COPPER_BUTTON_CLICK_ON_ID, new SoundEvent(BLOCK_COPPER_BUTTON_CLICK_ON_ID));
	public static final Identifier BLOCK_COPPER_BUTTON_CLICK_OFF_ID = RedstoneBits.id("block.copper_button.click_off");
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_OFF = Registry.register(Registry.SOUND_EVENT, BLOCK_COPPER_BUTTON_CLICK_OFF_ID, new SoundEvent(BLOCK_COPPER_BUTTON_CLICK_OFF_ID));
}
