package com.shnupbups.redstonebits.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.redstonebits.ModBlockEntities;

public class RedstoneGateBlockEntity extends BlockEntity {
	private int outputSignal;

	public RedstoneGateBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.REDSTONE_GATE, pos, state);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt("OutputSignal", this.outputSignal);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.outputSignal = nbt.getInt("OutputSignal");
	}

	public int getOutputSignal() {
		return this.outputSignal;
	}

	public void setOutputSignal(int outputSignal) {
		this.outputSignal = outputSignal;
	}
}
