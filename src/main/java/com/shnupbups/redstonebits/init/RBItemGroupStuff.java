package com.shnupbups.redstonebits.init;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class RBItemGroupStuff {
	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
			entries.addAfter(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
					RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
					RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
			entries.addAfter(Blocks.STONE_BUTTON,
					RBBlocks.WAXED_COPPER_BUTTON, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON,
					RBBlocks.WAXED_WEATHERED_COPPER_BUTTON, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
			entries.addAfter(Blocks.DROPPER,
					RBBlocks.BREAKER, RBBlocks.PLACER,RBBlocks.ITEM_USER);
			entries.addAfter(Blocks.OBSERVER,
					RBBlocks.CHECKER);
			entries.addAfter(Blocks.STICKY_PISTON,
					RBBlocks.ROTATOR);
			entries.addAfter(Blocks.COMPARATOR,
					RBBlocks.COUNTER, RBBlocks.ADDER, RBBlocks.RESISTOR, RBBlocks.INVERTER);
			entries.addAfter(Blocks.REDSTONE_LAMP,
					RBBlocks.ANALOG_REDSTONE_LAMP, RBBlocks.REDSTONE_DISPLAY, RBBlocks.REDSTONE_GLASS);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Blocks.COPPER_BLOCK,
					RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.COPPER_BUTTON);
			entries.addAfter(Blocks.EXPOSED_COPPER,
					RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.EXPOSED_COPPER_BUTTON);
			entries.addAfter(Blocks.WEATHERED_COPPER,
					RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WEATHERED_COPPER_BUTTON);
			entries.addAfter(Blocks.OXIDIZED_COPPER,
					RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.OXIDIZED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_COPPER_BLOCK,
					RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_EXPOSED_COPPER,
					RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_WEATHERED_COPPER,
					RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_WEATHERED_COPPER_BUTTON);
			entries.addAfter(Blocks.WAXED_OXIDIZED_COPPER,
					RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
			entries.addAfter(Blocks.REDSTONE_LAMP,
					RBBlocks.ANALOG_REDSTONE_LAMP);
			entries.addAfter(Blocks.TINTED_GLASS,
					RBBlocks.REDSTONE_GLASS);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
			entries.addAfter(Blocks.TINTED_GLASS,
					RBBlocks.REDSTONE_GLASS);
		});
	}
}
