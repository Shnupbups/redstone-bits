package com.shnupbups.redstonebits;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import com.mojang.datafixers.types.Type;

import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;
import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;

import java.util.Set;

public class ModBlockEntities {
	public static final BlockEntityType<PlacerBlockEntity> PLACER = Registry.register(Registry.BLOCK_ENTITY_TYPE, RedstoneBits.id("placer"), FabricBlockEntityTypeBuilder.create(PlacerBlockEntity::new, ModBlocks.PLACER).build(null));
	public static final BlockEntityType<BreakerBlockEntity> BREAKER = Registry.register(Registry.BLOCK_ENTITY_TYPE, RedstoneBits.id("breaker"), FabricBlockEntityTypeBuilder.create(BreakerBlockEntity::new, ModBlocks.BREAKER).build(null));
	public static final BlockEntityType<CheckerBlockEntity> CHECKER = Registry.register(Registry.BLOCK_ENTITY_TYPE, RedstoneBits.id("checker"), FabricBlockEntityTypeBuilder.create(CheckerBlockEntity::new, ModBlocks.CHECKER).build(null));

	public static void init() {

	}
}
