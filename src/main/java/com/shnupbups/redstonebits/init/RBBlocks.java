package com.shnupbups.redstonebits.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.block.*;

public class RBBlocks {

	public static final Block PLACER = new PlacerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER));
	public static final Block BREAKER = new BreakerBlock(FabricBlockSettings.copyOf(Blocks.DISPENSER));

	public static final Block CHECKER = new CheckerBlock(FabricBlockSettings.copyOf(Blocks.OBSERVER));

	public static final Block ROTATOR = new RotatorBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).strength(1.5F));

	public static final Block COUNTER = new CounterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block RESISTOR = new ResistorBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block ADDER = new AdderBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
	public static final Block INVERTER = new InverterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));

	public static final Block ANALOG_REDSTONE_LAMP = new AnalogRedstoneReceiverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP).luminance((state) -> state.get(AnalogRedstoneReceiverBlock.POWER)));
	public static final Block REDSTONE_DISPLAY = new AnalogRedstoneReceiverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_LAMP).luminance(0));

	public static final Block REDSTONE_GLASS = new RedstoneGlassBlock(FabricBlockSettings.copyOf(Blocks.TINTED_GLASS).solidBlock(RedstoneGlassBlock::shouldBeOpaque).suffocates(RedstoneGlassBlock::shouldBeOpaque).blockVision(RedstoneGlassBlock::shouldBeOpaque));

	public static final Block COPPER_BUTTON = new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().unaffectedPressTicks(), OxidationLevel.UNAFFECTED, createCopperButtonSettings(OxidationLevel.UNAFFECTED));
	public static final Block EXPOSED_COPPER_BUTTON = new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().exposedPressTicks(), OxidationLevel.EXPOSED, createCopperButtonSettings(OxidationLevel.EXPOSED));
	public static final Block WEATHERED_COPPER_BUTTON = new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().weatheredPressTicks(), OxidationLevel.WEATHERED, createCopperButtonSettings(OxidationLevel.WEATHERED));
	public static final Block OXIDIZED_COPPER_BUTTON = new OxidizableCopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().oxidizedPressTicks(), OxidationLevel.OXIDIZED, createCopperButtonSettings(OxidationLevel.OXIDIZED));

	public static final Block WAXED_COPPER_BUTTON = new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().unaffectedPressTicks(), createCopperButtonSettings(OxidationLevel.UNAFFECTED));
	public static final Block WAXED_EXPOSED_COPPER_BUTTON = new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().exposedPressTicks(), createCopperButtonSettings(OxidationLevel.EXPOSED));
	public static final Block WAXED_WEATHERED_COPPER_BUTTON = new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().weatheredPressTicks(), createCopperButtonSettings(OxidationLevel.WEATHERED));
	public static final Block WAXED_OXIDIZED_COPPER_BUTTON = new CopperButtonBlock(RedstoneBits.getConfig().buttonPressTimes().oxidizedPressTicks(), createCopperButtonSettings(OxidationLevel.OXIDIZED));

	public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().unaffectedWeight(), OxidationLevel.UNAFFECTED, createCopperPressurePlateSettings(OxidationLevel.UNAFFECTED));
	public static final Block EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().exposedWeight(), OxidationLevel.EXPOSED, createCopperPressurePlateSettings(OxidationLevel.EXPOSED));
	public static final Block WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().weatheredWeight(), OxidationLevel.WEATHERED, createCopperPressurePlateSettings(OxidationLevel.WEATHERED));
	public static final Block OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new OxidizableWeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().oxidizedWeight(), OxidationLevel.OXIDIZED, createCopperPressurePlateSettings(OxidationLevel.OXIDIZED));

	public static final Block WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().unaffectedWeight(), BlockSetType.COPPER, createCopperPressurePlateSettings(OxidationLevel.UNAFFECTED));
	public static final Block WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().exposedWeight(), BlockSetType.COPPER, createCopperPressurePlateSettings(OxidationLevel.EXPOSED));
	public static final Block WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().weatheredWeight(), BlockSetType.COPPER, createCopperPressurePlateSettings(OxidationLevel.WEATHERED));
	public static final Block WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new WeightedPressurePlateBlock(RedstoneBits.getConfig().pressurePlateWeights().oxidizedWeight(), BlockSetType.COPPER, createCopperPressurePlateSettings(OxidationLevel.OXIDIZED));

	public static FabricBlockSettings createCopperButtonSettings(OxidationLevel level) {
		MapColor color = switch(level) {
			case UNAFFECTED -> MapColor.ORANGE;
			case EXPOSED -> MapColor.TERRACOTTA_LIGHT_GRAY;
			case WEATHERED -> MapColor.DARK_AQUA;
			case OXIDIZED -> MapColor.TEAL;
		};
		return FabricBlockSettings.create().noCollision().strength(0.5f).sounds(BlockSoundGroup.COPPER).pistonBehavior(PistonBehavior.DESTROY).mapColor(color);
	}

	public static FabricBlockSettings createCopperPressurePlateSettings(OxidationLevel level) {
		return createCopperButtonSettings(level).requiresTool().solid();
	}

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

		register("redstone_glass", REDSTONE_GLASS);

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