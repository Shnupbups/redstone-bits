package com.shnupbups.redstonebits.config.section;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class BreakerConfigSection implements ConfigSection {
	@ConfigEntry.Gui.Tooltip
	public float breakTimeMultiplier = 1;

	public BreakerConfigSection() {

	}

	public BreakerConfigSection(float breakTimeMultiplier) {
		this.breakTimeMultiplier = breakTimeMultiplier;
	}

	public float breakTimeMultiplier() {
		return breakTimeMultiplier;
	}

	@Override
	public ConfigSectionKey getSectionKey() {
		return ConfigSections.BREAKER;
	}

	public BreakerConfigSection withBreakTimeMultiplier(float breakTimeMultiplier) {
		return new BreakerConfigSection(breakTimeMultiplier);
	}
}
