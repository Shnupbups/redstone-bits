package com.shnupbups.redstonebits.container.screen;

import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import com.shnupbups.redstonebits.blockentity.BreakerBlockEntity;
import com.shnupbups.redstonebits.container.BreakerContainer;

public class BreakerContainerScreen extends AbstractContainerScreen<BreakerContainer> {
	
	private static final Identifier TEXTURE = new Identifier("redstonebits", "textures/gui/container/breaker.png");
	
	private BreakerContainer container;
	
	public BreakerContainerScreen(BreakerContainer container) {
		super(container, container.playerInventory, ((BreakerBlockEntity) container.inventory).getContainerName());
		this.container = container;
	}
	
	@Override
	public void render(int int_1, int int_2, float float_1) {
		this.renderBackground();
		super.render(int_1, int_2, float_1);
		this.drawMouseoverTooltip(int_1, int_2);
	}
	
	@Override
	protected void drawForeground(int int_1, int int_2) {
		String string_1 = this.title.asFormattedString();
		this.font.draw(string_1, (float) (this.containerWidth / 2 - this.font.getStringWidth(string_1) / 2), 6.0F, 4210752);
		this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float) (this.containerHeight - 96 + 2), 4210752);
	}
	
	@Override
	protected void drawBackground(float float_1, int int_1, int int_2) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int int_3 = (this.width - this.containerWidth) / 2;
		int int_4 = (this.height - this.containerHeight) / 2;
		this.blit(int_3, int_4, 0, 0, this.containerWidth, this.containerHeight);
		if (!container.getSlot(0).hasStack()) {
			this.blit(int_3 + 80, int_4 + 35, this.containerWidth, 0, 16, 16);
		}
		if (container.getBreakPercentage() > 0)
			this.blit(int_3 + 80, int_4 + 53, this.containerWidth, 16 + ((int) Math.floor(container.getBreakPercentage() / 10) * 16), 16, 16);
	}
}
