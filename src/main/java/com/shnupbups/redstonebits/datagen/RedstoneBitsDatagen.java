package com.shnupbups.redstonebits.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.shnupbups.redstonebits.RedstoneBits;

public class RedstoneBitsDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		RedstoneBits.LOGGER.info("Starting Redstone Bits datagen...");

		FabricDataGenerator.Pack pack = dataGenerator.createPack();

		pack.addProvider(RBModelProvider::new);
		pack.addProvider(RBRecipeProvider::new);
		pack.addProvider(RBBlockLootTableProvider::new);

		RBBlockTagProvider blockTagProvider = pack.addProvider(RBBlockTagProvider::new);
		pack.addProvider((output, registriesFuture) -> new RBItemTagProvider(output, registriesFuture, blockTagProvider));
	}
}
