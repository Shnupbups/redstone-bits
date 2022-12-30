package com.shnupbups.redstonebits.datagen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.block.AdderOrCounterBlock;
import com.shnupbups.redstonebits.block.InverterBlock;
import com.shnupbups.redstonebits.block.ResistorBlock;
import com.shnupbups.redstonebits.init.RBBlocks;
import com.shnupbups.redstonebits.properties.ResistorMode;

public class RBModelProvider extends FabricModelProvider {
	public static final Model TEMPLATE_CHECKER = createModel("template_checker", TextureKey.PARTICLE, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.FRONT);
	public static final TextureKey SLAB = TextureKey.of("slab");
	public static final TextureKey LOCK = TextureKey.of("lock");
	public static final Model TEMPLATE_ADDER_OR_COUNTER = createRedstoneGateModel("template_adder_or_counter", false);
	public static final Model TEMPLATE_ADDER_OR_COUNTER_LOCKED = createRedstoneGateModel("template_adder_or_counter_locked", true);
	public static final Model TEMPLATE_RESISTOR_HALVE = createRedstoneGateModel("template_resistor_halve", false);
	public static final Model TEMPLATE_RESISTOR_HALVE_LOCKED = createRedstoneGateModel("template_resistor_halve_locked", true);
	public static final Model TEMPLATE_RESISTOR_THIRD = createRedstoneGateModel("template_resistor_third", false);
	public static final Model TEMPLATE_RESISTOR_THIRD_LOCKED = createRedstoneGateModel("template_resistor_third_locked", true);
	public static final Model TEMPLATE_RESISTOR_ONE_POINT_FIVE = createRedstoneGateModel("template_resistor_one_point_five", false);
	public static final Model TEMPLATE_RESISTOR_ONE_POINT_FIVE_LOCKED = createRedstoneGateModel("template_resistor_one_point_five_locked", true);
	public static final Model TEMPLATE_INVERTER = createRedstoneGateModel("template_inverter", false);
	public static final Model TEMPLATE_INVERTER_LOCKED = createRedstoneGateModel("template_inverter_locked", true);

	public RBModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
		RedstoneBits.LOGGER.info("Generating blockstate models...");

		registerDispenserLikeOrientable(generator, RBBlocks.PLACER);
		registerDispenserLikeOrientable(generator, RBBlocks.BREAKER);

		registerChecker(generator);
		registerRotator(generator);

		registerAdderOrCounter(generator, RBBlocks.ADDER);
		registerAdderOrCounter(generator, RBBlocks.COUNTER);
		registerResistor(generator);
		registerInverter(generator);

		registerPressurePlate(generator, RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK);
		registerPressurePlate(generator, RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER);
		registerPressurePlate(generator, RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER);
		registerPressurePlate(generator, RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER);
		registerPressurePlate(generator, RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK);
		registerPressurePlate(generator, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER);
		registerPressurePlate(generator, RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER);
		registerPressurePlate(generator, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER);

		registerButton(generator, RBBlocks.COPPER_BUTTON, Blocks.COPPER_BLOCK);
		registerButton(generator, RBBlocks.EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_COPPER);
		registerButton(generator, RBBlocks.WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_COPPER);
		registerButton(generator, RBBlocks.OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_COPPER);
		registerButton(generator, RBBlocks.WAXED_COPPER_BUTTON, Blocks.COPPER_BLOCK);
		registerButton(generator, RBBlocks.WAXED_EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_COPPER);
		registerButton(generator, RBBlocks.WAXED_WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_COPPER);
		registerButton(generator, RBBlocks.WAXED_OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_COPPER);

		registerAnalogLamp(generator);
		registerDisplay(generator);

		registerRedstoneGlass(generator);

		RedstoneBits.LOGGER.info("Finished generating blockstate models!");
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		RedstoneBits.LOGGER.info("Generating item models...");

		// there ain't nothin' here, chief!

