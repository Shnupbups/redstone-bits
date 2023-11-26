package com.shnupbups.redstonebits.mixin;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeightedPressurePlateBlock.class)
public interface WeightedPressurePlateBlockAccessor {
    @Accessor
    int getWeight();
}
