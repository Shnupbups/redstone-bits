package com.shnupbups.redstonebits.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.shnupbups.redstonebits.init.RBTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

public class RBRecipeProvider extends FabricRecipeProvider {
	public RBRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	public void generate(RecipeExporter exporter) {
		RedstoneBits.LOGGER.info("Generating recipes...");

		offerPressurePlateRecipe(exporter, RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Items.COPPER_INGOT);
		offerSingleOutputShapelessRecipe(exporter, RBBlocks.COPPER_BUTTON, Items.COPPER_INGOT, null);
		
		offerWaxing(exporter, RBBlocks.COPPER_BUTTON, RBBlocks.WAXED_COPPER_BUTTON);
		offerWaxing(exporter, RBBlocks.EXPOSED_COPPER_BUTTON, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON);
		offerWaxing(exporter, RBBlocks.WEATHERED_COPPER_BUTTON, RBBlocks.WAXED_WEATHERED_COPPER_BUTTON);
		offerWaxing(exporter, RBBlocks.OXIDIZED_COPPER_BUTTON, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
		
		offerWaxing(exporter, RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		offerWaxing(exporter, RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		offerWaxing(exporter, RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		offerWaxing(exporter, RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.COPPER_BUTTON, Blocks.COPPER_BLOCK, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_COPPER_BUTTON, Blocks.WAXED_COPPER_BLOCK, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON, Blocks.WAXED_EXPOSED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_WEATHERED_COPPER_BUTTON, Blocks.WAXED_WEATHERED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON, Blocks.WAXED_OXIDIZED_COPPER, 9);

		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_COPPER_BLOCK, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_EXPOSED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_WEATHERED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_OXIDIZED_COPPER, 5);

		offerDispenserLikeRecipe(exporter, RBBlocks.PLACER, Items.BRICKS);
		offerDispenserLikeRecipe(exporter, RBBlocks.BREAKER, Ingredient.fromTag(RBTags.Items.DECENT_TOOLS), Items.DIAMOND_PICKAXE);
		offerDispenserLikeRecipe(exporter, RBBlocks.ITEM_USER, Items.ECHO_SHARD);

		RedstoneBits.LOGGER.info("Finished generating recipes!");
	}

	public static void offerWaxing(RecipeExporter exporter, ItemConvertible unwaxed, ItemConvertible waxed) {
		ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, waxed).input(unwaxed).input(Items.HONEYCOMB).group(RecipeProvider.getItemPath(waxed)).criterion(RecipeProvider.hasItem(unwaxed), RecipeProvider.conditionsFromItem(unwaxed)).offerTo(exporter, RecipeProvider.convertBetween(waxed, Items.HONEYCOMB));
	}

	public static void offerDispenserLikeRecipe(RecipeExporter exporter, ItemConvertible output, Ingredient input, ItemConvertible criteria) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
			.pattern("ccc")
			.pattern("cic")
			.pattern("crc")
			.input('c', Items.COBBLESTONE)
			.input('r', Items.REDSTONE)
			.input('i', input)
			.criterion(RecipeProvider.hasItem(criteria), RecipeProvider.conditionsFromItem(criteria))
			.offerTo(exporter);
	}

	public static void offerDispenserLikeRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
		offerDispenserLikeRecipe(exporter, output, Ingredient.ofItems(input), input);
	}
}
