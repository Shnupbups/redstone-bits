package com.shnupbups.redstonebits;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

public class RedstoneBits implements ModInitializer {

	public static Identifier id(String name) {
		return new Identifier("redstonebits", name);
	}

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModBlockEntities.init();
		ModSoundEvents.init();
		ModScreenHandlers.init();
	}
}
