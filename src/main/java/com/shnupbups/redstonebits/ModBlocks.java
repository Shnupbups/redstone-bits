package com.shnupbups.redstonebits;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.shnupbups.redstonebits.block.AnalogRedstoneLampBlock;
import com.shnupbups.redstonebits.block.BreakerBlock;
import com.shnupbups.redstonebits.block.CheckerBlock;
import com.shnupbups.redstonebits.block.CounterBlock;
import com.shnupbups.redstonebits.block.PlacerBlock;
import com.shnupbups.redstonebits.block.ResistorBlock;

public class ModBlocks {
	
	public static final Block PLACER = register("placer", new PlacerBlock(Block.Settings.copy(Blocks.DISPENSER)));
	public static final Block BREAKER = register("breaker", new BreakerBlock(Block.Settings.copy(Blocks.DISPENSER)));
	public static final Block CHECKER = register("checker", new CheckerBlock(Block.Settings.copy(Blocks.OBSERVER)));
	public static final Block COUNTER = register("counter", new CounterBlock(Block.Settings.copy(Blocks.REPEATER)));
	public static final Block RESISTOR = register("resistor", new ResistorBlock(Block.Settings.copy(Blocks.REPEATER)));
	public static final Block ANALOG_REDSTONE_LAMP = register("analog_redstone_lamp", new AnalogRedstoneLampBlock(Block.Settings.copy(Blocks.REDSTONE_LAMP).luminance((state) -> state.get(AnalogRedstoneLampBlock.POWER))));
	
	public static <T extends Block> T register(String name, T block) {
		T b = Registry.register(Registry.BLOCK, RedstoneBits.id(name), block);
		BlockItem item = new BlockItem(b, (new Item.Settings()).group(ItemGroup.REDSTONE));
		item.appendBlocks(Item.BLOCK_ITEMS, item);
		Registry.register(Registry.ITEM, RedstoneBits.id(name), item);
		return b;
	}

	public static void init() {

	}
}