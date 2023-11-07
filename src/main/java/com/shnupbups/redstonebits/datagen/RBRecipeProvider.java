package com.shnupbups.redstonebits.datagen;

import java.util.function.Consumer;

import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlocks;

public class RBRecipeProvider extends FabricRecipeProvider {
	public RBRecipeProvider(FabricDataOutput output) {
		super(output);
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

		RedstoneBits.LOGGER.info("Finished generating recipes!");
	}

	public static void offerWaxing(RecipeExporter exporter, ItemConvertible unwaxed, ItemConvertible waxed) {
		ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, waxed).input(unwaxed).input(Items.HONEYCOMB).group(RecipeProvider.getItemPath(waxed)).criterion(RecipeProvider.hasItem(unwaxed), RecipeProvider.conditionsFromItem(unwaxed)).offerTo(exporter, RecipeProvider.convertBetween(waxed, Items.HONEYCOMB));
	}
}
