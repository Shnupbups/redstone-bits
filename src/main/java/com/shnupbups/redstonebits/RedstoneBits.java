package com.shnupbups.redstonebits;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

public class RedstoneBits implements ModInitializer {

	public static Identifier id(String name) {
		return new Identifier("redstonebits", name);
	}
	
	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModBlockEntities.init();
		ModScreenHandlers.init();
	}
}
