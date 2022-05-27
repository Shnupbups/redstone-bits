package com.shnupbups.redstonebits.block.placer;

import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;

public class BlacklistedDispenserBehavior extends FallibleItemDispenserBehavior {
	@Override
	public boolean isSuccess() {
		return false;
	}
}
