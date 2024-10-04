package com.shnupbups.redstonebits.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.screen.handler.BreakerScreenHandler;
import com.shnupbups.redstonebits.screen.handler.CheckerScreenHandler;

public class RBScreenHandlers {
	public static final ScreenHandlerType<BreakerScreenHandler> BREAKER = new ExtendedScreenHandlerType<>((syncId, playerInventory, data) -> new BreakerScreenHandler(syncId, playerInventory), BreakerScreenHandler.Data.PACKET_CODEC);
	public static final ScreenHandlerType<CheckerScreenHandler> CHECKER = new ExtendedScreenHandlerType<>((syncId, playerInventory, data) -> new CheckerScreenHandler(syncId, playerInventory), CheckerScreenHandler.Data.PACKET_CODEC);

	public static void init() {
		Registry.register(Registries.SCREEN_HANDLER, RedstoneBits.id("breaker"), BREAKER);
		Registry.register(Registries.SCREEN_HANDLER, RedstoneBits.id("checker"), CHECKER);
	}
}
