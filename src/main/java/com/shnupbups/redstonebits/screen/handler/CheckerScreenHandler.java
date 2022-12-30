package com.shnupbups.redstonebits.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import com.shnupbups.redstonebits.init.RBScreenHandlers;

public class CheckerScreenHandler extends ScreenHandler {
	public final PlayerInventory playerInventory;
	public final Inventory inventory;

	public CheckerScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(15));
	}

	public CheckerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(RBScreenHandlers.CHECKER, syncId);
		checkSize(inventory, 15);
		this.inventory = inventory;
		this.playerInventory = playerInventory;
		inventory.onOpen(playerInventory.player);

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 5; column++) {
				this.addSlot(new Slot(inventory, column + row * 5, 44 + column * 18, 17 + row * 18));
			}
		}

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
			}
		}

		for (int slot = 0; slot < 9; slot++) {
			this.addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 142));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < 15 ? !this.insertItem(itemStack2, 15, 51, true) : !this.insertItem(itemStack2, 0, 15, false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTakeItem(player, itemStack2);
		}
		return itemStack;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}
}
