package com.shnupbups.redstonebits.block;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.blockentity.ItemUserBlockEntity;
import com.shnupbups.redstonebits.init.RBBlockEntities;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemUserBlock extends DispenserBlock {
	public static final MapCodec<ItemUserBlock> CODEC = createCodec(ItemUserBlock::new);

	public ItemUserBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends ItemUserBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ItemUserBlockEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ItemUserBlockEntity itemUserBlockEntity) {
				player.openHandledScreen(itemUserBlockEntity);
			}
		}
		return ActionResult.SUCCESS;
	}

	private ActionResult interactInternal(ServerWorld targetWorld, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
		BlockPos blockPos = hitResult.getBlockPos();
		ItemStack itemStack = player.getStackInHand(hand);

		if (!(player.shouldCancelInteraction() && !player.getMainHandStack().isEmpty())) {
			BlockState blockState = targetWorld.getBlockState(blockPos);

			ActionResult actionResult = blockState.onUse(targetWorld, player, hand, hitResult);
			if (actionResult.isAccepted()) {
				return actionResult;
			}
		}

		if (!itemStack.isEmpty() && !player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
			ItemUsageContext itemUsageContext = new ItemUsageContext(player, hand, hitResult);
			return itemStack.useOnBlock(itemUsageContext);
		} else {
			return ActionResult.PASS;
		}
	}

	@Override
	protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
		ItemUserBlockEntity itemUserBlockEntity = world.getBlockEntity(pos, RBBlockEntities.ITEM_USER).orElse(null);
		if (itemUserBlockEntity == null) {
			RedstoneBits.LOGGER.warn("Ignoring dispensing attempt for Item User without matching block entity at {}",
					pos);
		} else {
			int i = itemUserBlockEntity.chooseNonEmptySlot(world.random);
			if (i < 0) {
				i = 0;
			}
			Direction direction = world.getBlockState(pos).get(FACING);
			ItemStack itemStack = itemUserBlockEntity.getStack(i);
			FakePlayer fakePlayer = FakePlayer.get(world);
			try {
				BlockPos endPos = pos.offset(direction);
				fakePlayer.setPosition(pos.down().toCenterPos());
				fakePlayer.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, Vec3d.ofCenter(endPos));
				fakePlayer.setStackInHand(Hand.MAIN_HAND, itemStack);

				List<Entity> nearbyRealEntities = world.getEntitiesByClass(Entity.class,
						new Box(endPos),
						e -> e != fakePlayer);
				for (var targetEntity : nearbyRealEntities) {
					targetEntity.interact(fakePlayer, Hand.MAIN_HAND);
				}

				List<LivingEntity> nearbyEntities = world.getEntitiesByClass(LivingEntity.class,
						new Box(endPos),
						e -> e != fakePlayer);
				for (var targetEntity : nearbyEntities) {
					ActionResult entityUseResult = itemStack.useOnEntity(fakePlayer, targetEntity,
							Hand.MAIN_HAND);
					if (entityUseResult == ActionResult.SUCCESS) {
						itemStack.decrement(1);
					}
				}
				BlockHitResult blockHitResult = new BlockHitResult(Vec3d.ofCenter(pos),
						direction.getOpposite(), endPos, false);
				interactInternal(world, fakePlayer, Hand.MAIN_HAND, blockHitResult);

			} finally {
				itemUserBlockEntity.setStack(i, fakePlayer.getEquippedStack(EquipmentSlot.MAINHAND));
			}

		}
	}
}
