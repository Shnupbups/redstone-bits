package com.shnupbups.redstonebits.block.placer;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import com.shnupbups.redstonebits.RedstoneBits;

// BlockPlacementDispenserBehavior but using BetterAutomaticItemPlacementContext so it doesn't cause stack overflows
public class BetterBlockPlacementDispenserBehavior extends FallibleItemDispenserBehavior {
	@Override
	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		this.setSuccess(false);
		Item item = stack.getItem();
		if (item instanceof BlockItem blockItem) {
			Direction direction = pointer.state().get(DispenserBlock.FACING);
			BlockPos blockPos = pointer.pos().offset(direction);
			Direction direction2 = pointer.world().isAir(blockPos.down()) ? direction : Direction.UP;
			try {
				this.setSuccess(blockItem.place(new BetterAutomaticItemPlacementContext(pointer.world(), blockPos, direction, stack, direction2)).isAccepted());
			}
			catch (Exception exception) {
				RedstoneBits.LOGGER.error("Error trying to place block at {} with item {}: {}", blockPos, item, exception);
			}
		}
		return stack;
	}
}
