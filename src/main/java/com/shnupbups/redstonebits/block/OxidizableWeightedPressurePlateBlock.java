package com.shnupbups.redstonebits.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shnupbups.redstonebits.mixin.WeightedPressurePlateBlockAccessor;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class OxidizableWeightedPressurePlateBlock extends WeightedPressurePlateBlock implements Oxidizable {
	public static final MapCodec<OxidizableWeightedPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				Codec.intRange(1, 1024).fieldOf("max_weight").forGetter(block -> ((WeightedPressurePlateBlockAccessor)block).getWeight()),
				Oxidizable.OxidationLevel.CODEC.fieldOf("weathering_state").forGetter(OxidizableWeightedPressurePlateBlock::getDegradationLevel),
				createSettingsCodec()
			)
			.apply(instance, OxidizableWeightedPressurePlateBlock::new)
	);

	private final Oxidizable.OxidationLevel oxidationLevel;

	public OxidizableWeightedPressurePlateBlock(int weight, Oxidizable.OxidationLevel oxidationLevel, Settings settings) {
		super(weight, BlockSetType.COPPER, settings);
		this.oxidationLevel = oxidationLevel;
	}

	/*TODO: @Override
	public MapCodec<? extends OxidizableWeightedPressurePlateBlock> getCodec() {
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
}
