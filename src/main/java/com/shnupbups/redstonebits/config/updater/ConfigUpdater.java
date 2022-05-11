package com.shnupbups.redstonebits.config.updater;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.config.RedstoneBitsConfig;
import com.shnupbups.redstonebits.config.section.ConfigSection;
import com.shnupbups.redstonebits.config.section.ConfigSectionKey;
import com.shnupbups.redstonebits.config.section.ConfigSections;

public abstract class ConfigUpdater {
	public boolean shouldUpdateConfig(RedstoneBitsConfig config) {
		int configVersion = config.getVersion();
		int outputVersion = getOutputVersion();

		if(configVersion < outputVersion - 1) {
			RedstoneBits.LOGGER.warn("Config updater "+getName()+" may be running too early! Attempting to update from config version "+configVersion+" to "+outputVersion+", potentially skipping updates.");
		}

		return configVersion < outputVersion;
	}

	abstract boolean shouldUpdateSection(ConfigSectionKey sectionKey);

	public <T extends ConfigSection> T updateSection(T section) {
		return section;
	}

	public final RedstoneBitsConfig update(RedstoneBitsConfig config) {
		RedstoneBitsConfig newConfig = config;

		if(shouldUpdateConfig(config)) {
			for(ConfigSectionKey key : ConfigSections.SECTIONS) {
				if(shouldUpdateSection(key)) {
					newConfig = newConfig.withSection(updateSection(newConfig.getSection(key)));
				}
			}
		}

		return newConfig.withVersion(getOutputVersion());
	}

	public abstract String getName();

	public abstract int getOutputVersion();

	@Override
	public String toString() {
		return getName();
	}
}
