package com.shnupbups.redstonebits.init;

import net.minecraft.block.*;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.block.*;

import java.util.function.Function;

public class RBBlocks {
	public static final Block UTILIZER = register("utilizer", UtilizerBlock::new, AbstractBlock.Settings.copy(Blocks.DISPENSER));
	public static final Block PLACER = register("placer", PlacerBlock::new, AbstractBlock.Settings.copy(Blocks.DISPENSER));
	public static final Block BREAKER = register("breaker", BreakerBlock::new, AbstractBlock.Settings.copy(Blocks.DISPENSER));

	public static final Block CHECKER = register("checker", CheckerBlock::new, AbstractBlock.Settings.copy(Blocks.OBSERVER));

	public static final Block ROTATOR = register("rotator", RotatorBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).strength(1.5F));

	public static final Block COUNTER = register("counter", CounterBlock::new, AbstractBlock.Settings.copy(Blocks.REPEATER));
	public static final Block RESISTOR = register("resistor", ResistorBlock::new, AbstractBlock.Settings.copy(Blocks.REPEATER));
	public static final Block ADDER = register("adder", AdderBlock::new, AbstractBlock.Settings.copy(Blocks.REPEATER));
	public static final Block INVERTER = register("inverter", InverterBlock::new, AbstractBlock.Settings.copy(Blocks.REPEATER));

	public static final Block ANALOG_REDSTONE_LAMP = register("analog_redstone_lamp", AnalogRedstoneReceiverBlock::new, AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP).luminance((state) -> state.get(AnalogRedstoneReceiverBlock.POWER)));
	public static final Block REDSTONE_DISPLAY = register("redstone_display", AnalogRedstoneReceiverBlock::new, AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP).luminance((state) -> 0));

	public static final Block REDSTONE_GLASS = register("redstone_glass", RedstoneGlassBlock::new, AbstractBlock.Settings.copy(Blocks.TINTED_GLASS).solidBlock(RedstoneGlassBlock::shouldBeOpaque).suffocates(RedstoneGlassBlock::shouldBeOpaque).blockVision(RedstoneGlassBlock::shouldBeOpaque));

	public static final Block COPPER_BUTTON = register("copper_button", (settings) -> new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().unaffectedPressTicks(), OxidationLevel.UNAFFECTED, settings), createCopperButtonSettings(OxidationLevel.UNAFFECTED));
	public static final Block EXPOSED_COPPER_BUTTON = register("exposed_copper_button", (settings) -> new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().exposedPressTicks(), OxidationLevel.EXPOSED, settings), createCopperButtonSettings(OxidationLevel.EXPOSED));
	public static final Block WEATHERED_COPPER_BUTTON = register("weathered_copper_button", (settings) -> new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().weatheredPressTicks(), OxidationLevel.WEATHERED, settings), createCopperButtonSettings(OxidationLevel.WEATHERED));
	public static final Block OXIDIZED_COPPER_BUTTON = register("oxidized_copper_button", (settings) -> new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().oxidizedPressTicks(), OxidationLevel.OXIDIZED, settings), createCopperButtonSettings(OxidationLevel.OXIDIZED));

	public static final Block WAXED_COPPER_BUTTON = register("waxed_copper_button", (settings) -> new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().unaffectedPressTicks(), settings), createCopperButtonSettings(OxidationLevel.UNAFFECTED));
	public static final Block WAXED_EXPOSED_COPPER_BUTTON = register("waxed_exposed_copper_button", (settings) -> new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().exposedPressTicks(), settings), createCopperButtonSettings(OxidationLevel.EXPOSED));
	public static final Block WAXED_WEATHERED_COPPER_BUTTON = register("waxed_weathered_copper_button", (settings) -> new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().weatheredPressTicks(), settings), createCopperButtonSettings(OxidationLevel.WEATHERED));
	public static final Block WAXED_OXIDIZED_COPPER_BUTTON = register("waxed_oxidized_copper_button", (settings) -> new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().oxidizedPressTicks(), settings), createCopperButtonSettings(OxidationLevel.OXIDIZED));

	public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = register("medium_weighted_pressure_plate", (settings) -> new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().unaffectedWeight(), OxidationLevel.UNAFFECTED, settings), createCopperPressurePlateSettings(OxidationLevel.UNAFFECTED));
	public static final Block EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("exposed_medium_weighted_pressure_plate", (settings) -> new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().exposedWeight(), OxidationLevel.EXPOSED, settings), createCopperPressurePlateSettings(OxidationLevel.EXPOSED));
	public static final Block WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("weathered_medium_weighted_pressure_plate", (settings) -> new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().weatheredWeight(), OxidationLevel.WEATHERED, settings), createCopperPressurePlateSettings(OxidationLevel.WEATHERED));
	public static final Block OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("oxidized_medium_weighted_pressure_plate", (settings) -> new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().oxidizedWeight(), OxidationLevel.OXIDIZED, settings), createCopperPressurePlateSettings(OxidationLevel.OXIDIZED));

	public static final Block WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("waxed_medium_weighted_pressure_plate", (settings) -> new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().unaffectedWeight(), BlockSetType.COPPER, settings), createCopperPressurePlateSettings(OxidationLevel.UNAFFECTED));
	public static final Block WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("waxed_exposed_medium_weighted_pressure_plate", (settings) -> new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().exposedWeight(), BlockSetType.COPPER, settings), createCopperPressurePlateSettings(OxidationLevel.EXPOSED));
	public static final Block WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("waxed_weathered_medium_weighted_pressure_plate", (settings) -> new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().weatheredWeight(), BlockSetType.COPPER, settings), createCopperPressurePlateSettings(OxidationLevel.WEATHERED));
	public static final Block WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register("waxed_oxidized_medium_weighted_pressure_plate", (settings) -> new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().oxidizedWeight(), BlockSetType.COPPER, settings), createCopperPressurePlateSettings(OxidationLevel.OXIDIZED));

	public static AbstractBlock.Settings createCopperButtonSettings(OxidationLevel level) {
		MapColor color = switch(level) {
			case UNAFFECTED -> MapColor.ORANGE;
			case EXPOSED -> MapColor.TERRACOTTA_LIGHT_GRAY;
			case WEATHERED -> MapColor.DARK_AQUA;
			case OXIDIZED -> MapColor.TEAL;
		};
		return AbstractBlock.Settings.create().noCollision().strength(0.5f).sounds(BlockSoundGroup.COPPER).pistonBehavior(PistonBehavior.DESTROY).mapColor(color);
	}

	public static AbstractBlock.Settings createCopperPressurePlateSettings(OxidationLevel level) {
		return createCopperButtonSettings(level).requiresTool().solid();
	}

	private static RegistryKey<Block> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.BLOCK, RedstoneBits.id(id));
	}

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return Blocks.register(keyOf(id), factory, settings);
	}

	public static <T extends Block> T register(String name, T block) {
		T b = Registry.register(Registries.BLOCK, RedstoneBits.id(name), block);
		BlockItem item = new BlockItem(b, new Item.Settings());
		item.appendBlocks(Item.BLOCK_ITEMS, item);
		Registry.register(Registries.ITEM, RedstoneBits.id(name), item);
		return b;
	}

	public static void init() {
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