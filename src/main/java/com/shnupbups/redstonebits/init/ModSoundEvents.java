package com.shnupbups.redstonebits.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.shnupbups.redstonebits.RedstoneBits;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_ON = new SoundEvent(RedstoneBits.id("block.copper_button.click_on"));
	public static final SoundEvent BLOCK_COPPER_BUTTON_CLICK_OFF = new SoundEvent(RedstoneBits.id("block.copper_button.click_off"));
	public static final SoundEvent BLOCK_COUNTER_CLICK = new SoundEvent(RedstoneBits.id("block.counter.click"));
	public static final SoundEvent BLOCK_RESISTOR_CLICK = new SoundEvent(RedstoneBits.id("block.resistor.click"));
	public static final SoundEvent BLOCK_ADDER_CLICK = new SoundEvent(RedstoneBits.id("block.adder.click"));
	public static final SoundEvent BLOCK_INVERTER_CLICK = new SoundEvent(RedstoneBits.id("block.inverter.click"));
	public static final SoundEvent BLOCK_ROTATOR_INVERT = new SoundEvent(RedstoneBits.id("block.rotator.invert"));
	public static final SoundEvent BLOCK_ROTATOR_ROTATE = new SoundEvent(RedstoneBits.id("block.rotator.rotate"));
	public static final SoundEvent BLOCK_ROTATOR_FAIL = new SoundEvent(RedstoneBits.id("block.rotator.fail"));

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
		register(BLOCK_INVERTER_CLICK);
		register(BLOCK_ROTATOR_ROTATE);
		register(BLOCK_ROTATOR_FAIL);
	}
}
