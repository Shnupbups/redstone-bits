package com.shnupbups.redstonebits.container.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.container.BreakerScreenHandler;

public class BreakerHandledScreen extends HandledScreen<BreakerScreenHandler> {
	
	private static final Identifier TEXTURE = RedstoneBits.id("textures/gui/container/breaker.png");
	
	private BreakerScreenHandler screenHandler;
	
	public BreakerHandledScreen(BreakerScreenHandler screenHandler) {
		super(screenHandler, screenHandler.playerInventory, ((BreakerBlockEntity) screenHandler.inventory).getContainerName());
		this.screenHandler = screenHandler;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		String string_1 = this.title.getString();
		this.textRenderer.draw(matrices, string_1, (float) (this.backgroundWidth / 2 - this.textRenderer.getWidth(string_1) / 2), 6.0F, 4210752);
		this.textRenderer.draw(matrices, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.backgroundHeight - 96 + 2), 4210752);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(TEXTURE);
		int int_3 = (this.width - this.backgroundWidth) / 2;
		int int_4 = (this.height - this.backgroundHeight) / 2;
		this.drawTexture(matrices, int_3, int_4, 0, 0, this.backgroundWidth, this.backgroundHeight);
		if (!screenHandler.getSlot(0).hasStack()) {
			this.drawTexture(matrices, int_3 + 80, int_4 + 35, this.backgroundWidth, 0, 16, 16);
		}
		if (screenHandler.getBreakPercentage() > 0)
			this.drawTexture(matrices, int_3 + 80, int_4 + 53, this.backgroundWidth, 16 + ((int) Math.floor(screenHandler.getBreakPercentage() / 10) * 16), 16, 16);
	}
}
