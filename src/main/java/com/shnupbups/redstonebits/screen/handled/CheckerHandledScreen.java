package com.shnupbups.redstonebits.screen.handled;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.screen.handler.CheckerScreenHandler;

public class CheckerHandledScreen extends HandledScreen<CheckerScreenHandler> {

	private static final Identifier TEXTURE = RedstoneBits.id("textures/gui/container/checker.png");

	private final CheckerScreenHandler handler;

	public CheckerHandledScreen(CheckerScreenHandler handler, PlayerInventory playerInventory, Text name) {
		super(handler, playerInventory, name);
		this.handler = handler;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context);
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;
		context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 5; column++) {
				int slot = (column + row * 5);
				if (!handler.getSlot(slot).hasStack()) {
					int power = slot + 1;
					String slotText = (power < 10 ? "0" : "") + power;
					context.drawText(textRenderer, slotText, (x + 44 + (column * 18)) + 3, (y + 17 + (row * 18)) + 5, 0x192022, false);
				}
			}
		}
	}
}
