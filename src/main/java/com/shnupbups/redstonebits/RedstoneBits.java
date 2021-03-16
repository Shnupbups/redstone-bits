package com.shnupbups.redstonebits;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;
import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;
import com.shnupbups.redstonebits.container.BreakerScreenHandler;

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
