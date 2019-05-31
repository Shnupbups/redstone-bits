package com.shnupbups.redstonebits;

import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.container.screen.BreakerContainerScreen;
import com.shnupbups.redstonebits.blockentity.renderer.BreakerBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {

	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(RedstoneBits.BREAKER_CONTAINER, BreakerContainerScreen::new);
		BlockEntityRendererRegistry.INSTANCE.register(BreakerBlockEntity.class, new BreakerBlockEntityRenderer());
	}
}
