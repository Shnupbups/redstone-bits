package com.shnupbups.redstonebits.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shnupbups.redstonebits.mixin.ButtonBlockAccessor;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
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

	@Override
	public MapCodec<ButtonBlock> getCodec() {
		return CODEC.xmap(copperButtonBlock -> copperButtonBlock, buttonBlock -> (CopperButtonBlock) buttonBlock);
	}

	@Override
	public void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		super.onStateReplaced(state, world, pos, moved);
		BlockState newState = world.getBlockState(pos);
		if (!moved && !state.isOf(newState.getBlock())) {
			if (newState.isIn(BlockTags.BUTTONS) && state.get(POWERED) && newState.get(POWERED)) {
				world.setBlockState(pos, newState.with(POWERED, false));
			}
		}
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (stack.isIn(ItemTags.AXES) && !state.get(POWERED)) return ActionResult.SUCCESS;
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}
}
