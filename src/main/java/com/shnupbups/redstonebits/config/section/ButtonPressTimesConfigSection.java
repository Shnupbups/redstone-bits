package com.shnupbups.redstonebits.config.section;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ButtonPressTimesConfigSection implements ConfigSection {
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int unaffectedPressTicks = 5;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int exposedPressTicks = 15;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int weatheredPressTicks = 25;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int oxidizedPressTicks = 35;

	@Override
	public ConfigSectionKey getSectionKey() {
		return ConfigSections.BUTTON_PRESS_TIMES;
	}

	public ButtonPressTimesConfigSection() {

	}

	public ButtonPressTimesConfigSection(int unaffectedPressTicks, int exposedPressTicks, int weatheredPressTicks, int oxidizedPressTicks) {
		this.unaffectedPressTicks = unaffectedPressTicks;
		this.exposedPressTicks = exposedPressTicks;
		this.weatheredPressTicks = weatheredPressTicks;
		this.oxidizedPressTicks = oxidizedPressTicks;
	}

	public int unaffectedPressTicks() {
		return unaffectedPressTicks;
	}

	public int exposedPressTicks() {
		return exposedPressTicks;
	}

	public int weatheredPressTicks() {
		return weatheredPressTicks;
	}

	public int oxidizedPressTicks() {
		return oxidizedPressTicks;
	}

	public ButtonPressTimesConfigSection withUnaffectedPressTicks(int unaffectedPressTicks) {
		return new ButtonPressTimesConfigSection(unaffectedPressTicks, exposedPressTicks(), weatheredPressTicks(), oxidizedPressTicks());
	}

	public ButtonPressTimesConfigSection withExposedPressTicks(int exposedPressTicks) {
		return new ButtonPressTimesConfigSection(unaffectedPressTicks(), exposedPressTicks, weatheredPressTicks(), oxidizedPressTicks());
	}

	public ButtonPressTimesConfigSection withWeatheredPressTicks(int weatheredPressTicks) {
		return new ButtonPressTimesConfigSection(unaffectedPressTicks(), exposedPressTicks(), weatheredPressTicks, oxidizedPressTicks());
	}

	public ButtonPressTimesConfigSection withOxidizedPressTicks(int oxidizedPressTicks) {
		return new ButtonPressTimesConfigSection(unaffectedPressTicks(), exposedPressTicks(), weatheredPressTicks(), oxidizedPressTicks);
	}
}
