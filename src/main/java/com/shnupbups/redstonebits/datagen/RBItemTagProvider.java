package com.shnupbups.redstonebits.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBTags;

public class RBItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public RBItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
		super(output, registriesFuture, blockTagProvider);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup registries) {
		RedstoneBits.LOGGER.info("Generating item tags...");

		copy(RBTags.Blocks.UNWAXED_COPPER_BUTTONS, RBTags.Items.UNWAXED_COPPER_BUTTONS);
		copy(RBTags.Blocks.WAXED_COPPER_BUTTONS, RBTags.Items.WAXED_COPPER_BUTTONS);
		copy(RBTags.Blocks.COPPER_BUTTONS, RBTags.Items.COPPER_BUTTONS);

		copy(RBTags.Blocks.UNWAXED_COPPER_PRESSURE_PLATES, RBTags.Items.UNWAXED_COPPER_PRESSURE_PLATES);
		copy(RBTags.Blocks.WAXED_COPPER_PRESSURE_PLATES, RBTags.Items.WAXED_COPPER_PRESSURE_PLATES);
		copy(RBTags.Blocks.COPPER_PRESSURE_PLATES, RBTags.Items.COPPER_PRESSURE_PLATES);

		getOrCreateTagBuilder(RBTags.Items.BREAKER_TOOL_BLACKLIST);
		getOrCreateTagBuilder(RBTags.Items.PLACER_BLACKLIST);
		getOrCreateTagBuilder(RBTags.Items.ITEM_USER_BLACKLIST);

		getOrCreateTagBuilder(ItemTags.BUTTONS).addTag(RBTags.Items.COPPER_BUTTONS);

		RedstoneBits.LOGGER.info("Finished generating item tags!");
	}
}
