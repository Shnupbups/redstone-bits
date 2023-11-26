package com.shnupbups.redstonebits.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shnupbups.redstonebits.mixin.ButtonBlockAccessor;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperButtonBlock extends ButtonBlock {
	public static final MapCodec<CopperButtonBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				Codec.intRange(1, 1024).fieldOf("ticks_to_stay_pressed").forGetter(block -> ((ButtonBlockAccessor)block).getPressTicks()),
				createSettingsCodec()
			)
			.apply(instance, CopperButtonBlock::new)
	);

	public CopperButtonBlock(int pressTicks, Settings settings) {
		super(BlockSetType.COPPER, pressTicks, settings);
	}

	/*TODO: @Override
	public MapCodec<? extends CopperButtonBlock> getCodec() {
		return CODEC;
	}*/

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		super.onStateReplaced(state, world, pos, newState, moved);
		if (!moved && !state.isOf(newState.getBlock())) {
			if (newState.isIn(BlockTags.BUTTONS) && state.get(POWERED) && newState.get(POWERED)) {
				world.setBlockState(pos, newState.with(POWERED, false));
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isIn(ItemTags.AXES) && !state.get(POWERED)) return ActionResult.PASS;
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
