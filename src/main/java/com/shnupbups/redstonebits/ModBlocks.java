package com.shnupbups.redstonebits;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.shnupbups.redstonebits.block.BreakerBlock;
import com.shnupbups.redstonebits.block.CheckerBlock;
import com.shnupbups.redstonebits.block.PlacerBlock;

public class ModBlocks {
	
	public static final PlacerBlock PLACER = new PlacerBlock(Block.Settings.copy(Blocks.DISPENSER));
	public static final BreakerBlock BREAKER = new BreakerBlock(Block.Settings.copy(Blocks.DISPENSER));
	public static final CheckerBlock CHECKER = new CheckerBlock(Block.Settings.copy(Blocks.OBSERVER));
	
	public static void registerBlock(Block block, String name) {
		Registry.register(Registry.BLOCK, RedstoneBits.id(name), block);
		BlockItem item = new BlockItem(block, (new Item.Settings()).group(ItemGroup.REDSTONE));
		item.appendBlocks(Item.BLOCK_ITEMS, item);
		Registry.register(Registry.ITEM, RedstoneBits.id(name), item);
	}
	
	public static void init() {
		registerBlock(PLACER, "placer");
		registerBlock(BREAKER, "breaker");
		registerBlock(CHECKER, "checker");
	}
}