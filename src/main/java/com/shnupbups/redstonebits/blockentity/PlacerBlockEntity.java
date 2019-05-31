package com.shnupbups.redstonebits.blockentity;

import com.shnupbups.redstonebits.RedstoneBits;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class PlacerBlockEntity extends DispenserBlockEntity {
	public PlacerBlockEntity() {
		super(RedstoneBits.PLACER);
	}

	protected Component getContainerName() {
		return new TranslatableComponent("container.redstonebits.placer", new Object[0]);
	}
}
