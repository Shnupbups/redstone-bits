package com.shnupbups.redstonebits;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

import com.shnupbups.oxidizelib.OxidizableFamily;
import com.shnupbups.oxidizelib.OxidizeLib;
import com.shnupbups.redstonebits.block.*;

public class ModBlocks {
	
	public static final Block PLACER = new PlacerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER));
	public static final Block BREAKER = new BreakerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER));
	public static final Block CHECKER = new CheckerBlock(FabricBlockSettings.copyOf(Blocks.OBSERVER));
	public static final Block COUNTER = new CounterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block RESISTOR = new ResistorBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block ANALOG_REDSTONE_LAMP = new AnalogRedstoneRecieverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP).luminance((state) -> state.get(AnalogRedstoneRecieverBlock.POWER)));
	public static final Block REDSTONE_DISPLAY = new AnalogRedstoneRecieverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP).luminance(0));
	
	private static final FabricBlockSettings UNAFFECTED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER);
	private static final FabricBlockSettings EXPOSED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER);
	private static final FabricBlockSettings WEATHERED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER);
	private static final FabricBlockSettings OXIDIZED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.TEAL).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER);
	
	public static final Block COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidizationLevel.UNAFFECTED, 5, UNAFFECTED_COPPER_SETTINGS);
	public static final Block EXPOSED_COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidizationLevel.EXPOSED, 15, EXPOSED_COPPER_SETTINGS);
	public static final Block WEATHERED_COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidizationLevel.WEATHERED, 25, WEATHERED_COPPER_SETTINGS);
	public static final Block OXIDIZED_COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidizationLevel.OXIDIZED, 35, OXIDIZED_COPPER_SETTINGS);
	public static final Block WAXED_COPPER_BUTTON = new WaxedCopperButtonBlock(5, UNAFFECTED_COPPER_SETTINGS);
	public static final Block WAXED_EXPOSED_COPPER_BUTTON = new WaxedCopperButtonBlock(15, EXPOSED_COPPER_SETTINGS);
	public static final Block WAXED_WEATHERED_COPPER_BUTTON = new WaxedCopperButtonBlock(25, WEATHERED_COPPER_SETTINGS);
	public static final Block WAXED_OXIDIZED_COPPER_BUTTON = new WaxedCopperButtonBlock(35, OXIDIZED_COPPER_SETTINGS);

	public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidizationLevel.UNAFFECTED, 30, UNAFFECTED_COPPER_SETTINGS);
	public static final Block EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidizationLevel.EXPOSED, 60, EXPOSED_COPPER_SETTINGS);
	public static final Block WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidizationLevel.WEATHERED, 90, WEATHERED_COPPER_SETTINGS);
	public static final Block OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidizationLevel.OXIDIZED, 120, OXIDIZED_COPPER_SETTINGS);
	public static final Block WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new ModWeightedPressurePlateBlock(30, UNAFFECTED_COPPER_SETTINGS);
	public static final Block WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new ModWeightedPressurePlateBlock(60, EXPOSED_COPPER_SETTINGS);
	public static final Block WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new ModWeightedPressurePlateBlock(90, WEATHERED_COPPER_SETTINGS);
	public static final Block WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new ModWeightedPressurePlateBlock(120, OXIDIZED_COPPER_SETTINGS);

	public static <T extends Block> T register(String name, T block) {
		T b = Registry.register(Registry.BLOCK, RedstoneBits.id(name), block);
		BlockItem item = new BlockItem(b, new FabricItemSettings().group(ItemGroup.REDSTONE));
		item.appendBlocks(Item.BLOCK_ITEMS, item);
		Registry.register(Registry.ITEM, RedstoneBits.id(name), item);
		return b;
	}

	public static void init() {
		register("placer", PLACER);
		register("breaker", BREAKER);
		register("checker", CHECKER);
		register("counter", COUNTER);
		register("resistor", RESISTOR);
		register("analog_redstone_lamp", ANALOG_REDSTONE_LAMP);
		register("redstone_display", REDSTONE_DISPLAY);
		
		register("copper_button", COPPER_BUTTON);
		register("exposed_copper_button", EXPOSED_COPPER_BUTTON);
		register("weathered_copper_button", WEATHERED_COPPER_BUTTON);
		register("oxidized_copper_button", OXIDIZED_COPPER_BUTTON);
		register("waxed_copper_button", WAXED_COPPER_BUTTON);
		register("waxed_exposed_copper_button", WAXED_EXPOSED_COPPER_BUTTON);
		register("waxed_weathered_copper_button", WAXED_WEATHERED_COPPER_BUTTON);
		register("waxed_oxidized_copper_button", WAXED_OXIDIZED_COPPER_BUTTON);

		register("medium_weighted_pressure_plate", MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("exposed_medium_weighted_pressure_plate", EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("weathered_medium_weighted_pressure_plate", WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("oxidized_medium_weighted_pressure_plate", OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("waxed_medium_weighted_pressure_plate", WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("waxed_exposed_medium_weighted_pressure_plate", WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("waxed_weathered_medium_weighted_pressure_plate", WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		register("waxed_oxidized_medium_weighted_pressure_plate", WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		OxidizeLib.registerOxidizableFamily(
				new OxidizableFamily.Builder()
				.unaffected(COPPER_BUTTON, WAXED_COPPER_BUTTON)
				.exposed(EXPOSED_COPPER_BUTTON, WAXED_EXPOSED_COPPER_BUTTON)
				.weathered(WEATHERED_COPPER_BUTTON, WAXED_WEATHERED_COPPER_BUTTON)
				.oxidized(OXIDIZED_COPPER_BUTTON, WAXED_OXIDIZED_COPPER_BUTTON)
				.build()
		);

		OxidizeLib.registerOxidizableFamily(
				new OxidizableFamily.Builder()
				.unaffected(MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
				.exposed(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
				.weathered(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
				.oxidized(OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
				.build()
		);
	}
}