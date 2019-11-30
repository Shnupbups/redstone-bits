package com.shnupbups.redstonebits;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.impl.client.renderer.registry.BlockEntityRendererRegistryImpl;

import com.shnupbups.redstonebits.blockentity.renderer.BreakerBlockEntityRenderer;
import com.shnupbups.redstonebits.container.screen.BreakerContainerScreen;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(RedstoneBits.BREAKER_CONTAINER, BreakerContainerScreen::new);
		BlockEntityRendererRegistryImpl.INSTANCE.register(RedstoneBits.BREAKER, BreakerBlockEntityRenderer::new);
	}
}
