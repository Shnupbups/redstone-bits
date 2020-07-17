package com.shnupbups.redstonebits;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.blockentity.CheckerBlockEntity;
import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;

public class RedstoneBits implements ModInitializer {
	
	public static final BlockEntityType<PlacerBlockEntity> PLACER = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("placer"), BlockEntityType.Builder.create(PlacerBlockEntity::new, ModBlocks.PLACER).build(null));
	public static final BlockEntityType<BreakerBlockEntity> BREAKER = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("breaker"), BlockEntityType.Builder.create(BreakerBlockEntity::new, ModBlocks.BREAKER).build(null));
	public static final BlockEntityType<CheckerBlockEntity> CHECKER = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("checker"), BlockEntityType.Builder.create(CheckerBlockEntity::new, ModBlocks.CHECKER).build(null));
	public static final Identifier BREAKER_CONTAINER = id("breaker_container");
	
	public static Identifier id(String name) {
		return new Identifier("redstonebits", name);
	}
	
	@Override
	public void onInitialize() {
		ModBlocks.init();
		
		ContainerProviderRegistry.INSTANCE.registerFactory(BREAKER_CONTAINER, (syncId, identifier, player, buf) -> {
			BlockPos pos = buf.readBlockPos();
			return ((BreakerBlockEntity) player.getEntityWorld().getBlockEntity(pos)).createMenu(syncId, player.inventory, player);
		});
	}
}
