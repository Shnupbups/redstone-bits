package com.shnupbups.redstonebits;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;

import com.shnupbups.redstonebits.container.screen.BreakerHandledScreen;
import com.shnupbups.redstonebits.container.screen.CheckerHandledScreen;
import com.shnupbups.redstonebits.init.ModBlocks;
import com.shnupbups.redstonebits.init.ModScreenHandlers;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.BREAKER, BreakerHandledScreen::new);
		HandledScreens.register(ModScreenHandlers.CHECKER, CheckerHandledScreen::new);

		BlockRenderLayerMapImpl.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.COUNTER, ModBlocks.RESISTOR, ModBlocks.ADDER, ModBlocks.INVERTER);
	}
}
