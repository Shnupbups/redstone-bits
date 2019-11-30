package com.shnupbups.redstonebits.blockentity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
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

import com.shnupbups.redstonebits.ModProperties;
import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.container.BreakerContainer;

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
			public int get(int int_1) {
				switch (int_1) {
					case 0:
						return BreakerBlockEntity.this.breakProgress;
					case 1:
						return BreakerBlockEntity.this.getBreakTime();
					default:
						return 0;
				}
			}
			
			@Override
			public void set(int int_1, int int_2) {
				switch (int_1) {
					case 0:
						BreakerBlockEntity.this.breakProgress = int_2;
						break;
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
		float itemMultiplier = this.getTool().getMiningSpeed(breakState);
		int level = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, this.getTool());
		if (level > 0) {
			itemMultiplier += (level * level + 1);
		}
		float time = baseTime / itemMultiplier;
		return (int) time;
	}
	
	public boolean isToolEffective() {
		if (this.breakState == null) return false;
		ItemStack item = this.getInvStack(0);
		return this.breakState.getMaterial().canBreakByHand() || item.isEffectiveOn(breakState);
	}
	
	public float calcBlockBreakingTime() {
		float float_1 = this.breakState.getHardness(this.getWorld(), this.getBreakPos());
		if (float_1 == -1.0F) {
			return 0.0F;
		} else {
			int int_1 = isToolEffective() ? 30 : 100;
			return float_1 * (float) int_1;
		}
	}
	
	public void startBreak() {
		//System.out.println("start break at "+getBreakPos().toString());
		this.breakState = this.getWorld().getBlockState(this.getBreakPos());
		this.breakStack = this.getTool();
		this.breakProgress++;
		BlockState state = this.getWorld().getBlockState(this.getPos());
		this.markDirty();
	}
	
	public void cancelBreak() {
		//System.out.println("finish/cancel break at "+getBreakPos().toString());
		this.breakState = null;
		this.breakStack = ItemStack.EMPTY;
		this.breakProgress = 0;
		BlockState state = this.getWorld().getBlockState(this.getPos());
		this.markDirty();
	}
	
	public void finishBreak() {
		//System.out.println("finish break at "+getBreakPos().toString());
		this.breakBlock();
		this.cancelBreak();
		if (this.getTool().getItem().isDamageable()) {
			this.getTool().setDamage(this.getInvStack(0).getDamage() + 1);
			if (this.getTool().getDamage() >= this.getInvStack(0).getMaxDamage()) {
				this.removeInvStack(0);
			}
		}
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
			if (isToolEffective()) {
				Block.dropStacks(breakState, world, getBreakPos(), blockEntity, null, getTool());
			}
			return world.breakBlock(getBreakPos(), false);
		} else return true;
	}
	
	public void continueBreak() {
		this.breakProgress++;
		this.markDirty();
	}
	
	public ItemStack getTool() {
		return this.getInvStack(0);
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
	protected Container createContainer(int int_1, PlayerInventory playerInventory_1) {
		return new BreakerContainer(int_1, playerInventory_1, this, this.propertyDelegate);
	}
	
	@Override
	public int getInvSize() {
		return 1;
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
		this.breakProgress = compoundTag_1.getInt("BreakProgress");
		Inventories.fromTag(compoundTag_1, this.inventory);
	}
	
	@Override
	public CompoundTag toTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		Inventories.toTag(compoundTag_1, this.inventory);
		compoundTag_1.putInt("BreakProgress", breakProgress);
		return compoundTag_1;
	}
	
	@Override
	public void fromClientTag(CompoundTag compoundTag_1) {
		super.fromTag(compoundTag_1);
		this.inventory = DefaultedList.ofSize(this.getInvSize(), ItemStack.EMPTY);
		this.breakProgress = compoundTag_1.getInt("BreakProgress");
		Inventories.fromTag(compoundTag_1, this.inventory);
	}
	
	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		Inventories.toTag(compoundTag_1, this.inventory);
		compoundTag_1.putInt("BreakProgress", breakProgress);
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
	
	public int getBreakPercentage() {
		if (this.getBreakTime() > 0) {
			float div = ((float) this.breakProgress / (float) this.getBreakTime());
			return Math.min((int) (div * 100), 100);
		} else return 0;
	}
}
