package com.shnupbups.redstonebits;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.shnupbups.redstonebits.init.ModBlockEntities;
import com.shnupbups.redstonebits.init.ModBlocks;
import com.shnupbups.redstonebits.init.ModScreenHandlers;
import com.shnupbups.redstonebits.init.ModSoundEvents;

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
