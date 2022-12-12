package com.shnupbups.redstonebits.datagen;

import java.util.function.Consumer;

import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.ModBlocks;

public class RBRecipeProvider extends FabricRecipeProvider {
	public RBRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		RedstoneBits.LOGGER.info("Generating recipes...");

		offerPressurePlateRecipe(exporter, ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Items.COPPER_INGOT);
		offerSingleOutputShapelessRecipe(exporter, ModBlocks.COPPER_BUTTON, Items.COPPER_INGOT, null);
		
		offerWaxing(exporter, ModBlocks.COPPER_BUTTON, ModBlocks.WAXED_COPPER_BUTTON);
		offerWaxing(exporter, ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
		offerWaxing(exporter, ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
		offerWaxing(exporter, ModBlocks.OXIDIZED_COPPER_BUTTON, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
		
		offerWaxing(exporter, ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		offerWaxing(exporter, ModBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		offerWaxing(exporter, ModBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		offerWaxing(exporter, ModBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.COPPER_BUTTON, Blocks.COPPER_BLOCK, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_COPPER_BUTTON, Blocks.WAXED_COPPER_BLOCK, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, Blocks.WAXED_EXPOSED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, Blocks.WAXED_WEATHERED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_COPPER, 9);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, Blocks.WAXED_OXIDIZED_COPPER, 9);

		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_COPPER_BLOCK, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_EXPOSED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_WEATHERED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER, 5);
		offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, ModBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WAXED_OXIDIZED_COPPER, 5);

		RedstoneBits.LOGGER.info("Finished generating recipes!");
	}

	public static void offerWaxing(Consumer<RecipeJsonProvider> exporter, ItemConvertible unwaxed, ItemConvertible waxed) {
		ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, waxed).input(unwaxed).input(Items.HONEYCOMB).group(RecipeProvider.getItemPath(waxed)).criterion(RecipeProvider.hasItem(unwaxed), RecipeProvider.conditionsFromItem(unwaxed)).offerTo(exporter, RecipeProvider.convertBetween(waxed, Items.HONEYCOMB));
	}
}
