package com.shnupbups.redstonebits.block.breaker;

import com.mojang.authlib.GameProfile;
import com.shnupbups.redstonebits.mixin.ServerPlayerInteractionManagerAccessor;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.UUID;

public class BreakerFakePlayer extends FakePlayer {
    public static final UUID DEFAULT_UUID = UUID.fromString("f5cd523e-57ff-4064-a86a-04cc7245fc15");
    public static final GameProfile DEFAULT_PROFILE = new GameProfile(DEFAULT_UUID, "[Breaker]");

    public BreakerFakePlayer(ServerWorld world) {
        super(world, DEFAULT_PROFILE);
    }

    @Override
    public void tick() {
        this.interactionManager.update();
    }

    public ServerPlayerInteractionManager getInteractionManager() {
        return interactionManager;
    }

    public boolean shouldSkipBlockDrops() {
        return false;
    }

    public int getBlockBreakingProgress() {
        return ((ServerPlayerInteractionManagerAccessor)this.getInteractionManager()).getBlockBreakingProgress();
    }

    public void startBlockBreak(BlockPos pos, Direction direction) {
        this.blockBreakingAction(pos, PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, direction);
    }

    public void abortBlockBreak(BlockPos pos, Direction direction) {
        this.blockBreakingAction(pos, PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, direction);
    }

    public void finishBlockBreak(BlockPos pos, Direction direction) {
        this.blockBreakingAction(pos, PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, direction);
    }

    private void blockBreakingAction(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction) {
        this.getInteractionManager().processBlockBreakingAction(pos, action, direction, this.getWorld().getTopYInclusive(), 0);
    }
}
