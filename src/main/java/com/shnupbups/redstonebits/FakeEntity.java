package com.shnupbups.redstonebits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class FakeEntity extends Entity {
	public FakeEntity(World world) {
		super(EntityType.PLAYER, world);
	}
	
	@Override
	protected void initDataTracker() {
	
	}
	
	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {
	
	}
	
	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {
	
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		return null;
	}
}
