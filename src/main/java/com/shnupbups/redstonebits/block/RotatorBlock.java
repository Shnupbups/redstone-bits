package com.shnupbups.redstonebits.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.init.ModSoundEvents;
import com.shnupbups.redstonebits.init.ModTags;

public class RotatorBlock extends FacingBlock {
	public static final BooleanProperty INVERTED = Properties.INVERTED;
	public static final BooleanProperty POWERED = Properties.POWERED;

	public static final List<Property<?>> ROTATION_PROPERTIES = List.of(
			Properties.FACING, Properties.HOPPER_FACING, Properties.HORIZONTAL_FACING, Properties.VERTICAL_DIRECTION, // DirectionProperty
			Properties.AXIS, Properties.HORIZONTAL_AXIS, // EnumProperty<Direction.Axis>
			Properties.ROTATION, // IntProperty
			Properties.RAIL_SHAPE, Properties.STRAIGHT_RAIL_SHAPE // EnumProperty<RailShape>
	);

	public RotatorBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP).with(POWERED, false).with(INVERTED, false));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.getAbilities().allowModifyWorld) {
			return ActionResult.PASS;
		} else {
			boolean inverted = state.get(INVERTED);
			float pitch = inverted ? 0.55F : 0.5F;
			world.playSound(player, pos, ModSoundEvents.BLOCK_ROTATOR_INVERT, SoundCategory.BLOCKS, 0.3F, pitch);
			world.setBlockState(pos, state.with(INVERTED, !inverted), Block.NOTIFY_ALL);
			return ActionResult.SUCCESS;
		}
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos pos2, boolean notify) {
		boolean receivingPower = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
		boolean powered = state.get(POWERED);

		BlockPos facingPos = getFacingPos(state, pos);
		boolean shouldUpdateComparators = pos2.equals(facingPos);

		if(receivingPower && !powered) {
			world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_ALL | Block.NO_REDRAW);
			BlockState facingState = world.getBlockState(facingPos);
			if(canRotate(facingState)) {
				BlockState rotatedState = facingState.rotate(state.get(INVERTED) ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90);
				if(!rotatedState.equals(facingState) && rotatedState.canPlaceAt(world, facingPos)) {
					world.setBlockState(facingPos, rotatedState, Block.NOTIFY_ALL);
					world.playSound(null, pos, ModSoundEvents.BLOCK_ROTATOR_ROTATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
					shouldUpdateComparators = true;
				} else {
					world.playSound(null, pos, ModSoundEvents.BLOCK_ROTATOR_FAIL, SoundCategory.BLOCKS, 1.0f, 1.2f);
				}
			}
		} else if(!receivingPower && powered) {
			world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_ALL | Block.NO_REDRAW);
		}

		if(shouldUpdateComparators) {
			world.updateComparators(pos, this);
		}
	}

	public BlockPos getFacingPos(BlockState state, BlockPos pos) {
		return pos.offset(state.get(FACING));
	}

	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		BlockState facingState = world.getBlockState(getFacingPos(state, pos));

		return getComparatorValue(facingState);
	}

	public static boolean canRotate(BlockState state) {
		return !state.isIn(ModTags.Blocks.ROTATOR_BLACKLIST);
	}

	public static boolean canGetComparatorValue(BlockState state) {
		if (!canRotate(state)) return false;

		Property<?> rotationProperty = getRotationProperty(state);
		return rotationProperty != null && state.contains(rotationProperty);
	}

	public static int getComparatorValue(BlockState state) {
		if(canGetComparatorValue(state)) {
			Property<?> rotationProperty = getRotationProperty(state);

			if(rotationProperty instanceof DirectionProperty property) {
				return getComparatorValue(state.get(property));
			} else if(rotationProperty instanceof EnumProperty property) {
				if(state.get(property) instanceof Direction.Axis axis) {
					return getComparatorValue(axis);
				} else if(state.get(property) instanceof RailShape railShape) {
					return getComparatorValue(railShape);
				}
			} else if(rotationProperty instanceof IntProperty property) {
				return state.get(property);
			}
		}

		return 0;
	}

	public static Property<?> getRotationProperty(BlockState state) {
		for(Property<?> property : ROTATION_PROPERTIES) {
			if(state.contains(property)) return property;
		}
		return null;
	}

	public static int getComparatorValue(Direction direction) {
		return switch(direction) {
			case WEST -> 4;
			case NORTH -> 8;
			case EAST -> 12;
			default -> 0;
		};
	}

	public static int getComparatorValue(Direction.Axis axis) {
		return axis.equals(Direction.Axis.X) ? 4 : 0;
	}

	public static int getComparatorValue(RailShape railShape) {
		return switch(railShape) {
			case NORTH_SOUTH, ASCENDING_SOUTH -> 0;
			case EAST_WEST, ASCENDING_WEST -> 4;
			case ASCENDING_EAST -> 12;
			case ASCENDING_NORTH -> 8;
			case SOUTH_EAST -> 14;
			case SOUTH_WEST -> 2;
			case NORTH_WEST -> 6;
			case NORTH_EAST -> 10;
		};
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, INVERTED);
	}
}
