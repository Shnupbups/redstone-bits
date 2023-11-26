package com.shnupbups.redstonebits.block;

import com.mojang.serialization.MapCodec;
import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.block.placer.BetterBlockPlacementDispenserBehavior;
import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;
import com.shnupbups.redstonebits.init.RBTags;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

public class PlacerBlock extends DispenserBlock {
	public static final MapCodec<PlacerBlock> CODEC = createCodec(PlacerBlock::new);

	private static final DispenserBehavior PLACE_BLOCK = new BetterBlockPlacementDispenserBehavior();

	public PlacerBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends PlacerBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
		return stack.isIn(RBTags.Items.PLACER_BLACKLIST) ? DispenserBehavior.NOOP : PLACE_BLOCK;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PlacerBlockEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof PlacerBlockEntity placerBlockEntity) {
				player.openHandledScreen(placerBlockEntity);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
		PlacerBlockEntity placerBlockEntity = world.getBlockEntity(pos, RBBlockEntities.PLACER).orElse(null);
		if (placerBlockEntity == null) {
			RedstoneBits.LOGGER.warn("Ignoring dispensing attempt for Placer without matching block entity at {}", pos);
		} else {
			BlockPointer blockPointer = new BlockPointer(world, pos, state, placerBlockEntity);
			int i = placerBlockEntity.chooseNonEmptySlot(world.random);
			if (i < 0) {
				world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0);
				world.emitGameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Emitter.of(placerBlockEntity.getCachedState()));
			} else {
				ItemStack itemStack = placerBlockEntity.getStack(i);
				DispenserBehavior dispenserBehavior = this.getBehaviorForItem(itemStack);
				if (dispenserBehavior != DispenserBehavior.NOOP) {
					placerBlockEntity.setStack(i, dispenserBehavior.dispense(blockPointer, itemStack));
				}
			}
		}
	}
}
