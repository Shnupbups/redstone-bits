package com.shnupbups.redstonebits;

import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;

public class PlacerBlockEntity extends DispenserBlockEntity {
	public PlacerBlockEntity() {
		super(RedstoneBits.PLACER);
	}

	protected TextComponent getContainerName() {
		return new TranslatableTextComponent("container.redstonebits.placer", new Object[0]);
	}
}
