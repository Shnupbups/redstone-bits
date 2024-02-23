package com.shnupbups.redstonebits.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlocks;
import com.shnupbups.redstonebits.init.RBTags;

public class RBBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public RBBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void configure(RegistryWrapper.WrapperLookup registries) {
		RedstoneBits.LOGGER.info("Generating block tags...");

		getOrCreateTagBuilder(RBTags.Blocks.UNWAXED_COPPER_BUTTONS).add(
				RBBlocks.COPPER_BUTTON,
				RBBlocks.EXPOSED_COPPER_BUTTON,
				RBBlocks.WEATHERED_COPPER_BUTTON,
				RBBlocks.OXIDIZED_COPPER_BUTTON
		);
		getOrCreateTagBuilder(RBTags.Blocks.WAXED_COPPER_BUTTONS).add(
				RBBlocks.WAXED_COPPER_BUTTON,
				RBBlocks.WAXED_EXPOSED_COPPER_BUTTON,
				RBBlocks.WAXED_WEATHERED_COPPER_BUTTON,
				RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON
		);
		getOrCreateTagBuilder(RBTags.Blocks.COPPER_BUTTONS).addTag(RBTags.Blocks.UNWAXED_COPPER_BUTTONS).addTag(RBTags.Blocks.WAXED_COPPER_BUTTONS);

		getOrCreateTagBuilder(RBTags.Blocks.UNWAXED_COPPER_PRESSURE_PLATES).add(
				RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE,
				RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE
		);
		getOrCreateTagBuilder(RBTags.Blocks.WAXED_COPPER_PRESSURE_PLATES).add(
				RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
				RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE
		);
		getOrCreateTagBuilder(RBTags.Blocks.COPPER_PRESSURE_PLATES).addTag(RBTags.Blocks.UNWAXED_COPPER_PRESSURE_PLATES).addTag(RBTags.Blocks.WAXED_COPPER_PRESSURE_PLATES);

		getOrCreateTagBuilder(RBTags.Blocks.ROTATOR_BLACKLIST);
		getOrCreateTagBuilder(RBTags.Blocks.BREAKER_BLACKLIST);

		getOrCreateTagBuilder(BlockTags.BUTTONS).addTag(RBTags.Blocks.COPPER_BUTTONS);
		getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).addTag(RBTags.Blocks.COPPER_PRESSURE_PLATES);
		getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).addTag(RBTags.Blocks.COPPER_BUTTONS).addTag(RBTags.Blocks.COPPER_PRESSURE_PLATES);

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
				RBBlocks.CHECKER,
				RBBlocks.BREAKER,
				RBBlocks.ITEM_USER,
				RBBlocks.PLACER,
				RBBlocks.ROTATOR
		).addTag(RBTags.Blocks.COPPER_BUTTONS).addTag(RBTags.Blocks.COPPER_PRESSURE_PLATES);

		getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(RBBlocks.REDSTONE_GLASS);

		RedstoneBits.LOGGER.info("Finished generating block tags!");
	}
}
