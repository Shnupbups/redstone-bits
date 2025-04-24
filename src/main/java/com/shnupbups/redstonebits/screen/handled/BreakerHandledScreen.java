package com.shnupbups.redstonebits.screen.handled;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
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
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;
		context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
		if (!handler.getSlot(0).hasStack()) {
			context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 80, y + 35, this.backgroundWidth, 0, 16, 16, 256, 256);
		}
		RedstoneBits.LOGGER.info("prog: {}",handler.getBreakProgressSegmented());
		if (handler.getBreakProgressSegmented() > 0)
			context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 80, y + 53, this.backgroundWidth, 16 + (handler.getBreakProgressSegmented() * 16), 16, 16, 256, 256);
	}
}
