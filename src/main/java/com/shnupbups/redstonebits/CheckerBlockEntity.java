package com.shnupbups.redstonebits;

import com.sun.istack.internal.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.Generic3x3Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;

public class CheckerBlockEntity extends LockableContainerBlockEntity {
	private DefaultedList<ItemStack> inventory;

	public CheckerBlockEntity() {
		super(RedstoneBits.CHECKER);
		this.inventory = DefaultedList.create(9, ItemStack.EMPTY);
	}

	public boolean matches() {
		BlockState state = this.getWorld().getBlockState(this.getCheckPos());
		Iterator var1 = this.inventory.iterator();

		ItemStack itemStack_1;
		while(!var1.hasNext()) {
			itemStack_1 = (ItemStack)var1.next();
			if(itemStack_1.getItem().equals(state.getBlock().getItem())) {
				return true;
			}
		}
		return false;
	}

	public BlockPos getCheckPos() {
		return this.getPos().add(this.getWorld().getBlockState(this.getPos()).get(Properties.FACING).getVector());
	}

	public TextComponent getContainerName() {
		return new TranslatableTextComponent("container.redstonebits.checker", new Object[0]);
	}

	protected Container createContainer(int int_1, PlayerInventory playerInventory_1) {
		return new Generic3x3Container(int_1, playerInventory_1, this);
	}

	public int getInvSize() {
		return 9;
	}

	public boolean isInvEmpty() {
		Iterator var1 = this.inventory.iterator();

		ItemStack itemStack_1;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			itemStack_1 = (ItemStack)var1.next();
		} while(itemStack_1.isEmpty());

		return false;
	}

	public void fromTag(CompoundTag compoundTag_1) {
		super.fromTag(compoundTag_1);
		this.inventory = DefaultedList.create(this.getInvSize(), ItemStack.EMPTY);
		Inventories.fromTag(compoundTag_1, this.inventory);
	}

	public CompoundTag toTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		Inventories.toTag(compoundTag_1, this.inventory);
		return compoundTag_1;
	}

	public ItemStack getInvStack(int int_1) {
		return this.inventory.get(int_1);
	}

	public ItemStack takeInvStack(int int_1, int int_2) {
		ItemStack itemStack_1 = Inventories.splitStack(this.inventory, int_1, int_2);
		if (!itemStack_1.isEmpty()) {
			this.markDirty();
		}

		return itemStack_1;
	}

	public ItemStack removeInvStack(int int_1) {
		return Inventories.removeStack(this.inventory, int_1);
	}

	public void setInvStack(int int_1, ItemStack itemStack_1) {
		this.inventory.set(int_1, itemStack_1);
		if (itemStack_1.getAmount() > this.getInvMaxStackAmount()) {
			itemStack_1.setAmount(this.getInvMaxStackAmount());
		}

		this.markDirty();
	}

	public boolean canPlayerUseInv(PlayerEntity playerEntity_1) {
		if (this.world.getBlockEntity(this.pos) != this) {
			return false;
		} else {
			return playerEntity_1.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	public void clear() {
		this.inventory.clear();
	}

	public boolean checkUnlocked(PlayerEntity playerEntity_1) {
		return super.checkUnlocked(playerEntity_1) && !playerEntity_1.isSpectator();
	}

	@Nullable
	public Container createMenu(int int_1, PlayerInventory playerInventory_1, PlayerEntity playerEntity_1) {
		if (this.checkUnlocked(playerEntity_1)) {
			return this.createContainer(int_1, playerInventory_1);
		} else {
			return null;
		}
	}
}
