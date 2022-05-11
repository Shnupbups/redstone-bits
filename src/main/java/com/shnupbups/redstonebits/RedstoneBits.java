package com.shnupbups.redstonebits;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import com.shnupbups.redstonebits.config.RedstoneBitsConfig;
import com.shnupbups.redstonebits.config.updater.ConfigUpdaters;
import com.shnupbups.redstonebits.init.ModBlockEntities;
import com.shnupbups.redstonebits.init.ModBlocks;
import com.shnupbups.redstonebits.init.ModScreenHandlers;
import com.shnupbups.redstonebits.init.ModSoundEvents;

public class RedstoneBits implements ModInitializer {
	public static final String MOD_ID = "redstonebits";

	public static final Logger LOGGER = LogUtils.getLogger();

	public static final int LATEST_CONFIG_VERSION = 1;

	private static RedstoneBitsConfig config;

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		if(!isConfigLoaded()) loadConfig();

		ModBlocks.init();
		ModBlockEntities.init();
		ModSoundEvents.init();
		ModScreenHandlers.init();
	}

	public static boolean isConfigLoaded() {
		return config != null;
	}

	public static RedstoneBitsConfig getConfig() {
		if(!isConfigLoaded()) loadConfig();
		return config;
	}

	public static void loadConfig() {
		ConfigHolder<RedstoneBitsConfig> configHolder = AutoConfig.register(RedstoneBitsConfig.class, GsonConfigSerializer::new);
		config = configHolder.getConfig();

		LOGGER.info("Redstone Bits config version " + getConfig().getVersion());

		if (ConfigUpdaters.shouldUpdate(config)) {
			LOGGER.info("Redstone Bits config outdated! Attempting to update...");
			config = ConfigUpdaters.update(config);
			configHolder.setConfig(config);
			configHolder.save();
		}
	}
}
