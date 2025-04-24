package com.shnupbups.redstonebits.block.breaker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OperatorBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * Largely copied from ClientPlayerInteractionManager
 */
public class BreakerFakePlayerInteractionManager {
    public final BreakerFakePlayer fakePlayer;
    public final ServerWorld world;
    
    private BlockPos currentBreakingPos = new BlockPos(-1, -1, -1);
    private ItemStack currentTool = ItemStack.EMPTY;
    private float currentBreakingProgress;
    private float blockBreakingSoundCooldown;
    private int blockBreakingCooldown;
    private boolean breakingBlock;
    
    public BreakerFakePlayerInteractionManager(BreakerFakePlayer fakePlayer) {
        this.fakePlayer = fakePlayer;
        this.world = fakePlayer.getServerWorld();
    }

    private boolean breakBlock(BlockPos pos) {
        World world = this.world;
        BlockState blockState = world.getBlockState(pos);
        if (!this.fakePlayer.getMainHandStack().canMine(blockState, world, pos, this.fakePlayer)) {
            return false;
        } else {
            Block block = blockState.getBlock();
            if (block instanceof OperatorBlock && !this.fakePlayer.isCreativeLevelTwoOp()) {
                return false;
            } else if (blockState.isAir()) {
                return false;
            } else {
                block.onBreak(world, pos, blockState, this.fakePlayer);
                FluidState fluidState = world.getFluidState(pos);
                boolean bl = world.setBlockState(pos, fluidState.getBlockState(), Block.NOTIFY_ALL_AND_REDRAW);
                if (bl) {
                    block.onBroken(world, pos, blockState);
                }

                return bl;
            }
        }
    }

    public boolean attackBlock(BlockPos pos, Direction direction) {
        if (!this.world.getWorldBorder().contains(pos)) {
            return false;
        } else {
            if (this.fakePlayer.getAbilities().creativeMode) {
                this.breakBlock(pos);
                this.fakePlayer.startBlockBreak(pos, direction);
                this.blockBreakingCooldown = 5;
            } else if (!this.breakingBlock || !this.isCurrentlyBreaking(pos)) {
                if (this.breakingBlock) {
                    this.fakePlayer.abortBlockBreak(this.currentBreakingPos, direction);
                }

                BlockState blockState = this.world.getBlockState(pos);
                boolean bl = !blockState.isAir();
                if (bl && this.currentBreakingProgress == 0.0F) {
                    blockState.onBlockBreakStart(this.world, pos, this.fakePlayer);
                }

                if (bl && blockState.calcBlockBreakingDelta(this.fakePlayer, this.fakePlayer.getWorld(), pos) >= 1.0F) {
                    this.breakBlock(pos);
                } else {
                    this.breakingBlock = true;
                    this.currentBreakingPos = pos;
                    this.currentTool = this.fakePlayer.getMainHandStack();
                    this.currentBreakingProgress = 0.0F;
                    this.blockBreakingSoundCooldown = 0.0F;
                    this.world.setBlockBreakingInfo(this.fakePlayer.getId(), this.currentBreakingPos, this.getBlockBreakingProgress());
                }

                this.fakePlayer.startBlockBreak(pos, direction);
            }

            return true;
        }
    }

    public void cancelBlockBreaking() {
        if (this.breakingBlock) {
            this.fakePlayer.abortBlockBreak(this.currentBreakingPos, Direction.DOWN);
            this.breakingBlock = false;
            this.currentBreakingProgress = 0.0F;
            this.world.setBlockBreakingInfo(this.fakePlayer.getId(), this.currentBreakingPos, -1);
            this.fakePlayer.resetLastAttackedTicks();
        }
    }

    public boolean updateBlockBreakingProgress(BlockPos pos, Direction direction) {
        if (this.blockBreakingCooldown > 0) {
            this.blockBreakingCooldown--;
            return true;
        } else if (this.fakePlayer.getAbilities().creativeMode && this.world.getWorldBorder().contains(pos)) {
            this.blockBreakingCooldown = 5;
            this.breakBlock(pos);
            this.fakePlayer.startBlockBreak(pos, direction);
            return true;
        } else if (this.isCurrentlyBreaking(pos)) {
            BlockState blockState = this.world.getBlockState(pos);
            if (blockState.isAir()) {
                this.breakingBlock = false;
                return false;
            } else {
                this.currentBreakingProgress = this.currentBreakingProgress + blockState.calcBlockBreakingDelta(this.fakePlayer, this.fakePlayer.getWorld(), pos);
                if (this.blockBreakingSoundCooldown % 4.0F == 0.0F) {
                    BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
                    this.world.playSound(null,
                                    pos,
                                    blockSoundGroup.getHitSound(),
                                    SoundCategory.BLOCKS,
                                    (blockSoundGroup.getVolume() + 1.0F) / 8.0F,
                                    blockSoundGroup.getPitch() * 0.5F
                            );
                }

                this.blockBreakingSoundCooldown++;
                if (this.currentBreakingProgress >= 1.0F) {
                    this.breakingBlock = false;
                    this.breakBlock(pos);
                    this.fakePlayer.finishBlockBreak(pos, direction);
                    this.currentBreakingProgress = 0.0F;
                    this.blockBreakingSoundCooldown = 0.0F;
                    this.blockBreakingCooldown = 5;
                }

                this.world.setBlockBreakingInfo(this.fakePlayer.getId(), this.currentBreakingPos, this.getBlockBreakingProgress());
                return true;
            }
        } else {
            return this.attackBlock(pos, direction);
        }
    }

    private boolean isCurrentlyBreaking(BlockPos pos) {
        ItemStack itemStack = this.fakePlayer.getMainHandStack();
        return pos.equals(this.currentBreakingPos) && ItemStack.areItemsAndComponentsEqual(itemStack, this.currentTool);
    }

    public boolean isBreakingBlock() {
        return this.breakingBlock;
    }

    public int getBlockBreakingProgress() {
        return this.currentBreakingProgress > 0.0F ? (int)(this.currentBreakingProgress * 10.0F) : -1;
    }
}
