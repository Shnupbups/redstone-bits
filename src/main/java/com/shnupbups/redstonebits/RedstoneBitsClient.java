package com.shnupbups.redstonebits;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import com.shnupbups.redstonebits.screen.handled.BreakerHandledScreen;
import com.shnupbups.redstonebits.screen.handled.CheckerHandledScreen;
import com.shnupbups.redstonebits.init.RBBlocks;
import com.shnupbups.redstonebits.init.RBScreenHandlers;

@SuppressWarnings("unused")
public class RedstoneBitsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(RBScreenHandlers.BREAKER, BreakerHandledScreen::new);
		HandledScreens.register(RBScreenHandlers.CHECKER, CheckerHandledScreen::new);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), RBBlocks.COUNTER, RBBlocks.RESISTOR, RBBlocks.ADDER, RBBlocks.INVERTER, RBBlocks.REDSTONE_GLASS);
	}
}
