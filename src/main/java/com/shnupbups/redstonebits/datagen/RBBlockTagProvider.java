package com.shnupbups.redstonebits.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.ModBlocks;
import com.shnupbups.redstonebits.init.ModTags;

public class RBBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public RBBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void configure(RegistryWrapper.WrapperLookup registries) {
		RedstoneBits.LOGGER.info("Generating block tags...");

		getOrCreateTagBuilder(ModTags.Blocks.UNWAXED_COPPER_BUTTONS).add(
				ModBlocks.COPPER_BUTTON,
				ModBlocks.EXPOSED_COPPER_BUTTON,
				ModBlocks.WEATHERED_COPPER_BUTTON,
				ModBlocks.OXIDIZED_COPPER_BUTTON
		);
		getOrCreateTagBuilder(ModTags.Blocks.WAXED_COPPER_BUTTONS).add(
				ModBlocks.WAXED_COPPER_BUTTON,
				ModBlocks.WAXED_EXPOSED_COPPER_BUTTON,
				ModBlocks.WAXED_WEATHERED_COPPER_BUTTON,
				ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON
		);
		getOrCreateTagBuilder(ModTags.Blocks.COPPER_BUTTONS).addTag(ModTags.Blocks.UNWAXED_COPPER_BUTTONS).addTag(ModTags.Blocks.WAXED_COPPER_BUTTONS);

		getOrCreateTagBuilder(ModTags.Blocks.UNWAXED_COPPER_PRESSURE_PLATES).add(
				ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE,
				ModBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				ModBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				ModBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE
		);
		getOrCreateTagBuilder(ModTags.Blocks.WAXED_COPPER_PRESSURE_PLATES).add(
				ModBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				ModBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				ModBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				ModBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE
		);
		getOrCreateTagBuilder(ModTags.Blocks.COPPER_PRESSURE_PLATES).addTag(ModTags.Blocks.UNWAXED_COPPER_PRESSURE_PLATES).addTag(ModTags.Blocks.WAXED_COPPER_PRESSURE_PLATES);

		getOrCreateTagBuilder(ModTags.Blocks.ROTATOR_BLACKLIST);
		getOrCreateTagBuilder(ModTags.Blocks.BREAKER_BLACKLIST);

		getOrCreateTagBuilder(BlockTags.BUTTONS).addTag(ModTags.Blocks.COPPER_BUTTONS);
		getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).addTag(ModTags.Blocks.COPPER_PRESSURE_PLATES);
		getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).addTag(ModTags.Blocks.COPPER_BUTTONS).addTag(ModTags.Blocks.COPPER_PRESSURE_PLATES);

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
				ModBlocks.CHECKER,
				ModBlocks.BREAKER,
				ModBlocks.PLACER,
				ModBlocks.ROTATOR
		).addTag(ModTags.Blocks.COPPER_BUTTONS).addTag(ModTags.Blocks.COPPER_PRESSURE_PLATES);

		RedstoneBits.LOGGER.info("Finished generating block tags!");
	}
}
