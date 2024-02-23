package com.shnupbups.redstonebits.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.init.RBBlockEntities;

public class ItemUserBlockEntity extends DispenserBlockEntity {
	public ItemUserBlockEntity(BlockPos pos, BlockState state) {
		super(RBBlockEntities.ITEM_USER, pos, state);
	}

	@Override
	protected Text getContainerName() {
		return Text.translatable("container.redstonebits.item_user");
	}
}
