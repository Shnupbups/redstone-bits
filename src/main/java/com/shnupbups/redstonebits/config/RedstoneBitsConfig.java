package com.shnupbups.redstonebits.config;

import java.util.HashMap;
import java.util.Map;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import com.shnupbups.redstonebits.RedstoneBits;
import com.shnupbups.redstonebits.config.section.BreakerConfigSection;
import com.shnupbups.redstonebits.config.section.ButtonPressTimesConfigSection;
import com.shnupbups.redstonebits.config.section.ConfigSection;
import com.shnupbups.redstonebits.config.section.ConfigSectionKey;
import com.shnupbups.redstonebits.config.section.ConfigSections;
import com.shnupbups.redstonebits.config.section.PressurePlateWeightsConfigSection;

@Config(name = RedstoneBits.MOD_ID)
public class RedstoneBitsConfig implements ConfigData {
	@ConfigEntry.Gui.Excluded
	private int configVersion = RedstoneBits.LATEST_CONFIG_VERSION;

	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.CollapsibleObject
	public ButtonPressTimesConfigSection buttonPressTimes = new ButtonPressTimesConfigSection();

	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.CollapsibleObject
	public PressurePlateWeightsConfigSection pressurePlateWeights = new PressurePlateWeightsConfigSection();

	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.CollapsibleObject
	public BreakerConfigSection breaker = new BreakerConfigSection();

	public RedstoneBitsConfig() {

	}

	public RedstoneBitsConfig(int configVersion, Map<ConfigSectionKey, ConfigSection> sections) {
		this.configVersion = configVersion;
		this.buttonPressTimes = (ButtonPressTimesConfigSection) sections.getOrDefault(ConfigSections.BUTTON_PRESS_TIMES, buttonPressTimes);
		this.pressurePlateWeights = (PressurePlateWeightsConfigSection) sections.getOrDefault(ConfigSections.PRESSURE_PLATE_WEIGHTS, pressurePlateWeights);
		this.breaker = (BreakerConfigSection) sections.getOrDefault(ConfigSections.BREAKER, breaker);
	}

	public int configVersion() {
		return configVersion;
	}

	public ButtonPressTimesConfigSection buttonPressTimes() {
		return buttonPressTimes;
	}

	public PressurePlateWeightsConfigSection pressurePlateWeights() {
		return pressurePlateWeights;
	}

	public BreakerConfigSection breaker() {
		return breaker;
	}

	public int getVersion() {
		return Math.min(Math.max(configVersion(), 1), RedstoneBits.LATEST_CONFIG_VERSION);
	}

	public ConfigSection getSection(ConfigSectionKey key) {
		if(key.equals(ConfigSections.BUTTON_PRESS_TIMES)) return buttonPressTimes();
		else if(key.equals(ConfigSections.PRESSURE_PLATE_WEIGHTS)) return pressurePlateWeights();
		else if(key.equals(ConfigSections.BREAKER)) return breaker();
		else return null;
	}

	public RedstoneBitsConfig withVersion(int version) {
		return new RedstoneBitsConfig(version, getSections());
	}

	public <S extends ConfigSection> RedstoneBitsConfig withSection(S section) {
		Map<ConfigSectionKey, ConfigSection> sections = getSections();
		sections.put(section.getSectionKey(), section);
		return new RedstoneBitsConfig(configVersion(), sections);
	}

	public Map<ConfigSectionKey, ConfigSection> getSections() {
		Map<ConfigSectionKey, ConfigSection> sections = new HashMap<>();
		for(ConfigSectionKey key : ConfigSections.SECTIONS) {
			sections.put(key, getSection(key));
		}
		return sections;
	}
}
