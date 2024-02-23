package com.shnupbups.redstonebits.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import com.shnupbups.redstonebits.RedstoneBits;

public class RBTags {
	public static class Blocks {
		public static final TagKey<Block> COPPER_BUTTONS = of("copper_buttons");
		public static final TagKey<Block> COPPER_PRESSURE_PLATES = of("copper_pressure_plates");
		public static final TagKey<Block> UNWAXED_COPPER_BUTTONS = of("unwaxed_copper_buttons");
		public static final TagKey<Block> UNWAXED_COPPER_PRESSURE_PLATES = of("unwaxed_copper_pressure_plates");
		public static final TagKey<Block> WAXED_COPPER_BUTTONS = of("waxed_copper_buttons");
		public static final TagKey<Block> WAXED_COPPER_PRESSURE_PLATES = of("waxed_copper_pressure_plates");

		public static final TagKey<Block> ROTATOR_BLACKLIST = of("rotator_blacklist");
		public static final TagKey<Block> BREAKER_BLACKLIST = of("breaker_blacklist");

		public static TagKey<Block> of(String id) {
			return RBTags.of(RegistryKeys.BLOCK, id);
		}
	}

	public static class Items {
		public static final TagKey<Item> COPPER_BUTTONS = of("copper_buttons");
		public static final TagKey<Item> COPPER_PRESSURE_PLATES = of("copper_pressure_plates");
		public static final TagKey<Item> UNWAXED_COPPER_BUTTONS = of("unwaxed_copper_buttons");
		public static final TagKey<Item> UNWAXED_COPPER_PRESSURE_PLATES = of("unwaxed_copper_pressure_plates");
		public static final TagKey<Item> WAXED_COPPER_BUTTONS = of("waxed_copper_buttons");
		public static final TagKey<Item> WAXED_COPPER_PRESSURE_PLATES = of("waxed_copper_pressure_plates");

		public static final TagKey<Item> BREAKER_TOOL_BLACKLIST = of("breaker_tool_blacklist");
		public static final TagKey<Item> PLACER_BLACKLIST = of("placer_blacklist");
		public static final TagKey<Item> ITEM_USER_BLACKLIST = of("item_user_blacklist");

		public static TagKey<Item> of(String id) {
			return RBTags.of(RegistryKeys.ITEM, id);
		}
	}

	public static <T> TagKey<T> of(RegistryKey<Registry<T>> registryKey, String id) {
		return TagKey.of(registryKey, RedstoneBits.id(id));
	}
}
