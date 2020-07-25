package com.shnupbups.redstonebits;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.fabricmc.fabric.impl.client.renderer.registry.BlockEntityRendererRegistryImpl;

import net.minecraft.client.render.RenderLayer;

import com.shnupbups.redstonebits.blockentity.renderer.BreakerBlockEntityRenderer;
import com.shnupbups.redstonebits.container.screen.BreakerHandledScreen;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(RedstoneBits.BREAKER_CONTAINER, BreakerHandledScreen::new);
		BlockEntityRendererRegistryImpl.INSTANCE.register(RedstoneBits.BREAKER, BreakerBlockEntityRenderer::new);
		
		BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.COUNTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.RESISTOR, RenderLayer.getCutout());
	}
}
