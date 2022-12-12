package com.shnupbups.redstonebits.init;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class ModItemGroupStuff {
	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
			entries.addAfter(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
					ModBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
					ModBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
			entries.addAfter(Blocks.STONE_BUTTON,
					ModBlocks.WAXED_COPPER_BUTTON, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON,
					ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
			entries.addAfter(Blocks.DROPPER,
					ModBlocks.BREAKER, ModBlocks.PLACER);
			entries.addAfter(Blocks.OBSERVER,
					ModBlocks.CHECKER);
			entries.addAfter(Blocks.STICKY_PISTON,
					ModBlocks.ROTATOR);
			entries.addAfter(Blocks.COMPARATOR,
					ModBlocks.COUNTER, ModBlocks.ADDER, ModBlocks.RESISTOR, ModBlocks.INVERTER);
			entries.addAfter(Blocks.REDSTONE_LAMP,
					ModBlocks.ANALOG_REDSTONE_LAMP, ModBlocks.REDSTONE_DISPLAY);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Blocks.COPPER_BLOCK,
					ModBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.COPPER_BUTTON);
			entries.addAfter(Blocks.EXPOSED_COPPER,
					ModBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.EXPOSED_COPPER_BUTTON);
			entries.addAfter(Blocks.WEATHERED_COPPER,
					ModBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WEATHERED_COPPER_BUTTON);
			entries.addAfter(Blocks.OXIDIZED_COPPER,
					ModBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.OXIDIZED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_COPPER_BLOCK,
					ModBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_EXPOSED_COPPER,
					ModBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_WEATHERED_COPPER,
					ModBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_OXIDIZED_COPPER,
					ModBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
			entries.addAfter(Blocks.REDSTONE_LAMP,
					ModBlocks.ANALOG_REDSTONE_LAMP);
		});
	}
}
