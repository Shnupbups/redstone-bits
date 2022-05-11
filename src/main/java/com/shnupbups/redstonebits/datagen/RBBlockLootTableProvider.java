package com.shnupbups.redstonebits.datagen;

import java.io.IOException;

import net.minecraft.data.DataCache;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.ModBlocks;

public class RBBlockLootTableProvider extends FabricBlockLootTableProvider {
	protected RBBlockLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables() {
		RedstoneBits.LOGGER.info("Generating block loot tables...");

		addDrop(ModBlocks.PLACER);
		addDrop(ModBlocks.BREAKER);

		addDrop(ModBlocks.CHECKER);

		addDrop(ModBlocks.ROTATOR);

		addDrop(ModBlocks.COUNTER);
		addDrop(ModBlocks.RESISTOR);
		addDrop(ModBlocks.ADDER);
		addDrop(ModBlocks.INVERTER);

		addDrop(ModBlocks.ANALOG_REDSTONE_LAMP);
		addDrop(ModBlocks.REDSTONE_DISPLAY);

		addDrop(ModBlocks.COPPER_BUTTON);
		addDrop(ModBlocks.EXPOSED_COPPER_BUTTON);
		addDrop(ModBlocks.WEATHERED_COPPER_BUTTON);
		addDrop(ModBlocks.OXIDIZED_COPPER_BUTTON);

		addDrop(ModBlocks.WAXED_COPPER_BUTTON);
		addDrop(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
		addDrop(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
		addDrop(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

		addDrop(ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(ModBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(ModBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(ModBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		addDrop(ModBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(ModBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(ModBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(ModBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		RedstoneBits.LOGGER.info("Finished generating block loot tables!");
	}
}
