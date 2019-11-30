package com.shnupbups.redstonebits.blockentity;

import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import com.shnupbups.redstonebits.RedstoneBits;

public class PlacerBlockEntity extends DispenserBlockEntity {
	public PlacerBlockEntity() {
		super(RedstoneBits.PLACER);
	}
	
	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.redstonebits.placer");
	}
}
