package com.shnupbups.redstonebits.block;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RedstoneGlassBlock extends AbstractGlassBlock {
	public static final IntProperty POWER = Properties.POWER;

	public RedstoneGlassBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWER, 0));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(POWER, getPowerFor(ctx.getWorld(), ctx.getBlockPos()));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if(world instanceof World w) return state.with(POWER, getPowerFor(w, pos));
		else return state;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		world.updateComparators(pos, this);
	}

	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return shouldBeTransparent(state, world, pos) ? super.getCameraCollisionShape(state, world, pos, context) : VoxelShapes.fullCube();
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return shouldBeTransparent(state, world, pos) ? super.getAmbientOcclusionLightLevel(state, world, pos) : 0.2F;
	}

	@Override
	public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return shouldBeTransparent(state, world, pos);
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if(!shouldBeTransparent(state)) return false;
		return stateFrom.isOf(this) && shouldBeTransparent(stateFrom);
	}

	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.isOf(this) ? state.get(POWER) : 0;
	}

	public int getPowerFor(World world, BlockPos pos) {
		if(world.isReceivingRedstonePower(pos)) return 15;
		else {
			int adjPower = 0;
			for (Direction direction : DIRECTIONS) {
				BlockState dirState = world.getBlockState(pos.offset(direction));
				if(dirState.isOf(this) && dirState.get(POWER) > adjPower) adjPower = dirState.get(POWER);
				if(adjPower == 15) break;
			}
			return Math.max(adjPower - 1, 0);
		}
	}

	public static boolean shouldBeTransparent(BlockState state) {
		return state.contains(POWER) && state.get(POWER) > 0;
	}

	public static boolean shouldBeTransparent(BlockState state, BlockView world, BlockPos pos) {
		return shouldBeTransparent(state);
	}

	public static boolean shouldBeOpaque(BlockState state, BlockView world, BlockPos pos) {
		return !shouldBeTransparent(state, world, pos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}
}
