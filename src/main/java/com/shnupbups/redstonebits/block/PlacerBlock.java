package com.shnupbups.redstonebits.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;

public class PlacerBlock extends DispenserBlock {
	private static final DispenserBehavior BEHAVIOR = new BlockPlacementDispenserBehavior();
	
	public PlacerBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
		return BEHAVIOR;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return new PlacerBlockEntity();
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof PlacerBlockEntity) {
				player.openHandledScreen((PlacerBlockEntity) blockEntity);
			}
		}
		return ActionResult.SUCCESS;
	}
}
