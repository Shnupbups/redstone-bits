package com.shnupbups.redstonebits;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class BreakerBlockEntityRenderer extends BlockEntityRenderer<BreakerBlockEntity> {

	public void render(BreakerBlockEntity blockEntity_1, double double_1, double double_2, double double_3, float float_1, int int_1) {
		if(blockEntity_1.hasCustomName()) super.render(blockEntity_1, double_1, double_2, double_3, float_1, int_1);
		if(blockEntity_1.isBreaking()) {
			blockEntity_1.getWorld().setBlockBreakingProgress(MinecraftClient.getInstance().player.getEntityId(),blockEntity_1.getBreakPos(),blockEntity_1.getBreakPercentage()/10);
		}
	}

	public boolean method_3563(BreakerBlockEntity blockEntity_1) {
		return blockEntity_1.isBreaking();
	}
}
