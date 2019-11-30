package com.shnupbups.redstonebits.blockentity;

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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.RedstoneBits;

import java.util.Iterator;

public class CheckerBlockEntity extends LockableContainerBlockEntity implements Tickable {
	private DefaultedList<ItemStack> inventory;
	
	public CheckerBlockEntity() {
		super(RedstoneBits.CHECKER);
		this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
	}
	
	@Override
	public void tick() {
		if (!world.isClient()) {
			try {
				if (matches() != this.world.getBlockState(this.getPos()).get(Properties.POWERED)) {
					this.world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).with(Properties.POWERED, matches()));
				}
			} catch (Exception e) {
			}
		}
	}
	
	public boolean matches() {
		BlockState state = this.getWorld().getBlockState(this.getCheckPos());
		
		for (int i = 0; i < this.getInvSize(); i++) {
			ItemStack itemStack_1 = this.getInvStack(i);
			if (!itemStack_1.isEmpty() && itemStack_1.getItem().equals(state.getBlock().asItem())) {
				return true;
			}
		}
		return false;
	}
	
	public BlockPos getCheckPos() {
		try {
			return this.getPos().add(this.getWorld().getBlockState(this.getPos()).get(Properties.FACING).getVector());
		} catch (Exception e) {
			return this.getPos();
		}
	}
	
	@Override
	public Text getContainerName() {
		return new TranslatableText("container.redstonebits.checker");
	}
	
	@Override
	protected Container createContainer(int int_1, PlayerInventory playerInventory_1) {
		return new Generic3x3Container(int_1, playerInventory_1, this);
	}
	
	@Override
	public int getInvSize() {
		return 9;
	}
	
	@Override
	public boolean isInvEmpty() {
		Iterator var1 = this.inventory.iterator();
		
		ItemStack itemStack_1;
		do {
			if (!var1.hasNext()) {
				return true;
			}
			
			itemStack_1 = (ItemStack) var1.next();
		} while (itemStack_1.isEmpty());
		
		return false;
	}
	
	@Override
	public void fromTag(CompoundTag compoundTag_1) {
		super.fromTag(compoundTag_1);
		this.inventory = DefaultedList.ofSize(this.getInvSize(), ItemStack.EMPTY);
		Inventories.fromTag(compoundTag_1, this.inventory);
	}
	
	@Override
	public CompoundTag toTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		Inventories.toTag(compoundTag_1, this.inventory);
		return compoundTag_1;
	}
	
	@Override
	public ItemStack getInvStack(int int_1) {
		return this.inventory.get(int_1);
	}
	
	@Override
	public ItemStack takeInvStack(int int_1, int int_2) {
		ItemStack itemStack_1 = Inventories.splitStack(this.inventory, int_1, int_2);
		if (!itemStack_1.isEmpty()) {
			this.markDirty();
		}
		
		return itemStack_1;
	}
	
	@Override
	public ItemStack removeInvStack(int int_1) {
		return Inventories.removeStack(this.inventory, int_1);
	}
	
	@Override
	public void setInvStack(int int_1, ItemStack itemStack_1) {
		this.inventory.set(int_1, itemStack_1);
		if (itemStack_1.getCount() > this.getInvMaxStackAmount()) {
			itemStack_1.setCount(this.getInvMaxStackAmount());
		}
		
		this.markDirty();
	}
	
	@Override
	public boolean canPlayerUseInv(PlayerEntity playerEntity_1) {
		if (this.world.getBlockEntity(this.pos) != this) {
			return false;
		} else {
			return playerEntity_1.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}
	
	@Override
	public void clear() {
		this.inventory.clear();
	}
	
	@Override
	public boolean checkUnlocked(PlayerEntity playerEntity_1) {
		return super.checkUnlocked(playerEntity_1) && !playerEntity_1.isSpectator();
	}
	
	@Override
	public Container createMenu(int int_1, PlayerInventory playerInventory_1, PlayerEntity playerEntity_1) {
		if (this.checkUnlocked(playerEntity_1)) {
			return this.createContainer(int_1, playerInventory_1);
		} else {
			return null;
		}
	}
}
