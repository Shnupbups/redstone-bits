package com.shnupbups.redstonebits.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import com.shnupbups.redstonebits.ModScreenHandlers;

public class BreakerScreenHandler extends ScreenHandler {
	public final PlayerInventory playerInventory;
	public final Inventory inventory;
	private final PropertyDelegate propertyDelegate;

	public BreakerScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(2));
	}

	public BreakerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		this(syncId, playerInventory, inventory, new ArrayPropertyDelegate(2));
	}
	
	public BreakerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModScreenHandlers.BREAKER, syncId);
		checkSize(inventory, 1);
		this.inventory = inventory;
		this.playerInventory = playerInventory;
		inventory.onOpen(playerInventory.player);
		
		this.addSlot(new Slot(inventory, 0, 80, 35));
		
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
			}
		}
		
		for (int slot = 0; slot < 9; ++slot) {
			this.addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 142));
		}
		this.propertyDelegate = propertyDelegate;
		this.addProperties(propertyDelegate);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}
	
	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack stack2 = slot.getStack();
			stack = stack2.copy();
			if (index < 1) {
				if (!this.insertItem(stack2, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(stack2, 0, 1, false)) {
				return ItemStack.EMPTY;
			}
			
			if (stack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
			
			if (stack2.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, stack2);
		}
		
		return stack;
	}
	
	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}
	
	@Environment(EnvType.CLIENT)
	public int getBreakPercentage() {
		if (this.propertyDelegate.get(1) > 0) {
			float div = ((float) this.propertyDelegate.get(0) / (float) this.propertyDelegate.get(1));
			return (int) (div * 100);
		} else return 0;
	}
}
