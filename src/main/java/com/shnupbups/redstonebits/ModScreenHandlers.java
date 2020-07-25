package com.shnupbups.redstonebits;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;

import net.minecraft.screen.ScreenHandlerType;

import com.shnupbups.redstonebits.container.BreakerScreenHandler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<BreakerScreenHandler> BREAKER = ScreenHandlerRegistry.registerExtended(RedstoneBits.id("breaker"), ((syncId, playerInventory, buf) -> new BreakerScreenHandler(syncId, playerInventory)));

    public static void init() {

    }
}
