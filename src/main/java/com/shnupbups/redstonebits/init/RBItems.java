package com.shnupbups.redstonebits.init;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.block.*;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class RBItems {
    public static final Item UTILIZER = register(RBBlocks.UTILIZER);
    public static final Item PLACER = register(RBBlocks.PLACER);
    public static final Item BREAKER = register(RBBlocks.BREAKER);

    public static final Item CHECKER = register(RBBlocks.CHECKER);

    public static final Item ROTATOR = register(RBBlocks.ROTATOR);

    public static final Item COUNTER = register(RBBlocks.COUNTER);
    public static final Item RESISTOR = register(RBBlocks.RESISTOR);
    public static final Item ADDER = register(RBBlocks.ADDER);
    public static final Item INVERTER = register(RBBlocks.INVERTER);

    public static final Item ANALOG_REDSTONE_LAMP = register(RBBlocks.ANALOG_REDSTONE_LAMP);
    public static final Item REDSTONE_DISPLAY = register(RBBlocks.REDSTONE_DISPLAY);

    public static final Item REDSTONE_GLASS = register(RBBlocks.REDSTONE_GLASS);

    public static final Item COPPER_BUTTON = register(RBBlocks.COPPER_BUTTON);
    public static final Item EXPOSED_COPPER_BUTTON = register(RBBlocks.EXPOSED_COPPER_BUTTON);
    public static final Item WEATHERED_COPPER_BUTTON = register(RBBlocks.WEATHERED_COPPER_BUTTON);
    public static final Item OXIDIZED_COPPER_BUTTON = register(RBBlocks.OXIDIZED_COPPER_BUTTON);

    public static final Item WAXED_COPPER_BUTTON = register(RBBlocks.WAXED_COPPER_BUTTON);
    public static final Item WAXED_EXPOSED_COPPER_BUTTON = register(RBBlocks.WAXED_EXPOSED_COPPER_BUTTON);
    public static final Item WAXED_WEATHERED_COPPER_BUTTON = register(RBBlocks.WAXED_WEATHERED_COPPER_BUTTON);
    public static final Item WAXED_OXIDIZED_COPPER_BUTTON = register(RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

    public static final Item MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
    public static final Item EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    public static final Item WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    public static final Item OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

    public static final Item WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    public static final Item WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    public static final Item WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    public static final Item WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = register(RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    
    public static Item register(Block block) {
        return Items.register(block);
    }

    public static void init() {};
}
