package com.shnupbups.redstonebits.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.init.ModBlockEntities;

public class PlacerBlockEntity extends DispenserBlockEntity {
	public PlacerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.PLACER, pos, state);
	}

	@Override
	protected Text getContainerName() {
		return Text.translatable("container.redstonebits.placer");
	}
}
