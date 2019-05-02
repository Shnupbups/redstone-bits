package com.shnupbups.redstonebits;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;

public class RedstoneBitsClient implements ClientModInitializer {

	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(RedstoneBits.BREAKER_CONTAINER, BreakerContainerScreen::new);
		BlockEntityRendererRegistry.INSTANCE.register(BreakerBlockEntity.class, new BreakerBlockEntityRenderer());
	}
}
