package com.shnupbups.redstonebits.config.section;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class PressurePlateWeightsConfigSection implements ConfigSection {
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int unaffectedWeight = 5;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int exposedWeight = 15;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int weatheredWeight = 25;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.RequiresRestart
	public int oxidizedWeight = 35;

	@Override
	public ConfigSectionKey getSectionKey() {
		return ConfigSections.PRESSURE_PLATE_WEIGHTS;
	}

	public PressurePlateWeightsConfigSection() {

	}

	public PressurePlateWeightsConfigSection(int unaffectedWeight, int exposedWeight, int weatheredWeight, int oxidizedWeight) {
		this.unaffectedWeight = unaffectedWeight;
		this.exposedWeight = exposedWeight;
		this.weatheredWeight = weatheredWeight;
		this.oxidizedWeight = oxidizedWeight;
	}

	public int unaffectedWeight() {
		return unaffectedWeight;
	}

	public int exposedWeight() {
		return exposedWeight;
	}

	public int weatheredWeight() {
		return weatheredWeight;
	}

	public int oxidizedWeight() {
		return oxidizedWeight;
	}

	public PressurePlateWeightsConfigSection withUnaffectedWeight(int unaffectedWeight) {
		return new PressurePlateWeightsConfigSection(unaffectedWeight, exposedWeight(), weatheredWeight(), oxidizedWeight());
	}

	public PressurePlateWeightsConfigSection withExposedWeight(int exposedWeight) {
		return new PressurePlateWeightsConfigSection(unaffectedWeight(), exposedWeight, weatheredWeight(), oxidizedWeight());
	}

	public PressurePlateWeightsConfigSection withWeatheredWeight(int weatheredWeight) {
		return new PressurePlateWeightsConfigSection(unaffectedWeight(), exposedWeight(), weatheredWeight, oxidizedWeight());
	}

	public PressurePlateWeightsConfigSection withOxidizedWeight(int oxidizedWeight) {
		return new PressurePlateWeightsConfigSection(unaffectedWeight(), exposedWeight(), weatheredWeight(), oxidizedWeight);
	}
}
