package com.shnupbups.redstonebits.blockentity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.FakePlayerEntity;
import com.shnupbups.redstonebits.properties.ModProperties;
import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.container.BreakerScreenHandler;

import java.util.Iterator;

public class BreakerBlockEntity extends LockableContainerBlockEntity implements Tickable, BlockEntityClientSerializable {
	protected final PropertyDelegate propertyDelegate;
	public BlockState breakState;
	public ItemStack breakStack = ItemStack.EMPTY;
	public int breakProgress = 0;
	private DefaultedList<ItemStack> inventory;
	
	public BreakerBlockEntity() {
		super(RedstoneBits.BREAKER);
		this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
		this.propertyDelegate = new PropertyDelegate() {
			@Override
			public int get(int index) {
				switch (index) {
					case 0:
						return BreakerBlockEntity.this.breakProgress;
					case 1:
						return BreakerBlockEntity.this.getBreakTime();
					default:
						return 0;
				}
			}
			
			@Override
			public void set(int index, int value) {
				if (index == 0) {
					BreakerBlockEntity.this.breakProgress = value;
				}
				
			}
			
			@Override
			public int size() {
				return 1;
			}
		};
	}
	
	public int getBreakTime() {
		if (this.breakState == null) return 0;
		float baseTime = this.calcBlockBreakingTime();
		float itemMultiplier = this.getTool().getMiningSpeedMultiplier(breakState);
		int level = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, this.getTool());
		if (level > 0) {
			itemMultiplier += (level * level + 1);
		}
		float time = baseTime / itemMultiplier;
		return (int) time;
	}
	
	public boolean isToolEffective() {
		if (this.breakState == null) return false;
		ItemStack item = this.getStack(0);
		return !this.breakState.isToolRequired() || item.isEffectiveOn(this.breakState);
	}
	
	public float calcBlockBreakingTime() {
		float hardness = this.breakState.getHardness(this.getWorld(), this.getBreakPos());
		if (hardness == -1.0F) {
			return 0.0F;
		} else {
			int multiplier = isToolEffective() ? 30 : 100;
			return hardness * (float) multiplier;
		}
	}
	
	public void startBreak() {
		//System.out.println("start break at "+getBreakPos().toString());
		this.breakState = this.getWorld().getBlockState(this.getBreakPos());
		this.breakStack = this.getTool();
		this.breakProgress++;
		this.markDirty();
	}
	
	public void cancelBreak() {
		//System.out.println("finish/cancel break at "+getBreakPos().toString());
		this.breakState = null;
		this.breakStack = ItemStack.EMPTY;
		this.breakProgress = 0;
		this.markDirty();
	}
	
	public void finishBreak() {
		//System.out.println("finish break at "+getBreakPos().toString());
		this.breakBlock();
		this.cancelBreak();
		this.markDirty();
	}
	
	public boolean isBreaking() {
		return breakProgress > 0;
	}
	
	public BlockPos getBreakPos() {
		try {
			return this.getPos().add(this.getWorld().getBlockState(this.getPos()).get(Properties.FACING).getVector());
		} catch (Exception e) {
			return this.getPos();
		}
	}
	
	public boolean breakBlock() {
		//System.out.println("break at "+getBreakPos().toString());
		if (!world.isClient()) {
			BlockEntity blockEntity = breakState.getBlock().hasBlockEntity() ? this.world.getBlockEntity(getBreakPos()) : null;
			FakePlayerEntity entity = new FakePlayerEntity(world, getTool());
			if (getTool().getItem().canMine(breakState, world, getBreakPos(), entity) && isToolEffective()) {
				Block.dropStacks(breakState, world, getBreakPos(), blockEntity, entity, getTool());
			}
			getTool().getItem().postMine(getTool(), world, breakState, getBreakPos(), entity);
			return world.breakBlock(getBreakPos(), false);
		} else return true;
	}
	
	public void continueBreak() {
		this.breakProgress++;
		this.markDirty();
	}
	
	public ItemStack getTool() {
		return this.getStack(0);
	}
	
	@Override
	public void tick() {
		BlockState currentBreakState = this.getWorld().getBlockState(this.getBreakPos());
		if (this.isBreaking()) {
			if (breakState == null) startBreak();
			if (!breakStack.equals(this.getTool()) || !breakState.equals(currentBreakState) || currentBreakState.isAir() || currentBreakState.getHardness(world, pos) < 0) {
				//System.out.println("cancel");
				cancelBreak();
			} else if (breakProgress >= getBreakTime()) {
				//System.out.println("break");
				finishBreak();
			} else {
				continueBreak();
			}
		}
		if (this.isBreaking() != this.getWorld().getBlockState(this.getPos()).get(ModProperties.BREAKING)) {
			this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).with(ModProperties.BREAKING, this.isBreaking()));
			this.markDirty();
		}
		
	}
	
	@Override
	public Text getContainerName() {
		return new TranslatableText("container.redstonebits.breaker");
	}
	
	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new BreakerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}
	
	@Override
	public int size() {
		return 1;
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
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		this.breakProgress = tag.getInt("BreakProgress");
		Inventories.fromTag(tag, this.inventory);
	}
	
	@Override
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		Inventories.toTag(tag, this.inventory);
		tag.putInt("BreakProgress", breakProgress);
		return tag;
	}

	@Override
	public void fromClientTag(CompoundTag tag) {
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		this.breakProgress = tag.getInt("BreakProgress");
		Inventories.fromTag(tag, this.inventory);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag) {
		super.toTag(tag);
		Inventories.toTag(tag, this.inventory);
		tag.putInt("BreakProgress", breakProgress);
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
	
	public int getBreakPercentage() {
		if (this.getBreakTime() > 0) {
			float div = ((float) this.breakProgress / (float) this.getBreakTime());
			return Math.min((int) (div * 100), 100);
		} else return 0;
	}
}
