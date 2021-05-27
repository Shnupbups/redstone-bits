package com.shnupbups.redstonebits.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface AdvancedRedstoneConnector {
	default boolean connectsToRedstoneInDirection(BlockState state, Direction direction) {
		return true;
	}
}
