package com.shnupbups.redstonebits;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.OxidizableBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.shnupbups.redstonebits.block.AnalogRedstoneLampBlock;
import com.shnupbups.redstonebits.block.BreakerBlock;
import com.shnupbups.redstonebits.block.CheckerBlock;
import com.shnupbups.redstonebits.block.CopperButtonBlock;
import com.shnupbups.redstonebits.block.CounterBlock;
import com.shnupbups.redstonebits.block.ModButtonBlock;
import com.shnupbups.redstonebits.block.PlacerBlock;
import com.shnupbups.redstonebits.block.ResistorBlock;

public class ModBlocks {
	
	public static final Block PLACER = new PlacerBlock(Block.Settings.copy(Blocks.DISPENSER));
	public static final Block BREAKER = register("breaker", new BreakerBlock(Block.Settings.copy(Blocks.DISPENSER)));
	public static final Block CHECKER = register("checker", new CheckerBlock(Block.Settings.copy(Blocks.OBSERVER)));
	public static final Block COUNTER = register("counter", new CounterBlock(Block.Settings.copy(Blocks.REPEATER)));
	public static final Block RESISTOR = register("resistor", new ResistorBlock(Block.Settings.copy(Blocks.REPEATER)));
	public static final Block ANALOG_REDSTONE_LAMP = register("analog_redstone_lamp", new AnalogRedstoneLampBlock(Block.Settings.copy(Blocks.REDSTONE_LAMP).luminance((state) -> state.get(AnalogRedstoneLampBlock.POWER))));
	public static final Block OXIDIZED_COPPER_BUTTON = register("oxidized_copper_button", new CopperButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.TEAL).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), 35));
	public static final Block WEATHERED_COPPER_BUTTON = register("weathered_copper_button", new CopperButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), Oxidizable.OxidizationLevel.WEATHERED, OXIDIZED_COPPER_BUTTON, 25));
	public static final Block EXPOSED_COPPER_BUTTON = register("exposed_copper_button", new CopperButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), Oxidizable.OxidizationLevel.EXPOSED, WEATHERED_COPPER_BUTTON, 15));
	public static final Block COPPER_BUTTON = register("copper_button", new CopperButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), Oxidizable.OxidizationLevel.UNAFFECTED, EXPOSED_COPPER_BUTTON, 5));
	public static final Block WAXED_WEATHERED_COPPER_BUTTON = register("waxed_weathered_copper_button", new ModButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), 25));
	public static final Block WAXED_EXPOSED_COPPER_BUTTON = register("waxed_exposed_copper_button", new ModButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), 15));
	public static final Block WAXED_COPPER_BUTTON = register("waxed_copper_button", new ModButtonBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER), 5));

	public static <T extends Block> T register(String name, T block) {
		T b = Registry.register(Registry.BLOCK, RedstoneBits.id(name), block);
		BlockItem item = new BlockItem(b, (new Item.Settings()).group(ItemGroup.REDSTONE));
		item.appendBlocks(Item.BLOCK_ITEMS, item);
		Registry.register(Registry.ITEM, RedstoneBits.id(name), item);
		return b;
	}

	public static void init() {
		register("placer", PLACER);
		register("breaker", BREAKER);
		register("checker", CHECKER);
		register("counter", COUNTER);
		register("analog_redstone_lamp", ANALOG_REDSTONE_LAMP);
		register("copper_button", COPPER_BUTTON);
		register("exposed_copper_button", EXPOSED_COPPER_BUTTON);
		register("weathered_copper_button", WEATHERED_COPPER_BUTTON);
		register("oxidized_copper_button", OXIDIZED_COPPER_BUTTON);
		register("waxed_copper_button", WAXED_COPPER_BUTTON);
		register("waxed_exposed_copper_button", WAXED_EXPOSED_COPPER_BUTTON);
		register("waxed_weathered_copper_button", WAXED_WEATHERED_COPPER_BUTTON);
	}
}