		RedstoneBits.LOGGER.info("Finished generating item models!");
	}

	public void registerFlatItemModel(BlockStateModelGenerator generator, Block block) {
		generator.registerItemModel(block.asItem());
	}

	public void registerParentedItemModel(BlockStateModelGenerator generator, Block block) {
		generator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
	}

	public void registerAnalogLamp(BlockStateModelGenerator generator) {
		Block block = RBBlocks.ANALOG_REDSTONE_LAMP;

		TextureMap offTextureMap = TextureMap.all(Blocks.REDSTONE_LAMP);
		TextureMap onTextureMap = TextureMap.all(TextureMap.getSubId(Blocks.REDSTONE_LAMP, "_on"));
		TextureMap mediumTextureMap = TextureMap.all(TextureMap.getSubId(block, "_medium"));

		Identifier offModelId = Models.CUBE_ALL.upload(block, offTextureMap, generator.modelCollector);
		Identifier onModelId = Models.CUBE_ALL.upload(block, "_on", onTextureMap, generator.modelCollector);
		Identifier mediumModelId = Models.CUBE_ALL.upload(block, "_medium", mediumTextureMap, generator.modelCollector);

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(createValueBasedModelMap(Properties.POWER, (power) -> power == 0 ? offModelId : power == 15 ? onModelId : mediumModelId)));

		registerParentedItemModel(generator, block);
	}

	public void registerDisplay(BlockStateModelGenerator generator) {
		Block block = RBBlocks.REDSTONE_DISPLAY;

		Function<Integer, Identifier> textureMapper = (power) -> RedstoneBits.id("block/display_"+(power < 10 ? "0":"")+power);

		registerFullAnalogBlock(generator, block, textureMapper);
	}
	
	public void registerRedstoneGlass(BlockStateModelGenerator generator) {
		Block block = RBBlocks.REDSTONE_GLASS;

		TextureMap offTextureMap = TextureMap.all(TextureMap.getSubId(block, "_off"));
		TextureMap onTextureMap = TextureMap.all(TextureMap.getSubId(block, "_on"));

		Identifier offModelId = Models.CUBE_ALL.upload(block, offTextureMap, generator.modelCollector);
		Identifier onModelId = Models.CUBE_ALL.upload(block, "_on", onTextureMap, generator.modelCollector);

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(createValueBasedModelMap(Properties.POWER, (power) -> power == 0 ? offModelId : onModelId)));

		registerParentedItemModel(generator, block);
	}

	public void registerDispenserLikeOrientable(BlockStateModelGenerator generator, Block block) {
		generator.registerDispenserLikeOrientable(block);

		registerParentedItemModel(generator, block);
	}

	private void registerChecker(BlockStateModelGenerator generator) {
		Block block = RBBlocks.CHECKER;

		TextureMap baseTextureMap = new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(Blocks.OBSERVER, "_top")).put(TextureKey.SIDE, TextureMap.getSubId(Blocks.OBSERVER, "_side")).put(TextureKey.FRONT, TextureMap.getSubId(block, "_front")).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_front"));
		TextureMap offTextureMap = baseTextureMap.copyAndAdd(TextureKey.BOTTOM, TextureMap.getSubId(Blocks.OBSERVER, "_back"));
		TextureMap onTextureMap = baseTextureMap.copyAndAdd(TextureKey.BOTTOM, TextureMap.getSubId(Blocks.OBSERVER, "_back_on"));

		Identifier offModelId = ModelIds.getBlockModelId(block);
		Identifier onModelId = ModelIds.getBlockSubModelId(block, "_on");

		TEMPLATE_CHECKER.upload(RBBlocks.CHECKER, offTextureMap, generator.modelCollector);
		TEMPLATE_CHECKER.upload(RBBlocks.CHECKER, "_on", onTextureMap, generator.modelCollector);

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createValueFencedModelMap(Properties.POWER, 1, onModelId, offModelId)).coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates()));

		generator.registerParentedItemModel(RBBlocks.CHECKER, offModelId);
	}

	private void registerRotator(BlockStateModelGenerator generator) {
		Block block = RBBlocks.ROTATOR;

		TextureMap baseTextureMap = new TextureMap().put(TextureKey.BOTTOM, TextureMap.getSubId(Blocks.PISTON, "_bottom")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top"));
		TextureMap regularTextureMap = baseTextureMap.copyAndAdd(TextureKey.SIDE, TextureMap.getSubId(block, "_side"));
		TextureMap invertedTextureMap = baseTextureMap.copyAndAdd(TextureKey.SIDE, TextureMap.getSubId(block, "_side_inverted"));

		Identifier regularModelId = Models.CUBE_BOTTOM_TOP.upload(block, regularTextureMap, generator.modelCollector);
		Identifier invertedModelId = Models.CUBE_BOTTOM_TOP.upload(block, "_inverted", invertedTextureMap, generator.modelCollector);

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.INVERTED, invertedModelId, regularModelId)).coordinate(createUpDefaultRotationStates()));

		generator.registerParentedItemModel(block, regularModelId);
	}
	
	public void registerButton(BlockStateModelGenerator generator, Block button, Block textureSource) {
		TextureMap textureMap = TextureMap.texture(textureSource);

		Identifier buttonModelId = Models.BUTTON.upload(button, textureMap, generator.modelCollector);
		Identifier buttonPressedModelId = Models.BUTTON_PRESSED.upload(button, textureMap, generator.modelCollector);

		generator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(button, buttonModelId, buttonPressedModelId));

		Identifier buttonInventoryModelId = Models.BUTTON_INVENTORY.upload(button, textureMap, generator.modelCollector);
		generator.registerParentedItemModel(button, buttonInventoryModelId);
	}

	public void registerPressurePlate(BlockStateModelGenerator generator, Block pressurePlate, Block textureSource)  {
		generator.registerPressurePlate(pressurePlate, textureSource);

		registerParentedItemModel(generator, pressurePlate);
	}

	public void registerAdderOrCounter(BlockStateModelGenerator generator, Block block)  {
		BiFunction<Integer, Boolean, TextureMap> unlockedTextureMapper = (power, backwards) -> new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, "_"+power)).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_"+power)).put(SLAB, TextureMap.getId(Blocks.SMOOTH_STONE)).put(TextureKey.TORCH, getRedstoneTorchTextureId(backwards));
		BiFunction<Integer, Boolean, TextureMap> lockedTextureMapper = (power, backwards) -> unlockedTextureMapper.apply(power, backwards).copyAndAdd(LOCK, TextureMap.getId(Blocks.BEDROCK));

		for(int power = 0; power <= 15; power++) {
			TEMPLATE_ADDER_OR_COUNTER.upload(block, "_"+power, unlockedTextureMapper.apply(power, false), generator.modelCollector);
			TEMPLATE_ADDER_OR_COUNTER.upload(block, "_backwards_"+power, unlockedTextureMapper.apply(power, true), generator.modelCollector);
			TEMPLATE_ADDER_OR_COUNTER_LOCKED.upload(block, "_locked_"+power, lockedTextureMapper.apply(power, false), generator.modelCollector);
			TEMPLATE_ADDER_OR_COUNTER_LOCKED.upload(block, "_backwards_locked_"+power, lockedTextureMapper.apply(power, true), generator.modelCollector);
		}

		BiFunction<Integer, Boolean, Identifier> unlockedModelIdMapper = (power, backwards) -> TextureMap.getSubId(block, (backwards ? "_backwards_" : "_")+power);
		BiFunction<Integer, Boolean, Identifier> lockedModelIdMapper = (power, backwards) -> TextureMap.getSubId(block, (backwards ? "_backwards" : "")+"_locked_"+power);

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(AdderOrCounterBlock.POWER, AdderOrCounterBlock.BACKWARDS, AdderOrCounterBlock.LOCKED, AdderOrCounterBlock.FACING).register((power, backwards, locked, facing) -> BlockStateVariant.create().put(VariantSettings.MODEL, (locked ? lockedModelIdMapper : unlockedModelIdMapper).apply(power, backwards)).put(VariantSettings.Y, getRotationForGateFacing(facing)))));

		registerFlatItemModel(generator, block);
	}

	public void registerResistor(BlockStateModelGenerator generator) {
		Block block = RBBlocks.RESISTOR;

		Function<Boolean, TextureMap> unlockedTextureMapper = (powered) -> new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, "_"+getOffOrOn(powered))).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_"+getOffOrOn(powered))).put(SLAB, TextureMap.getId(Blocks.SMOOTH_STONE)).put(TextureKey.TORCH, getRedstoneTorchTextureId(powered));
		Function<Boolean, TextureMap> lockedTextureMapper = (powered) -> unlockedTextureMapper.apply(powered).copyAndAdd(LOCK, TextureMap.getId(Blocks.BEDROCK));

		uploadBooleanGateModelTextures(generator, block, "_halve", TEMPLATE_RESISTOR_HALVE, TEMPLATE_RESISTOR_HALVE_LOCKED, unlockedTextureMapper, lockedTextureMapper);
		uploadBooleanGateModelTextures(generator, block, "_third", TEMPLATE_RESISTOR_THIRD, TEMPLATE_RESISTOR_THIRD_LOCKED, unlockedTextureMapper, lockedTextureMapper);
		uploadBooleanGateModelTextures(generator, block, "_one_point_five", TEMPLATE_RESISTOR_ONE_POINT_FIVE, TEMPLATE_RESISTOR_ONE_POINT_FIVE_LOCKED, unlockedTextureMapper, lockedTextureMapper);

		BiFunction<Boolean, ResistorMode, Identifier> unlockedModelIdMapper = (powered, mode) -> TextureMap.getSubId(block, "_"+mode.asString()+(powered ? "_on" : ""));
		BiFunction<Boolean, ResistorMode, Identifier> lockedModelIdMapper = (powered, mode) -> TextureMap.getSubId(block, "_"+mode.asString()+"_locked"+(powered ? "_on" : ""));

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(ResistorBlock.POWERED, ResistorBlock.MODE, ResistorBlock.LOCKED, ResistorBlock.FACING).register((powered, mode, locked, facing) -> BlockStateVariant.create().put(VariantSettings.MODEL, (locked ? lockedModelIdMapper : unlockedModelIdMapper).apply(powered, mode)).put(VariantSettings.Y, getRotationForGateFacing(facing)))));

		registerFlatItemModel(generator, block);
	}

	public void registerInverter(BlockStateModelGenerator generator) {
		Block block = RBBlocks.INVERTER;

		BiFunction<Boolean, Boolean, TextureMap> unlockedTextureMapper = (powered, noInverting) -> new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, (noInverting ? "_no_inverting_" : "_")+getOffOrOn(powered))).put(TextureKey.PARTICLE, TextureMap.getSubId(block, (noInverting ? "_no_inverting_" : "_")+getOffOrOn(powered))).put(SLAB, TextureMap.getId(Blocks.SMOOTH_STONE)).put(TextureKey.TORCH, getRedstoneTorchTextureId(powered == noInverting));
		BiFunction<Boolean, Boolean, TextureMap> lockedTextureMapper = (powered, noInverting) -> unlockedTextureMapper.apply(powered, noInverting).copyAndAdd(LOCK, TextureMap.getId(Blocks.BEDROCK));

		uploadBooleanGateModelTextures(generator, block, "", TEMPLATE_INVERTER, TEMPLATE_INVERTER_LOCKED, (powered) -> unlockedTextureMapper.apply(powered, false), (powered) -> lockedTextureMapper.apply(powered, false));
		uploadBooleanGateModelTextures(generator, block, "_no_inverting", TEMPLATE_INVERTER, TEMPLATE_INVERTER_LOCKED, (powered) -> unlockedTextureMapper.apply(powered, true), (powered) -> lockedTextureMapper.apply(powered, true));

		BiFunction<Boolean, Boolean, Identifier> unlockedModelIdMapper = (powered, noInverting) -> TextureMap.getSubId(block, (noInverting ? "_no_inverting" : "")+(powered ? "_on" : ""));
		BiFunction<Boolean, Boolean, Identifier> lockedModelIdMapper = (powered, noInverting) -> TextureMap.getSubId(block, (noInverting ? "_no_inverting" : "")+"_locked"+(powered ? "_on" : ""));

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(InverterBlock.POWERED, InverterBlock.INVERTED, InverterBlock.LOCKED, InverterBlock.FACING).register((powered, inverted, locked, facing) -> BlockStateVariant.create().put(VariantSettings.MODEL, (locked ? lockedModelIdMapper : unlockedModelIdMapper).apply(powered, !inverted)).put(VariantSettings.Y, getRotationForGateFacing(facing)))));

		registerFlatItemModel(generator, block);
	}

	public void registerFullAnalogBlock(BlockStateModelGenerator generator, Block block,Function<Integer, Identifier> textureMapper,  Function<Integer, String> modelSuffixMapper) {
		Function<Integer, Identifier> modelMapper = (power) -> TextureMap.getSubId(block, "_"+modelSuffixMapper.apply(power));

		for(int power = 0; power <= 15; power++) {
			Models.CUBE_ALL.upload(block, "_"+modelSuffixMapper.apply(power), TextureMap.all(textureMapper.apply(power)), generator.modelCollector);
		}

		generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(createValueBasedModelMap(Properties.POWER, modelMapper)));

		generator.registerParentedItemModel(block, modelMapper.apply(0));
	}

	public void registerFullAnalogBlock(BlockStateModelGenerator generator, Block block, Function<Integer, Identifier> textureMapper) {
		registerFullAnalogBlock(generator, block, textureMapper, String::valueOf);
	}

	public VariantSettings.Rotation getRotationForGateFacing(Direction facing) {
		return switch(facing) {
			default -> VariantSettings.Rotation.R0;
			case WEST -> VariantSettings.Rotation.R90;
			case NORTH -> VariantSettings.Rotation.R180;
			case EAST -> VariantSettings.Rotation.R270;
		};
	}

	public void uploadBooleanGateModelTextures(BlockStateModelGenerator generator, Block block, String suffix, Model unlockedModel, Model lockedModel, Function<Boolean, TextureMap> unlockedTextureMapper, Function<Boolean, TextureMap> lockedTextureMapper) {
		uploadBooleanGateModelTextures(generator, block, suffix, unlockedModel, unlockedTextureMapper);
		uploadBooleanGateModelTextures(generator, block, suffix+"_locked", lockedModel, lockedTextureMapper);
	}

	public void uploadBooleanGateModelTextures(BlockStateModelGenerator generator, Block block, String suffix, Model model, Function<Boolean, TextureMap> textureMapper) {
		model.upload(block, suffix, textureMapper.apply(false), generator.modelCollector);
		model.upload(block, suffix+"_on", textureMapper.apply(true), generator.modelCollector);
	}

	public static BlockStateVariantMap createUpDefaultRotationStates() {
		return BlockStateVariantMap.create(Properties.FACING).register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180)).register(Direction.UP, BlockStateVariant.create()).register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90)).register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180)).register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270)).register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90));
	}

	public static <T extends Comparable<T>> BlockStateVariantMap createValueBasedModelMap(Property<T> property, Function<T, Identifier> mapper) {
		Map<Identifier, BlockStateVariant> variants = new HashMap<>();
		return BlockStateVariantMap.create(property).register(value -> variants.computeIfAbsent(mapper.apply(value), (id) -> BlockStateVariant.create().put(VariantSettings.MODEL, id)));
	}

	public static Model createModel(String name, TextureKey... requiredKeys) {
		return new Model(Optional.of(RedstoneBits.id("block/"+name)), Optional.empty(), requiredKeys);
	}

	public static Model createRedstoneGateModel(String name, boolean locked) {
		List<TextureKey> lockedKeys = List.of(SLAB, TextureKey.TOP, TextureKey.TORCH, LOCK);
		List<TextureKey> unlockedKeys = List.of(SLAB, TextureKey.TOP, TextureKey.TORCH);

		return createModel(name, (locked ? lockedKeys : unlockedKeys).toArray(new TextureKey[0]));
	}

	public static String getOffOrOn(boolean powered) {
		return powered ? "on" : "off";
	}

	public static Identifier getRedstoneTorchTextureId(boolean on) {
		return on ? TextureMap.getId(Blocks.REDSTONE_TORCH) : TextureMap.getSubId(Blocks.REDSTONE_TORCH, "_off");
	}
}
