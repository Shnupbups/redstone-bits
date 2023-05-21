package com.shnupbups.redstonebits.screen.handled;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.screen.handler.BreakerScreenHandler;

public class BreakerHandledScreen extends HandledScreen<BreakerScreenHandler> {

	private static final Identifier TEXTURE = RedstoneBits.id("textures/gui/container/breaker.png");

	private final BreakerScreenHandler handler;

	public BreakerHandledScreen(BreakerScreenHandler handler, PlayerInventory playerInventory, Text name) {
		super(handler, playerInventory, name);
		this.handler = handler;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexProgram);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
		if (!handler.getSlot(0).hasStack()) {
			DrawableHelper.drawTexture(matrices, x + 80, y + 35, this.backgroundWidth, 0, 16, 16);
		}
		if (handler.getBreakPercentage() > 0)
			DrawableHelper.drawTexture(matrices, x + 80, y + 53, this.backgroundWidth, 16 + ((int) Math.floor(handler.getBreakPercentage() / 10.0) * 16), 16, 16);
	}
}
