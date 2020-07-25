package com.shnupbups.redstonebits.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;

import com.shnupbups.redstonebits.block.AdvancedRedstoneConnector;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireMixin {
	@Inject(at = @At(value = "HEAD"), method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", cancellable = true)
	private static void connectsToMixin(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() instanceof AdvancedRedstoneConnector) {
			cir.setReturnValue(((AdvancedRedstoneConnector) state.getBlock()).connectsToRedstoneInDirection(state, dir));
		}
	}
}
