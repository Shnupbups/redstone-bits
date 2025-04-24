package com.shnupbups.redstonebits.block.entity;

import com.shnupbups.redstonebits.block.BreakerBlock;
import com.shnupbups.redstonebits.block.breaker.BreakerFakePlayer;
import com.shnupbups.redstonebits.block.breaker.BreakerFakePlayerInteractionManager;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.init.RBBlockEntities;
import com.shnupbups.redstonebits.screen.handler.BreakerScreenHandler;
import com.shnupbups.redstonebits.init.RBTags;
import com.shnupbups.redstonebits.properties.RBProperties;
import org.jetbrains.annotations.Nullable;

public class BreakerBlockEntity extends LockableContainerBlockEntity {
	public static final String BREAK_PROGRESS_NBT_KEY = "break_progress";
	public static final String BREAK_STATE_NBT_KEY = "break_state";
	public static final String BREAK_TOOL_NBT_KEY = "break_tool";

	private final PropertyDelegate propertyDelegate = new BreakerPropertyDelegate();
	private DefaultedList<ItemStack> inventory;

	public BreakerFakePlayer fakePlayer;
	public BreakerFakePlayerInteractionManager interactionManager;

	public BreakerBlockEntity(BlockPos pos, BlockState state) {
		super(RBBlockEntities.BREAKER, pos, state);
		this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
		this.fakePlayer = getFakePlayer();
		this.interactionManager = getInteractionManager();
	}

	public static void serverTick(World world, BlockPos pos, BlockState state, BreakerBlockEntity blockEntity) {
		if (blockEntity.isBreaking()) {
			BreakerFakePlayer fakePlayer = blockEntity.getFakePlayer();
			BreakerFakePlayerInteractionManager interactionManager = blockEntity.getInteractionManager();
			if(fakePlayer == null || interactionManager == null) return;

			fakePlayer.setStackInHand(Hand.MAIN_HAND, blockEntity.getTool());
			interactionManager.updateBlockBreakingProgress(blockEntity.getBreakPos(), blockEntity.getBlockHitDirection());
		}

		if (blockEntity.isBreaking() != state.get(RBProperties.BREAKING)) {
			world.setBlockState(pos, state.with(RBProperties.BREAKING, blockEntity.isBreaking()));
			((ServerChunkManager) world.getChunkManager()).markForUpdate(pos);
			blockEntity.markDirty();
		}
	}

	public ItemStack getTool() {
		return inventory.getFirst();
	}

	public boolean startBreaking() {
		BreakerFakePlayer fakePlayer = getFakePlayer();
		BreakerFakePlayerInteractionManager interactionManager = getInteractionManager();
		if(fakePlayer == null || interactionManager == null) return false;

		fakePlayer.setStackInHand(Hand.MAIN_HAND, this.getTool());
		return interactionManager.attackBlock(this.getBreakPos(), this.getBlockHitDirection());
	}

	public void abortBreaking() {
		BreakerFakePlayer fakePlayer = getFakePlayer();
		BreakerFakePlayerInteractionManager interactionManager = getInteractionManager();
		if(fakePlayer == null || interactionManager == null) return;

		fakePlayer.setStackInHand(Hand.MAIN_HAND, this.getTool());
		interactionManager.cancelBlockBreaking();
	}

	public BlockPos getBreakPos() {
		if(this.getWorld() == null) return BlockPos.ORIGIN;
		return BreakerBlock.getBreakPos(this.getWorld(), this.getPos());
	}

	public Direction getFacing() {
		if(this.getWorld() == null) return Direction.NORTH;
		return BreakerBlock.getFacing(this.getWorld(), this.getPos());
	}

	public Direction getBlockHitDirection() {
		return this.getFacing().getOpposite();
	}

	public boolean isBreaking() {
		BreakerFakePlayerInteractionManager interactionManager = getInteractionManager();
		if(interactionManager != null) return interactionManager.isBreakingBlock();
		else return false;
	}

	public int getBreakProgress() {
		BreakerFakePlayerInteractionManager interactionManager = getInteractionManager();
		if(interactionManager != null) return interactionManager.getBlockBreakingProgress();
		else return 0;
	}

	public PropertyDelegate getPropertyDelegate() {
		return this.propertyDelegate;
	}

	@Nullable
	public BreakerFakePlayer getFakePlayer() {
		if(fakePlayer == null) {
			if (this.getWorld() instanceof ServerWorld serverWorld) fakePlayer = new BreakerFakePlayer(serverWorld);
		}
		return fakePlayer;
	}

	@Nullable BreakerFakePlayerInteractionManager getInteractionManager() {
		if(interactionManager == null) {
			if (this.getFakePlayer() != null) interactionManager = new BreakerFakePlayerInteractionManager(this.getFakePlayer());
		}
		return interactionManager;
	}

	@Override
	public Text getContainerName() {
		return Text.translatable("container.redstonebits.breaker");
	}

	@Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return inventory;
	}

	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new BreakerScreenHandler(syncId, playerInventory, this, this.getPropertyDelegate());
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return this.inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		Inventories.readNbt(nbt, this.inventory, registryLookup);
		//this.readBreakerNbt(nbt, registryLookup);
	}

	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		Inventories.writeNbt(nbt, this.inventory, registryLookup);
		//this.writeBreakerNbt(nbt, registryLookup);
	}

	@Override
	public ItemStack getStack(int slot) {
		return this.inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
		if (!stack.isEmpty()) {
			this.markDirty();
		}

		return stack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}

		this.markDirty();
	}

    @Override
	public void clear() {
		this.inventory.clear();
	}

	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	private class BreakerPropertyDelegate implements PropertyDelegate {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> BreakerBlockEntity.this.getBreakProgress();
				case 1 -> 0;//BreakerBlockEntity.this.getBreakTime();
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {

		}

		@Override
		public int size() {
			return 2;
		}
	}
}
