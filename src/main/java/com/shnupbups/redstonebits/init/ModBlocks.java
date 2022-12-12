package com.shnupbups.redstonebits.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.block.*;

public class ModBlocks {

	public static final Block PLACER = new PlacerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER));
	public static final Block BREAKER = new BreakerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER));

	public static final Block CHECKER = new CheckerBlock(FabricBlockSettings.copyOf(Blocks.OBSERVER));

	public static final Block ROTATOR = new RotatorBlock(FabricBlockSettings.of(Material.PISTON).strength(1.5f));

	public static final Block COUNTER = new CounterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block RESISTOR = new ResistorBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block ADDER = new AdderBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block INVERTER = new InverterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));

	public static final Block ANALOG_REDSTONE_LAMP = new AnalogRedstoneReceiverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP).luminance((state) -> state.get(AnalogRedstoneReceiverBlock.POWER)));
	public static final Block REDSTONE_DISPLAY = new AnalogRedstoneReceiverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP).luminance(0));

	private static final FabricBlockSettings UNAFFECTED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).requiresTool().noCollision().strength(0.5f).sounds(BlockSoundGroup.COPPER);
	private static final FabricBlockSettings EXPOSED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().noCollision().strength(0.5f).sounds(BlockSoundGroup.COPPER);
	private static final FabricBlockSettings WEATHERED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().noCollision().strength(0.5f).sounds(BlockSoundGroup.COPPER);
	private static final FabricBlockSettings OXIDIZED_COPPER_SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.TEAL).requiresTool().noCollision().strength(0.5f).sounds(BlockSoundGroup.COPPER);

	public static final Block COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, UNAFFECTED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().unaffectedPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);
	public static final Block EXPOSED_COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidationLevel.EXPOSED, EXPOSED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().exposedPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);
	public static final Block WEATHERED_COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidationLevel.WEATHERED, WEATHERED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().weatheredPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);
	public static final Block OXIDIZED_COPPER_BUTTON = new CopperButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, OXIDIZED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().oxidizedPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);

	public static final Block WAXED_COPPER_BUTTON = new WaxedCopperButtonBlock(UNAFFECTED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().unaffectedPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);
	public static final Block WAXED_EXPOSED_COPPER_BUTTON = new WaxedCopperButtonBlock(EXPOSED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().exposedPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);
	public static final Block WAXED_WEATHERED_COPPER_BUTTON = new WaxedCopperButtonBlock(WEATHERED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().weatheredPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);
	public static final Block WAXED_OXIDIZED_COPPER_BUTTON = new WaxedCopperButtonBlock(OXIDIZED_COPPER_SETTINGS, RedstoneBits.getConfig().buttonPressTimes().oxidizedPressTicks(), ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_OFF, ModSoundEvents.BLOCK_COPPER_BUTTON_CLICK_ON);

	public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidationLevel.UNAFFECTED, RedstoneBits.getConfig().pressurePlateWeights().unaffectedWeight(), UNAFFECTED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
	public static final Block EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidationLevel.EXPOSED, RedstoneBits.getConfig().pressurePlateWeights().exposedWeight(), EXPOSED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
	public static final Block WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidationLevel.WEATHERED, RedstoneBits.getConfig().pressurePlateWeights().weatheredWeight(), WEATHERED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
	public static final Block OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new CopperPressurePlateBlock(Oxidizable.OxidationLevel.OXIDIZED, RedstoneBits.getConfig().pressurePlateWeights().oxidizedWeight(), OXIDIZED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);

	public static final Block WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().unaffectedWeight(), UNAFFECTED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
	public static final Block WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().exposedWeight(), EXPOSED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
	public static final Block WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().weatheredWeight(), WEATHERED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
	public static final Block WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().oxidizedWeight(), OXIDIZED_COPPER_SETTINGS, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);

	public static <T extends Block> T register(String name, T block) {
		T b = Registry.register(Registries.BLOCK, RedstoneBits.id(name), block);
		BlockItem item = new BlockItem(b, new FabricItemSettings());
		item.appendBlocks(Item.BLOCK_ITEMS, item);
		Registry.register(Registries.ITEM, RedstoneBits.id(name), item);
		return b;
	}

	public static void init() {
		register("placer", PLACER);
		register("breaker", BREAKER);

		register("checker", CHECKER);

		register("rotator", ROTATOR);

		register("counter", COUNTER);
		register("resistor", RESISTOR);
		register("adder", ADDER);
		register("inverter", INVERTER);

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

		OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_BUTTON, EXPOSED_COPPER_BUTTON);
		OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_BUTTON, WEATHERED_COPPER_BUTTON);
		OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_BUTTON, OXIDIZED_COPPER_BUTTON);

		OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_BUTTON, WAXED_COPPER_BUTTON);
		OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_BUTTON, WAXED_EXPOSED_COPPER_BUTTON);
		OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_BUTTON, WAXED_WEATHERED_COPPER_BUTTON);
		OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_BUTTON, WAXED_OXIDIZED_COPPER_BUTTON);

		OxidizableBlocksRegistry.registerOxidizableBlockPair(MEDIUM_WEIGHTED_PRESSURE_PLATE, EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		OxidizableBlocksRegistry.registerWaxableBlockPair(MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
	}
}