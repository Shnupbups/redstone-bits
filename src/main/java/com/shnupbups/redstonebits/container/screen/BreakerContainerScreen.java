package com.shnupbups.redstonebits.container.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.container.BreakerContainer;

public class BreakerContainerScreen extends HandledScreen<BreakerContainer> {
	
	private static final Identifier TEXTURE = RedstoneBits.id("textures/gui/container/breaker.png");
	
	private BreakerContainer container;
	
	public BreakerContainerScreen(BreakerContainer container) {
		super(container, container.playerInventory, ((BreakerBlockEntity) container.inventory).getContainerName());
		this.container = container;
	}
	
	@Override
	public void render(MatrixStack matrices, int int_1, int int_2, float float_1) {
		this.renderBackground(matrices);
		super.render(matrices, int_1, int_2, float_1);
		this.drawMouseoverTooltip(matrices, int_1, int_2);
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int int_1, int int_2) {
		String string_1 = this.title.getString();
		this.textRenderer.draw(matrices, string_1, (float) (this.backgroundWidth / 2 - this.textRenderer.getWidth(string_1) / 2), 6.0F, 4210752);
		this.textRenderer.draw(matrices, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.backgroundHeight - 96 + 2), 4210752);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float float_1, int int_1, int int_2) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(TEXTURE);
		int int_3 = (this.width - this.backgroundWidth) / 2;
		int int_4 = (this.height - this.backgroundHeight) / 2;
		this.drawTexture(matrices, int_3, int_4, 0, 0, this.backgroundWidth, this.backgroundHeight);
		if (!container.getSlot(0).hasStack()) {
			this.drawTexture(matrices, int_3 + 80, int_4 + 35, this.backgroundWidth, 0, 16, 16);
		}
		if (container.getBreakPercentage() > 0)
			this.drawTexture(matrices, int_3 + 80, int_4 + 53, this.backgroundWidth, 16 + ((int) Math.floor(container.getBreakPercentage() / 10) * 16), 16, 16);
	}
}
