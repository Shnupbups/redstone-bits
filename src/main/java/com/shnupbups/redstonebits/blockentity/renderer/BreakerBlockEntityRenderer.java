package com.shnupbups.redstonebits.blockentity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;

@Environment(EnvType.CLIENT)
public class BreakerBlockEntityRenderer extends BlockEntityRenderer<BreakerBlockEntity> {
	
	public BreakerBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
		super(blockEntityRenderDispatcher);
	}
	
	@Override
	public void render(BreakerBlockEntity blockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		MinecraftClient.getInstance().worldRenderer.setBlockBreakingInfo(-1, blockEntity.getBreakPos(), blockEntity.getBreakPercentage() > 0 ? blockEntity.getBreakPercentage() / 10 : -1);
	}
}
