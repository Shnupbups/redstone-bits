package com.shnupbups.redstonebits.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shnupbups.redstonebits.mixin.ButtonBlockAccessor;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.OxidizableDoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class OxidizableCopperButtonBlock extends CopperButtonBlock implements Oxidizable {
	public static final MapCodec<OxidizableCopperButtonBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				Codec.intRange(1, 1024).fieldOf("ticks_to_stay_pressed").forGetter(block -> ((ButtonBlockAccessor)block).getPressTicks()),
				Oxidizable.OxidationLevel.CODEC.fieldOf("weathering_state").forGetter(OxidizableCopperButtonBlock::getDegradationLevel),
				createSettingsCodec()
			)
			.apply(instance, OxidizableCopperButtonBlock::new)
	);

	private final Oxidizable.OxidationLevel oxidationLevel;

	public OxidizableCopperButtonBlock(int pressTicks, Oxidizable.OxidationLevel oxidationLevel, Settings settings) {
		super(pressTicks, settings);
		this.oxidationLevel = oxidationLevel;
	}

	/*TODO: @Override
	public MapCodec<? extends OxidizableCopperButtonBlock> getCodec() {
		return CODEC;
	}*/

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	@Override
	public OxidationLevel getDegradationLevel() {
		return this.oxidationLevel;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isOf(Items.HONEYCOMB) && !state.get(POWERED))
			return ActionResult.PASS;
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
