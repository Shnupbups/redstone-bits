package com.shnupbups.redstonebits.config.updater;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.config.RedstoneBitsConfig;

public class ConfigUpdaters {
	public static final Int2ObjectArrayMap<ConfigUpdater> UPDATERS = new Int2ObjectArrayMap<>();
	public static final int LATEST_CONFIG_VERSION = RedstoneBits.LATEST_CONFIG_VERSION;

	static {
		// register updaters
	}

	public static void registerUpdater(int inputVersion, ConfigUpdater updater) {
		if(inputVersion <= 0) throw new IllegalArgumentException("Attempted to register updater for config version "+inputVersion+", but minimum is 1.");
		else if(inputVersion >= LATEST_CONFIG_VERSION) throw new IllegalArgumentException("Attempted to register updater for config version "+inputVersion+", but latest version is "+LATEST_CONFIG_VERSION+".");
		UPDATERS.put(inputVersion, updater);
	}

	public static boolean canUpdate(RedstoneBitsConfig config) {
		return UPDATERS.containsKey(config.getVersion());
	}

	public static boolean shouldUpdate(RedstoneBitsConfig config) {
		return config.getVersion() < LATEST_CONFIG_VERSION;
	}

	public static RedstoneBitsConfig update(RedstoneBitsConfig config) {
		while (canUpdate(config)) {
			int version = config.getVersion();
			ConfigUpdater updater = UPDATERS.get(version);
			config = updater.update(config);
			RedstoneBits.LOGGER.info("Ran config updater " + updater);
		}
		if(shouldUpdate(config)) RedstoneBits.LOGGER.warn("Could not update config! Config version is "+config.getVersion()+" when latest is "+LATEST_CONFIG_VERSION+".");
		return config;
	}
}
