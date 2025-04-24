package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public abstract class AbstractRedstoneBitsGateBlock extends AbstractRedstoneGateBlock implements AdvancedRedstoneConnector {
    public static final BooleanProperty LOCKED = Properties.LOCKED;

    public AbstractRedstoneBitsGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, POWERED, LOCKED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return state.with(LOCKED, this.isLocked(ctx.getWorld(), ctx.getBlockPos(), state));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.DOWN && !this.canPlaceAbove(world, neighborPos, neighborState)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return !world.isClient() && direction.getAxis() != state.get(FACING).getAxis() ? state.with(LOCKED, this.isLocked(world, pos, state)) : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
    }

    @Override
    public boolean isLocked(WorldView world, BlockPos pos, BlockState state) {
        return this.getMaxInputLevelSides(world, pos, state) > 0;
    }

    @Override
    public boolean getSideInputFromGatesOnly() {
        return true;
    }

    @Override
    public boolean connectsToRedstoneInDirection(BlockState state, Direction direction) {
        if (direction != null) {
            Direction facing = state.get(FACING);
            return direction == facing || direction.getOpposite() == facing;
        }
        return false;
    }
}
