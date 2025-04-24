package com.shnupbups.redstonebits.datagen;

import java.util.concurrent.CompletableFuture;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlocks;
import com.shnupbups.redstonebits.init.RBTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class RBRecipeProvider extends FabricRecipeProvider {
	public RBRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
		return new RecipeGenerator(registries, exporter) {
			private final RegistryEntryLookup<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

			@Override
			public void generate() {
				RedstoneBits.LOGGER.info("Generating recipes...");

				offerPressurePlateRecipe(RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Items.COPPER_INGOT);
				offerSingleOutputShapelessRecipe(RBBlocks.COPPER_BUTTON, Items.COPPER_INGOT, null);

				offerWaxing(RBBlocks.COPPER_BUTTON, RBBlocks.WAXED_COPPER_BUTTON);
				offerWaxing(RBBlocks.EXPOSED_COPPER_BUTTON, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON);
				offerWaxing(RBBlocks.WEATHERED_COPPER_BUTTON, RBBlocks.WAXED_WEATHERED_COPPER_BUTTON);
				offerWaxing(RBBlocks.OXIDIZED_COPPER_BUTTON, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

				offerWaxing(RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
				offerWaxing(RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
				offerWaxing(RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
				offerWaxing(RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.COPPER_BUTTON, Blocks.COPPER_BLOCK, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_COPPER_BUTTON, Blocks.WAXED_COPPER_BLOCK, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_COPPER, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON, Blocks.WAXED_EXPOSED_COPPER, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_COPPER, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_WEATHERED_COPPER_BUTTON, Blocks.WAXED_WEATHERED_COPPER, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_COPPER, 9);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON, Blocks.WAXED_OXIDIZED_COPPER, 9);

				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_COPPER_BLOCK, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_EXPOSED_COPPER, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_WEATHERED_COPPER, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER, 5);
				offerStonecuttingRecipe(RecipeCategory.REDSTONE, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_OXIDIZED_COPPER, 5);

				offerDispenserLikeRecipe(RBBlocks.PLACER, Items.BRICKS);
				offerDispenserLikeRecipe(RBBlocks.BREAKER, ingredientFromTag(RBTags.Items.DECENT_TOOLS), Items.DIAMOND_PICKAXE);
				offerDispenserLikeRecipe(RBBlocks.UTILIZER, Items.ECHO_SHARD);

				RedstoneBits.LOGGER.info("Finished generating recipes!");
			}

			public void offerWaxing(ItemConvertible unwaxed, ItemConvertible waxed) {
				ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.REDSTONE, waxed).input(unwaxed).input(Items.HONEYCOMB).group(getItemPath(waxed)).criterion(hasItem(unwaxed), conditionsFromItem(unwaxed)).offerTo(exporter, convertBetween(waxed, Items.HONEYCOMB));
			}

			public void offerDispenserLikeRecipe(ItemConvertible output, Ingredient input, ItemConvertible criteria) {
				ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.REDSTONE, output)
						.pattern("ccc")
						.pattern("cic")
						.pattern("crc")
						.input('c', Items.COBBLESTONE)
						.input('r', Items.REDSTONE)
						.input('i', input)
						.criterion(hasItem(criteria), conditionsFromItem(criteria))
						.offerTo(exporter);
			}

			public void offerDispenserLikeRecipe(ItemConvertible output, ItemConvertible input) {
				offerDispenserLikeRecipe(output, Ingredient.ofItems(input), input);
			}
		};
	}

	@Override
	public String getName() {
		return "RS Bits Recipes";
	}
}
