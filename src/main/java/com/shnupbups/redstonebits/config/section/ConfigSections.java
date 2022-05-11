package com.shnupbups.redstonebits.config.section;

import java.util.HashSet;
import java.util.Set;

public class ConfigSections {
	public static final Set<ConfigSectionKey> SECTIONS = new HashSet<>();

	public static final ConfigSectionKey BUTTON_PRESS_TIMES = registerSection(ConfigSectionKey.of("button_press_times"));
	public static final ConfigSectionKey PRESSURE_PLATE_WEIGHTS = registerSection(ConfigSectionKey.of("pressure_plate_weights"));
	public static final ConfigSectionKey BREAKER = registerSection(ConfigSectionKey.of("breaker"));

	public static ConfigSectionKey registerSection(ConfigSectionKey key) {
		if(SECTIONS.contains(key)) throw new IllegalArgumentException("Attempted to register config section "+key.sectionName()+", but it was already registered!");
		SECTIONS.add(key);
		return key;
	}
}
