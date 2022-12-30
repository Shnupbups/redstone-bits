package com.shnupbups.redstonebits.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import com.shnupbups.redstonebits.RedstoneBits;

public class RBSoundEvents {
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_ON = SoundEvent.of(RedstoneBits.id("block.copper_button.click_on"));
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_OFF = SoundEvent.of(RedstoneBits.id("block.copper_button.click_off"));
	public static final SoundEvent BLOCK_COUNTER_CLICK = SoundEvent.of(RedstoneBits.id("block.counter.click"));
	public static final SoundEvent BLOCK_RESISTOR_CLICK = SoundEvent.of(RedstoneBits.id("block.resistor.click"));
	public static final SoundEvent BLOCK_ADDER_CLICK = SoundEvent.of(RedstoneBits.id("block.adder.click"));
	public static final SoundEvent BLOCK_INVERTER_CLICK = SoundEvent.of(RedstoneBits.id("block.inverter.click"));
	public static final SoundEvent BLOCK_ROTATOR_INVERT = SoundEvent.of(RedstoneBits.id("block.rotator.invert"));
	public static final SoundEvent BLOCK_ROTATOR_ROTATE = SoundEvent.of(RedstoneBits.id("block.rotator.rotate"));
	public static final SoundEvent BLOCK_ROTATOR_FAIL = SoundEvent.of(RedstoneBits.id("block.rotator.fail"));

	public static SoundEvent register(SoundEvent event) {
		Identifier identifier = event.getId();
		return Registry.register(Registries.SOUND_EVENT, identifier, event);
	}

	public static void init() {
		register(BLOCK_COPPER_BUTTON_CLICK_ON);
		register(BLOCK_COPPER_BUTTON_CLICK_OFF);
		register(BLOCK_COUNTER_CLICK);
		register(BLOCK_RESISTOR_CLICK);
		register(BLOCK_ADDER_CLICK);
		register(BLOCK_INVERTER_CLICK);
		register(BLOCK_ROTATOR_ROTATE);
		register(BLOCK_ROTATOR_FAIL);
	}
}
