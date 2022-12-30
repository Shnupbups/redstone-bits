package com.shnupbups.redstonebits.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlocks;

public class RBBlockLootTableProvider extends FabricBlockLootTableProvider {
	protected RBBlockLootTableProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate() {
		RedstoneBits.LOGGER.info("Generating block loot tables...");

		addDrop(RBBlocks.PLACER);
		addDrop(RBBlocks.BREAKER);

		addDrop(RBBlocks.CHECKER);

		addDrop(RBBlocks.ROTATOR);

		addDrop(RBBlocks.COUNTER);
		addDrop(RBBlocks.RESISTOR);
		addDrop(RBBlocks.ADDER);
		addDrop(RBBlocks.INVERTER);

		addDrop(RBBlocks.ANALOG_REDSTONE_LAMP);
		addDrop(RBBlocks.REDSTONE_DISPLAY);

		addDrop(RBBlocks.REDSTONE_GLASS);

		addDrop(RBBlocks.COPPER_BUTTON);
		addDrop(RBBlocks.EXPOSED_COPPER_BUTTON);
		addDrop(RBBlocks.WEATHERED_COPPER_BUTTON);
		addDrop(RBBlocks.OXIDIZED_COPPER_BUTTON);

		addDrop(RBBlocks.WAXED_COPPER_BUTTON);
		addDrop(RBBlocks.WAXED_EXPOSED_COPPER_BUTTON);
		addDrop(RBBlocks.WAXED_WEATHERED_COPPER_BUTTON);
		addDrop(RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

		addDrop(RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		addDrop(RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		addDrop(RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		RedstoneBits.LOGGER.info("Finished generating block loot tables!");
	}
}
