package com.shnupbups.redstonebits;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class FakePlayerEntity extends PlayerEntity {
	public static final UUID uuid = UUID.randomUUID();
	public static final String name = "RedstoneBitsFakePlayer";
	public static final GameProfile profile = new GameProfile(uuid, name);
	
	public FakePlayerEntity(World world, ItemStack heldStack) {
		super(world, new BlockPos(0, 0, 0), profile);
		this.setStackInHand(Hand.MAIN_HAND, heldStack);
	}
	
	@Override
	public boolean isSpectator() {
		return false;
	}
	
	@Override
	public boolean isCreative() {
		return false;
	}
}
