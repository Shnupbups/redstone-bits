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
		if (item instanceof BlockItem) {
			Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
			BlockPos blockPos = pointer.getPos().offset(direction);
			Direction direction2 = pointer.getWorld().isAir(blockPos.down()) ? direction : Direction.UP;
			try {
				this.setSuccess(((BlockItem)item).place(new BetterAutomaticItemPlacementContext(pointer.getWorld(), blockPos, direction, stack, direction2)).isAccepted());
			}
			catch (Exception exception) {
				RedstoneBits.LOGGER.error("Error trying to place block at {}: {}", blockPos, exception);
			}
		}
		return stack;
	}
}
