package com.shnupbups.redstonebits.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.shnupbups.redstonebits.RedstoneBits;

public class RedstoneBitsDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		RedstoneBits.LOGGER.info("Starting Redstone Bits datagen...");

		dataGenerator.addProvider(RBModelProvider::new);
		dataGenerator.addProvider(RBRecipeProvider::new);
		dataGenerator.addProvider(RBBlockLootTableProvider::new);

		RBBlockTagProvider blockTagProvider = dataGenerator.addProvider(RBBlockTagProvider::new);
		dataGenerator.addProvider(new RBItemTagProvider(dataGenerator, blockTagProvider));
	}
}
