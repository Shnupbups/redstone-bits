package com.shnupbups.redstonebits.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface AdvancedRedstoneConnector {
	default boolean connectsToRedstoneInDirection(BlockState state, @Nullable Direction direction) {
		return true;
	}
}
