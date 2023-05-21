package com.shnupbups.redstonebits.blockentity;

import java.util.Iterator;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.init.RBBlockEntities;
import com.shnupbups.redstonebits.screen.handler.CheckerScreenHandler;

public class CheckerBlockEntity extends LockableContainerBlockEntity {
	private DefaultedList<ItemStack> inventory;

	public CheckerBlockEntity(BlockPos pos, BlockState state) {
		super(RBBlockEntities.CHECKER, pos, state);
		this.inventory = DefaultedList.ofSize(15, ItemStack.EMPTY);
	}

	public static void serverTick(World world, BlockPos pos, BlockState state, CheckerBlockEntity blockEntity) {
		if (!world.isClient()) {
			if (blockEntity.matches() != state.get(Properties.POWER)) {
				world.setBlockState(pos, state.with(Properties.POWER, blockEntity.matches()));
			}
		}
	}

	public int matches() {
		BlockState state = this.getWorld().getBlockState(this.getCheckPos());

		for (int slot = 0; slot < this.size(); slot++) {
			ItemStack stack = this.getStack(slot);
			if (!stack.isEmpty() && stack.getItem().equals(state.getBlock().asItem())) {
				return slot + 1;
			}
		}
		return 0;
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
		return Text.translatable("container.redstonebits.checker");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new CheckerScreenHandler(syncId, playerInventory, this);
	}

	@Override
	public int size() {
		return 15;
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(tag, this.inventory);
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		Inventories.writeNbt(tag, this.inventory);
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
		return Inventory.canPlayerUse(this, player);
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
