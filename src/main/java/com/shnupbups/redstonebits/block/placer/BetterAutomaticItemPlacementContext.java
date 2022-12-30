package com.shnupbups.redstonebits.block.placer;

import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// the vanilla AutomaticItemPlacementContext can cause stack overflows because it's silly
public class BetterAutomaticItemPlacementContext extends ItemPlacementContext {
	private final Direction facing;

	public BetterAutomaticItemPlacementContext(World world, BlockPos pos, Direction facing, ItemStack stack, Direction side) {
		super(world, null, Hand.MAIN_HAND, stack, new BlockHitResult(Vec3d.ofBottomCenter(pos), side, pos, false));
		this.facing = facing;
	}

	@Override
	public BlockPos getBlockPos() {
		return this.getHitResult().getBlockPos();
	}

	@Override
	public Direction getPlayerLookDirection() {
		return Direction.DOWN;
	}

	@Override
	public Direction[] getPlacementDirections() {
		return switch (this.facing) {
			default -> new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};
			case UP -> new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
			case NORTH -> new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.SOUTH};
			case SOUTH -> new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.NORTH};
			case WEST -> new Direction[]{Direction.DOWN, Direction.WEST, Direction.SOUTH, Direction.UP, Direction.NORTH, Direction.EAST};
			case EAST -> new Direction[]{Direction.DOWN, Direction.EAST, Direction.SOUTH, Direction.UP, Direction.NORTH, Direction.WEST};
		};
	}

	@Override
	public Direction getPlayerFacing() {
		return this.facing.getAxis() == Direction.Axis.Y ? Direction.NORTH : this.facing;
	}

	@Override
	public boolean shouldCancelInteraction() {
		return false;
	}

	@Override
	public float getPlayerYaw() {
		return this.facing.getHorizontal() * 90;
	}
}