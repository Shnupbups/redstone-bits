package com.shnupbups.redstonebits.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.block.placer.BetterBlockPlacementDispenserBehavior;
import com.shnupbups.redstonebits.block.placer.BlacklistedDispenserBehavior;
import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;
import com.shnupbups.redstonebits.init.ModTags;

public class PlacerBlock extends DispenserBlock {
	private static final DispenserBehavior PLACE_BLOCK = new BetterBlockPlacementDispenserBehavior();
	private static final DispenserBehavior BLACKLISTED = new BlacklistedDispenserBehavior();

	public PlacerBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
		return stack.isIn(ModTags.Items.PLACER_BLACKLIST) ? BLACKLISTED : PLACE_BLOCK;
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
}
