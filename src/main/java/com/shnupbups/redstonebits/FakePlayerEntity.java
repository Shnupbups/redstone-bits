package com.shnupbups.redstonebits;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class FakePlayerEntity extends PlayerEntity {
	public static final UUID uuid = UUID.fromString("1e795529-c5b7-4b85-8633-7cd4bf3093ee");
	public static final String name = "RedstoneBitsFakePlayer";
	public static final GameProfile profile = new GameProfile(uuid, name);
	
	public FakePlayerEntity(World world) {
		super(world, BlockPos.ORIGIN, 0.0f, profile);
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
