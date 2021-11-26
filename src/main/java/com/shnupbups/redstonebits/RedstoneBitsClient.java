package com.shnupbups.redstonebits;

import net.minecraft.client.render.RenderLayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;

import com.shnupbups.redstonebits.container.screen.BreakerHandledScreen;
import com.shnupbups.redstonebits.container.screen.CheckerHandledScreen;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ModScreenHandlers.BREAKER, BreakerHandledScreen::new);
		ScreenRegistry.register(ModScreenHandlers.CHECKER, CheckerHandledScreen::new);

		BlockRenderLayerMapImpl.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.COUNTER, ModBlocks.RESISTOR, ModBlocks.ADDER, ModBlocks.INVERTER);
	}
}
