package com.shnupbups.redstonebits.datagen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.shnupbups.redstonebits.init.RBItems;
import com.shnupbups.redstonebits.properties.RBProperties;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.block.AdderOrCounterBlock;
import com.shnupbups.redstonebits.block.InverterBlock;
import com.shnupbups.redstonebits.block.ResistorBlock;
import com.shnupbups.redstonebits.init.RBBlocks;
import com.shnupbups.redstonebits.properties.ResistorMode;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

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

	// TODO: remove when TAW'd by FAPI
	private static final BlockStateVariantMap<ModelVariantOperator> NORTH_DEFAULT_ROTATION_OPERATIONS = BlockStateVariantMap.operations(Properties.FACING)
			.register(Direction.DOWN, ROTATE_X_90)
			.register(Direction.UP, ROTATE_X_270)
			.register(Direction.NORTH, NO_OP)
			.register(Direction.SOUTH, ROTATE_Y_180)
			.register(Direction.WEST, ROTATE_Y_270)
			.register(Direction.EAST, ROTATE_Y_90);
	private static final BlockStateVariantMap<ModelVariantOperator> SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS = BlockStateVariantMap.operations(
					Properties.HORIZONTAL_FACING
			)
			.register(Direction.SOUTH, NO_OP)
			.register(Direction.WEST, ROTATE_Y_90)
			.register(Direction.NORTH, ROTATE_Y_180)
			.register(Direction.EAST, ROTATE_Y_270);
	private static final BlockStateVariantMap<ModelVariantOperator> UP_DEFAULT_ROTATION_OPERATIONS = BlockStateVariantMap.operations(Properties.FACING)
			.register(Direction.DOWN, ROTATE_X_180)
			.register(Direction.UP, NO_OP)
			.register(Direction.NORTH, ROTATE_X_90)
			.register(Direction.SOUTH, ROTATE_X_90.then(ROTATE_Y_180))
			.register(Direction.WEST, ROTATE_X_90.then(ROTATE_Y_270))
			.register(Direction.EAST, ROTATE_X_90.then(ROTATE_Y_90));

	public RBModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
		RedstoneBits.LOGGER.info("Generating blockstate models...");

		registerDispenserLikeOrientable(generator, RBBlocks.UTILIZER);
		registerDispenserLikeOrientable(generator, RBBlocks.PLACER);
		registerDispenserLikeOrientable(generator, RBBlocks.BREAKER);

		registerChecker(generator);
		registerRotator(generator);

		registerAdderOrCounter(generator, RBBlocks.ADDER);
		registerAdderOrCounter(generator, RBBlocks.COUNTER);
		registerResistor(generator);
		registerInverter(generator);

		registerWeightedPressurePlate(generator, RBBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK);
		registerWeightedPressurePlate(generator, RBBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER);
		registerWeightedPressurePlate(generator, RBBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER);
		registerWeightedPressurePlate(generator, RBBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER);
		registerWeightedPressurePlate(generator, RBBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK);
		registerWeightedPressurePlate(generator, RBBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER);
		registerWeightedPressurePlate(generator, RBBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER);
		registerWeightedPressurePlate(generator, RBBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER);

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

        WeightedVariant offModel = createWeightedVariant(Models.CUBE_ALL.upload(block, offTextureMap, generator.modelCollector));
        WeightedVariant onModel = createWeightedVariant(generator.createSubModel(block, "_on", Models.CUBE_ALL, id -> onTextureMap));
        WeightedVariant mediumModel = createWeightedVariant(generator.createSubModel(block, "_medium", Models.CUBE_ALL, id -> mediumTextureMap));

		generator.blockStateCollector.accept(createAnalogLampBlockState(block, offModel, onModel, mediumModel));

		registerParentedItemModel(generator, block);
	}

    public static BlockModelDefinitionCreator createAnalogLampBlockState(
            Block block, WeightedVariant offModel, WeightedVariant onModel, WeightedVariant mediumModel
    ) {
        return VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap.models(Properties.POWER).generate(power -> switch(power) {
            case 0 -> offModel;
            case 15 -> onModel;
            default -> mediumModel;
        }));
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

        WeightedVariant offModel = createWeightedVariant(Models.CUBE_ALL.upload(block, offTextureMap, generator.modelCollector));
        WeightedVariant onModel = createWeightedVariant(generator.createSubModel(block, "_on", Models.CUBE_ALL, id -> onTextureMap));

		generator.blockStateCollector.accept(createRedstoneGlassBlockState(block, offModel, onModel));

		registerParentedItemModel(generator, block);
	}

    public static BlockModelDefinitionCreator createRedstoneGlassBlockState(
            Block block, WeightedVariant offModel, WeightedVariant onModel
    ) {
        return VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap.models(Properties.POWER).generate(power -> power == 0? offModel : onModel));
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

		TEMPLATE_CHECKER.upload(RBBlocks.CHECKER, offTextureMap, generator.modelCollector);
		TEMPLATE_CHECKER.upload(RBBlocks.CHECKER, "_on", onTextureMap, generator.modelCollector);

		WeightedVariant offModel = createWeightedVariant(ModelIds.getBlockModelId(block));
		WeightedVariant onModel = createWeightedVariant(ModelIds.getBlockSubModelId(block, "_on"));
		generator.blockStateCollector
				.accept(
						VariantsBlockModelDefinitionCreator.of(block)
								.with(createValueFencedModelMap(Properties.POWER, 1, onModel, offModel))
								.coordinate(NORTH_DEFAULT_ROTATION_OPERATIONS)
				);

		registerParentedItemModel(generator, block);
	}

	private void registerRotator(BlockStateModelGenerator generator) {
		Block block = RBBlocks.ROTATOR;

		TextureMap baseTextureMap = new TextureMap().put(TextureKey.BOTTOM, TextureMap.getSubId(Blocks.PISTON, "_bottom")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top"));
		TextureMap regularTextureMap = baseTextureMap.copyAndAdd(TextureKey.SIDE, TextureMap.getSubId(block, "_side"));
		TextureMap invertedTextureMap = baseTextureMap.copyAndAdd(TextureKey.SIDE, TextureMap.getSubId(block, "_side_inverted"));

		WeightedVariant regularModel = createWeightedVariant(Models.CUBE_BOTTOM_TOP.upload(block, regularTextureMap, generator.modelCollector));
		WeightedVariant invertedModel = createWeightedVariant(generator.createSubModel(block, "_inverted", Models.CUBE_BOTTOM_TOP, id -> invertedTextureMap));

		generator.blockStateCollector.accept(createRotatorBlockState(block, regularModel, invertedModel));

		registerParentedItemModel(generator, block);
	}

	public static BlockModelDefinitionCreator createRotatorBlockState(
			Block block, WeightedVariant regularModel, WeightedVariant invertedModel
	) {
		return VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap.models(Properties.INVERTED).generate(inverted -> inverted ? invertedModel : regularModel))
				.coordinate(UP_DEFAULT_ROTATION_OPERATIONS);
	}
	
	public void registerButton(BlockStateModelGenerator generator, Block button, Block textureSource) {
		TextureMap textureMap = TextureMap.texture(textureSource);

		WeightedVariant unpressedModel = createWeightedVariant(Models.BUTTON.upload(button, textureMap, generator.modelCollector));
		WeightedVariant pressedModel = createWeightedVariant(generator.createSubModel(button, "_pressed", Models.BUTTON_PRESSED, id -> textureMap));

		generator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(button, unpressedModel, pressedModel));

		Identifier buttonInventoryModelId = Models.BUTTON_INVENTORY.upload(button, textureMap, generator.modelCollector);
		generator.registerParentedItemModel(button, buttonInventoryModelId);
	}

	public void registerWeightedPressurePlate(BlockStateModelGenerator generator, Block pressurePlate, Block textureSource)  {
		generator.registerWeightedPressurePlate(pressurePlate, textureSource);

		registerParentedItemModel(generator, pressurePlate);
	}

	public void registerAdderOrCounter(BlockStateModelGenerator generator, Block block)  {
		generator.registerItemModel(block.asItem());

		generator.blockStateCollector
				.accept(
						VariantsBlockModelDefinitionCreator.of(block)
								.with(BlockStateVariantMap.models(RBProperties.BACKWARDS, Properties.LOCKED, Properties.POWER).generate((backwards, locked, power) -> {
									StringBuilder stringBuilder = new StringBuilder();
									if (backwards) stringBuilder.append("_backwards");
									if (locked) stringBuilder.append("_locked");
									stringBuilder.append("_").append(power);

									return createWeightedVariant(TextureMap.getSubId(block, stringBuilder.toString()));
								}))
								.coordinate(SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS)
				);

		BiFunction<Integer, Boolean, TextureMap> unlockedTextureMapper = (power, backwards) -> new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, "_"+power)).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_"+power)).put(SLAB, TextureMap.getId(Blocks.SMOOTH_STONE)).put(TextureKey.TORCH, getRedstoneTorchTextureId(backwards));
		BiFunction<Integer, Boolean, TextureMap> lockedTextureMapper = (power, backwards) -> unlockedTextureMapper.apply(power, backwards).copyAndAdd(LOCK, TextureMap.getId(Blocks.BEDROCK));

		for(int power = 0; power <= 15; power++) {
			TEMPLATE_ADDER_OR_COUNTER.upload(block, "_"+power, unlockedTextureMapper.apply(power, false), generator.modelCollector);
			TEMPLATE_ADDER_OR_COUNTER.upload(block, "_backwards_"+power, unlockedTextureMapper.apply(power, true), generator.modelCollector);
			TEMPLATE_ADDER_OR_COUNTER_LOCKED.upload(block, "_locked_"+power, lockedTextureMapper.apply(power, false), generator.modelCollector);
			TEMPLATE_ADDER_OR_COUNTER_LOCKED.upload(block, "_backwards_locked_"+power, lockedTextureMapper.apply(power, true), generator.modelCollector);
		}
	}

	public void registerResistor(BlockStateModelGenerator generator) {
		Block block = RBBlocks.RESISTOR;

		generator.registerItemModel(block.asItem());

		generator.blockStateCollector
				.accept(
						VariantsBlockModelDefinitionCreator.of(block)
								.with(BlockStateVariantMap.models(RBProperties.RESISTOR_MODE, Properties.LOCKED, Properties.POWERED).generate((mode, locked, on) -> {
									StringBuilder stringBuilder = new StringBuilder();
									stringBuilder.append('_').append(mode.asString());
									if (locked) stringBuilder.append("_locked");
									if (on) stringBuilder.append("_on");

									return createWeightedVariant(TextureMap.getSubId(block, stringBuilder.toString()));
								}))
								.coordinate(SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS)
				);

		Function<Boolean, TextureMap> unlockedTextureMapper = powered -> new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, "_"+getOffOrOn(powered))).put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_"+getOffOrOn(powered))).put(SLAB, TextureMap.getId(Blocks.SMOOTH_STONE)).put(TextureKey.TORCH, getRedstoneTorchTextureId(powered));
		Function<Boolean, TextureMap> lockedTextureMapper = powered -> unlockedTextureMapper.apply(powered).copyAndAdd(LOCK, TextureMap.getId(Blocks.BEDROCK));

		uploadBooleanGateModelTextures(generator, block, "_halve", TEMPLATE_RESISTOR_HALVE, TEMPLATE_RESISTOR_HALVE_LOCKED, unlockedTextureMapper, lockedTextureMapper);
		uploadBooleanGateModelTextures(generator, block, "_third", TEMPLATE_RESISTOR_THIRD, TEMPLATE_RESISTOR_THIRD_LOCKED, unlockedTextureMapper, lockedTextureMapper);
		uploadBooleanGateModelTextures(generator, block, "_one_point_five", TEMPLATE_RESISTOR_ONE_POINT_FIVE, TEMPLATE_RESISTOR_ONE_POINT_FIVE_LOCKED, unlockedTextureMapper, lockedTextureMapper);
	}

	public void registerInverter(BlockStateModelGenerator generator) {
		Block block = RBBlocks.INVERTER;

		generator.registerItemModel(block.asItem());

		generator.blockStateCollector
				.accept(
						VariantsBlockModelDefinitionCreator.of(block)
								.with(BlockStateVariantMap.models(Properties.INVERTED, Properties.LOCKED, Properties.POWERED).generate((inverted, locked, on) -> {
									StringBuilder stringBuilder = new StringBuilder();
									if (!inverted) stringBuilder.append("_no_inverting");
									if (locked) stringBuilder.append("_locked");
									if (on) stringBuilder.append("_on");

									return createWeightedVariant(TextureMap.getSubId(block, stringBuilder.toString()));
								}))
								.coordinate(SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS)
				);

		BiFunction<Boolean, Boolean, TextureMap> unlockedTextureMapper = (powered, noInverting) -> new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, (noInverting ? "_no_inverting_" : "_")+getOffOrOn(powered))).put(TextureKey.PARTICLE, TextureMap.getSubId(block, (noInverting ? "_no_inverting_" : "_")+getOffOrOn(powered))).put(SLAB, TextureMap.getId(Blocks.SMOOTH_STONE)).put(TextureKey.TORCH, getRedstoneTorchTextureId(powered == noInverting));
		BiFunction<Boolean, Boolean, TextureMap> lockedTextureMapper = (powered, noInverting) -> unlockedTextureMapper.apply(powered, noInverting).copyAndAdd(LOCK, TextureMap.getId(Blocks.BEDROCK));

		uploadBooleanGateModelTextures(generator, block, "", TEMPLATE_INVERTER, TEMPLATE_INVERTER_LOCKED, (powered) -> unlockedTextureMapper.apply(powered, false), (powered) -> lockedTextureMapper.apply(powered, false));
		uploadBooleanGateModelTextures(generator, block, "_no_inverting", TEMPLATE_INVERTER, TEMPLATE_INVERTER_LOCKED, (powered) -> unlockedTextureMapper.apply(powered, true), (powered) -> lockedTextureMapper.apply(powered, true));
	}

	public void registerFullAnalogBlock(BlockStateModelGenerator generator, Block block, Function<Integer, Identifier> textureMapper,  Function<Integer, String> modelSuffixMapper) {
		Function<Integer, Identifier> modelIdMapper = power -> generator.createSubModel(block, "_"+modelSuffixMapper.apply(power), Models.CUBE_ALL, id -> TextureMap.all(textureMapper.apply(power)));
		Function<Integer, WeightedVariant> modelMapper = modelIdMapper.andThen(BlockStateModelGenerator::createWeightedVariant);

		generator.blockStateCollector.accept(createFullAnalogBlockState(block, modelMapper));

		generator.registerParentedItemModel(block, TextureMap.getSubId(block, "_0"));
	}

	public void registerFullAnalogBlock(BlockStateModelGenerator generator, Block block, Function<Integer, Identifier> textureMapper) {
		registerFullAnalogBlock(generator, block, textureMapper, String::valueOf);
	}

	public static BlockModelDefinitionCreator createFullAnalogBlockState(
			Block block, Function<Integer, WeightedVariant> models
	) {
		return VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap.models(Properties.POWER).generate(models));
	}

	public void uploadBooleanGateModelTextures(BlockStateModelGenerator generator, Block block, String suffix, Model unlockedModel, Model lockedModel, Function<Boolean, TextureMap> unlockedTextureMapper, Function<Boolean, TextureMap> lockedTextureMapper) {
		uploadBooleanGateModelTextures(generator, block, suffix, unlockedModel, unlockedTextureMapper);
		uploadBooleanGateModelTextures(generator, block, suffix+"_locked", lockedModel, lockedTextureMapper);
	}

	public void uploadBooleanGateModelTextures(BlockStateModelGenerator generator, Block block, String suffix, Model model, Function<Boolean, TextureMap> textureMapper) {
		model.upload(block, suffix, textureMapper.apply(false), generator.modelCollector);
		model.upload(block, suffix+"_on", textureMapper.apply(true), generator.modelCollector);
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