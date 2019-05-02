package com.shnupbups.redstonebits;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

	public static final PlacerBlock PLACER = new PlacerBlock(Block.Settings.copy(Blocks.DISPENSER));
	public static final BreakerBlock BREAKER = new BreakerBlock(Block.Settings.copy(Blocks.DISPENSER));
	public static final CheckerBlock CHECKER = new CheckerBlock(Block.Settings.copy(Blocks.OBSERVER));

	public static void registerBlock(Block block, String name) {
		Registry.register(Registry.BLOCK, new Identifier("redstonebits", name), block);
		BlockItem item = new BlockItem(block, (new Item.Settings()).itemGroup(ItemGroup.REDSTONE));
		item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
		Registry.register(Registry.ITEM, Registry.BLOCK.getId(block), item);
	}

	public static void init() {
		registerBlock(PLACER, "placer");
		registerBlock(BREAKER, "breaker");
		registerBlock(CHECKER, "checker");
	}
}