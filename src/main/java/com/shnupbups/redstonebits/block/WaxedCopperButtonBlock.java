package com.shnupbups.redstonebits.block;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaxedCopperButtonBlock extends ModButtonBlock {
	public WaxedCopperButtonBlock(Settings settings, BlockSetType blockSetType, int pressTicks) {
		super(settings, blockSetType, pressTicks, false);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isIn(ItemTags.AXES) && !state.get(POWERED)) return ActionResult.PASS;
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
