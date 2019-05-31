package com.shnupbups.redstonebits.block;

import com.shnupbups.redstonebits.blockentity.PlacerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PlacerBlock extends DispenserBlock {
	private static final DispenserBehavior BEHAVIOR = new BlockPlacementDispenserBehavior();

	public PlacerBlock(Block.Settings block$Settings_1) {
		super(block$Settings_1);
	}

	protected DispenserBehavior getBehaviorForItem(ItemStack itemStack_1) {
		return BEHAVIOR;
	}

	public BlockEntity createBlockEntity(BlockView blockView_1) {
		return new PlacerBlockEntity();
	}

	public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		if (world_1.isClient) {
			return true;
		} else {
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof PlacerBlockEntity) {
				playerEntity_1.openContainer((PlacerBlockEntity)blockEntity_1);
			}
			return true;
		}
	}
}
