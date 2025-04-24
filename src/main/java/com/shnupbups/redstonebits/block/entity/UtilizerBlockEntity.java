package com.shnupbups.redstonebits.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.init.RBBlockEntities;

public class UtilizerBlockEntity extends DispenserBlockEntity {
	public UtilizerBlockEntity(BlockPos pos, BlockState state) {
		super(RBBlockEntities.UTILIZER, pos, state);
	}

	@Override
	protected Text getContainerName() {
		return Text.translatable("container.redstonebits.utilizer");
	}
}
