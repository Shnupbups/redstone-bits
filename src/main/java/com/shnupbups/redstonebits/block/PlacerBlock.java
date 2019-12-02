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
	
	public PlacerBlock(Block.Settings block$Settings_1) {
		super(block$Settings_1);
	}
	
	@Override
	protected DispenserBehavior getBehaviorForItem(ItemStack itemStack_1) {
		return BEHAVIOR;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView blockView_1) {
		return new PlacerBlockEntity();
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity_1 = world.getBlockEntity(pos);
			if (blockEntity_1 instanceof PlacerBlockEntity) {
				player.openContainer((PlacerBlockEntity) blockEntity_1);
			}
		}
		return ActionResult.SUCCESS;
	}
}
