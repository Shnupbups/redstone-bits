package com.shnupbups.redstonebits.init;

import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.container.BreakerScreenHandler;
import com.shnupbups.redstonebits.container.CheckerScreenHandler;

public class ModScreenHandlers {
	public static final ScreenHandlerType<BreakerScreenHandler> BREAKER = ScreenHandlerRegistry.registerExtended(RedstoneBits.id("breaker"), ((syncId, playerInventory, buf) -> new BreakerScreenHandler(syncId, playerInventory)));
	public static final ScreenHandlerType<CheckerScreenHandler> CHECKER = ScreenHandlerRegistry.registerExtended(RedstoneBits.id("checker"), ((syncId, playerInventory, buf) -> new CheckerScreenHandler(syncId, playerInventory)));

	public static void init() {

	}
}
