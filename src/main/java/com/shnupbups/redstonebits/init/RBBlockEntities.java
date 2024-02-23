package com.shnupbups.redstonebits.init;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;
import com.shnupbups.redstonebits.blockentity.ItemUserBlockEntity;
import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;
import com.shnupbups.redstonebits.blockentity.RedstoneGateBlockEntity;

public class RBBlockEntities {
	public static void init() {

	}

	public static final BlockEntityType<ItemUserBlockEntity> ITEM_USER = Registry.register(Registries.BLOCK_ENTITY_TYPE, RedstoneBits.id("item_user"), FabricBlockEntityTypeBuilder.create(ItemUserBlockEntity::new, RBBlocks.ITEM_USER).build(null));
	public static final BlockEntityType<PlacerBlockEntity> PLACER = Registry.register(Registries.BLOCK_ENTITY_TYPE, RedstoneBits.id("placer"), FabricBlockEntityTypeBuilder.create(PlacerBlockEntity::new, RBBlocks.PLACER).build(null));
	public static final BlockEntityType<BreakerBlockEntity> BREAKER = Registry.register(Registries.BLOCK_ENTITY_TYPE, RedstoneBits.id("breaker"), FabricBlockEntityTypeBuilder.create(BreakerBlockEntity::new, RBBlocks.BREAKER).build(null));
	public static final BlockEntityType<CheckerBlockEntity> CHECKER = Registry.register(Registries.BLOCK_ENTITY_TYPE, RedstoneBits.id("checker"), FabricBlockEntityTypeBuilder.create(CheckerBlockEntity::new, RBBlocks.CHECKER).build(null));
	public static final BlockEntityType<RedstoneGateBlockEntity> REDSTONE_GATE = Registry.register(Registries.BLOCK_ENTITY_TYPE, RedstoneBits.id("redstone_gate"), FabricBlockEntityTypeBuilder.create(RedstoneGateBlockEntity::new, RBBlocks.RESISTOR, RBBlocks.INVERTER).build(null));


}
