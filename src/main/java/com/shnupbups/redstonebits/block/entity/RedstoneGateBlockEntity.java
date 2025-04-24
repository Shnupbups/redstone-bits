package com.shnupbups.redstonebits.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.init.RBBlockEntities;

public class RedstoneGateBlockEntity extends BlockEntity {
	private int outputSignal;

	public RedstoneGateBlockEntity(BlockPos pos, BlockState state) {
		super(RBBlockEntities.REDSTONE_GATE, pos, state);
	}

	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		nbt.putInt("output_signal", this.outputSignal);
	}

	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.outputSignal = nbt.getInt("output_signal", 0);
	}

	public int getOutputSignal() {
		return this.outputSignal;
	}

	public void setOutputSignal(int outputSignal) {
		this.outputSignal = outputSignal;
	}
}
