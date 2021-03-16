package com.shnupbups.redstonebits;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_ON = register("block.copper_button.click_on");
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_OFF = register("block.copper_button.click_off");
	public static final SoundEvent BLOCK_COUNTER_CLICK = register("block.counter.click");
	public static final SoundEvent BLOCK_RESISTOR_CLICK = register("block.resistor.click");

	public static SoundEvent register(String id) {
		Identifier identifier = RedstoneBits.id(id);
		return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
	}
}
