package com.shnupbups.redstonebits;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;

import net.minecraft.client.render.RenderLayer;

import com.shnupbups.redstonebits.container.screen.BreakerHandledScreen;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ModScreenHandlers.BREAKER, BreakerHandledScreen::new);
		
		BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.COUNTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.RESISTOR, RenderLayer.getCutout());
	}
}
