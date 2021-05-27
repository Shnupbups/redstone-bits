package com.shnupbups.redstonebits.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.ModBlockEntities;

import java.util.Iterator;

public class CheckerBlockEntity extends LockableContainerBlockEntity {
	private DefaultedList<ItemStack> inventory;
	
	public CheckerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CHECKER, pos, state);
		this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
	}

	public static void serverTick(World world, BlockPos pos, BlockState state, CheckerBlockEntity blockEntity) {
		if (!world.isClient()) {
			try {
				if (blockEntity.matches() != world.getBlockState(pos).get(Properties.POWERED)) {
					world.setBlockState(pos, world.getBlockState(pos).with(Properties.POWERED, blockEntity.matches()));
				}
			} catch (Exception ignored) {
			}
		}
	}
	
	public boolean matches() {
		BlockState state = this.getWorld().getBlockState(this.getCheckPos());
		
		for (int i = 0; i < this.size(); i++) {
			ItemStack stack = this.getStack(i);
			if (!stack.isEmpty() && stack.getItem().equals(state.getBlock().asItem())) {
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
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
	}
	
	@Override
	public int size() {
		return 9;
	}
	
	@Override
	public boolean isEmpty() {
		Iterator var1 = this.inventory.iterator();
		
		ItemStack stack;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			stack = (ItemStack) var1.next();
		} while (stack.isEmpty());
		
		return false;
	}
	
	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(tag, this.inventory);
	}
	
	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		Inventories.writeNbt(tag, this.inventory);
		return tag;
	}
	
	@Override
	public ItemStack getStack(int slot) {
		return this.inventory.get(slot);
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
		if (!stack.isEmpty()) {
			this.markDirty();
		}
		
		return stack;
	}
	
	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.inventory, slot);
	}
	
	@Override
	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}
		
		this.markDirty();
	}
	
	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (this.world.getBlockEntity(this.pos) != this) {
			return false;
		} else {
			return player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}
	
	@Override
	public void clear() {
		this.inventory.clear();
	}
	
	@Override
	public boolean checkUnlocked(PlayerEntity player) {
		return super.checkUnlocked(player) && !player.isSpectator();
	}
	
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		if (this.checkUnlocked(player)) {
			return this.createScreenHandler(syncId, playerInventory);
		} else {
			return null;
		}
	}
}
