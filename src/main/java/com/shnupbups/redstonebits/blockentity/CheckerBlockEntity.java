package com.shnupbups.redstonebits.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
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
		
		for (int i = 0; i < this.size(); i++) {
			ItemStack itemStack_1 = this.getStack(i);
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
	protected ScreenHandler createScreenHandler(int int_1, PlayerInventory playerInventory_1) {
		return new Generic3x3ContainerScreenHandler(int_1, playerInventory_1, this);
	}
	
	@Override
	public int size() {
		return 9;
	}
	
	@Override
	public boolean isEmpty() {
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
	public void fromTag(BlockState state, CompoundTag compoundTag_1) {
		super.fromTag(state, compoundTag_1);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.fromTag(compoundTag_1, this.inventory);
	}
	
	@Override
	public CompoundTag toTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		Inventories.toTag(compoundTag_1, this.inventory);
		return compoundTag_1;
	}
	
	@Override
	public ItemStack getStack(int int_1) {
		return this.inventory.get(int_1);
	}
	
	@Override
	public ItemStack removeStack(int int_1, int int_2) {
		ItemStack itemStack_1 = Inventories.splitStack(this.inventory, int_1, int_2);
		if (!itemStack_1.isEmpty()) {
			this.markDirty();
		}
		
		return itemStack_1;
	}
	
	@Override
	public ItemStack removeStack(int int_1) {
		return Inventories.removeStack(this.inventory, int_1);
	}
	
	@Override
	public void setStack(int int_1, ItemStack itemStack_1) {
		this.inventory.set(int_1, itemStack_1);
		if (itemStack_1.getCount() > this.getMaxCountPerStack()) {
			itemStack_1.setCount(this.getMaxCountPerStack());
		}
		
		this.markDirty();
	}
	
	@Override
	public boolean canPlayerUse(PlayerEntity playerEntity_1) {
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
	public ScreenHandler createMenu(int int_1, PlayerInventory playerInventory_1, PlayerEntity playerEntity_1) {
		if (this.checkUnlocked(playerEntity_1)) {
			return this.createScreenHandler(int_1, playerInventory_1);
		} else {
			return null;
		}
	}
}
