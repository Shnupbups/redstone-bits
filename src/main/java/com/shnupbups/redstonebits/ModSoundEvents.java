package com.shnupbups.redstonebits;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_ON = new SoundEvent(RedstoneBits.id("block.copper_button.click_on"));
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_OFF = new SoundEvent(RedstoneBits.id("block.copper_button.click_off"));
	public static final SoundEvent BLOCK_COUNTER_CLICK = new SoundEvent(RedstoneBits.id("block.counter.click"));
	public static final SoundEvent BLOCK_RESISTOR_CLICK = new SoundEvent(RedstoneBits.id("block.resistor.click"));
	public static final SoundEvent BLOCK_ADDER_CLICK = new SoundEvent(RedstoneBits.id("block.adder.click"));

	public static SoundEvent register(SoundEvent event) {
		Identifier identifier = event.getId();
		return Registry.register(Registry.SOUND_EVENT, identifier, event);
	}

	public static void init() {
		register(BLOCK_COPPER_BUTTON_CLICK_ON);
		register(BLOCK_COPPER_BUTTON_CLICK_OFF);
		register(BLOCK_COUNTER_CLICK);
		register(BLOCK_RESISTOR_CLICK);
		register(BLOCK_ADDER_CLICK);
	}
}
