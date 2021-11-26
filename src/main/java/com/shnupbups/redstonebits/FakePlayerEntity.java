package com.shnupbups.redstonebits;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FakePlayerEntity extends PlayerEntity {
	public static final UUID uuid = UUID.fromString("1e795529-c5b7-4b85-8633-7cd4bf3093ee");
	public static final String name = "RedstoneBitsFakePlayer";
	public static final GameProfile profile = new GameProfile(uuid, name);

	public FakePlayerEntity(World world, BlockPos pos) {
		super(world, pos, 0.0f, profile);
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
