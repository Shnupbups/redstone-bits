package com.shnupbups.redstonebits;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.Arm;
import net.minecraft.util.DefaultedList;
import net.minecraft.world.World;

public class FakeEntity extends LivingEntity {
	public ItemStack heldStack;
	
	public FakeEntity(World world, ItemStack heldStack) {
		super(EntityType.PLAYER, world);
		this.heldStack = heldStack;
	}
	
	@Override
	protected void initDataTracker() {
	
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
	
	}
	
	@Override
	public Iterable<ItemStack> getArmorItems() {
		return DefaultedList.ofSize(4, ItemStack.EMPTY);
	}
	
	@Override
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		if(slot.equals(EquipmentSlot.MAINHAND)) return heldStack;
		return ItemStack.EMPTY;
	}
	
	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {
		if(slot.equals(EquipmentSlot.MAINHAND)) heldStack = stack;
	}
	
	@Override
	public Arm getMainArm() {
		return Arm.RIGHT;
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
	
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		return null;
	}
	
	@Override
	public void setHealth(float health) {
	
	}
}
