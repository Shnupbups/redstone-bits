package com.shnupbups.redstonebits.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.ArrayPropertyDelegate;
import net.minecraft.container.Container;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class BreakerContainer extends Container {
	public final PlayerInventory playerInventory;
	public final Inventory inventory;
	private final PropertyDelegate propertyDelegate;

	public BreakerContainer(int int_1, PlayerInventory playerInventory_1) {
		this(int_1, playerInventory_1, new BasicInventory(1), new ArrayPropertyDelegate(2));
	}

	public BreakerContainer(int int_1, PlayerInventory playerInventory_1, PropertyDelegate propertyDelegate) {
		this(int_1, playerInventory_1, new BasicInventory(1), propertyDelegate);
	}

	public BreakerContainer(int int_1, PlayerInventory playerInventory_1, Inventory inventory_1) {
		this(int_1, playerInventory_1, inventory_1, new ArrayPropertyDelegate(2));
	}

	public BreakerContainer(int int_1, PlayerInventory playerInventory_1, Inventory inventory_1, PropertyDelegate propertyDelegate) {
		super(null, int_1);
		checkContainerSize(inventory_1, 1);
		this.inventory = inventory_1;
		this.playerInventory = playerInventory_1;
		inventory_1.onInvOpen(playerInventory_1.player);

		this.addSlot(new Slot(inventory_1, 0, 80, 35));

		for(int row = 0; row < 3; ++row) {
			for(int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory_1, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
			}
		}

		for(int slot = 0; slot < 9; ++slot) {
			this.addSlot(new Slot(playerInventory_1, slot, 8 + slot * 18, 142));
		}
		this.propertyDelegate = propertyDelegate;
		this.addProperties(propertyDelegate);
	}

	public boolean canUse(PlayerEntity playerEntity_1) {
		return this.inventory.canPlayerUseInv(playerEntity_1);
	}

	public ItemStack transferSlot(PlayerEntity playerEntity_1, int int_1) {
		ItemStack itemStack_1 = ItemStack.EMPTY;
		Slot slot_1 = this.slotList.get(int_1);
		if (slot_1 != null && slot_1.hasStack()) {
			ItemStack itemStack_2 = slot_1.getStack();
			itemStack_1 = itemStack_2.copy();
			if (int_1 < 1) {
				if (!this.insertItem(itemStack_2, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack_2, 0, 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack_2.isEmpty()) {
				slot_1.setStack(ItemStack.EMPTY);
			} else {
				slot_1.markDirty();
			}

			if (itemStack_2.getAmount() == itemStack_1.getAmount()) {
				return ItemStack.EMPTY;
			}

			slot_1.onTakeItem(playerEntity_1, itemStack_2);
		}

		return itemStack_1;
	}

	public void close(PlayerEntity playerEntity_1) {
		super.close(playerEntity_1);
		this.inventory.onInvClose(playerEntity_1);
	}

	@Environment(EnvType.CLIENT)
	public int getBreakPercentage() {
		if(this.propertyDelegate.get(1)>0) {
			float div = ((float)this.propertyDelegate.get(0)/(float)this.propertyDelegate.get(1));
			return (int)(div*100);
		} else return 0;
	}
}
