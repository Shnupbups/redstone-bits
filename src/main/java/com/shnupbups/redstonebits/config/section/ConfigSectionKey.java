package com.shnupbups.redstonebits.config.section;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public record ConfigSectionKey(String sectionName) {
	public static final Interner<ConfigSectionKey> INTERNER = Interners.newStrongInterner();

	public static ConfigSectionKey of(String sectionName) {
		return INTERNER.intern(new ConfigSectionKey(sectionName));
	}
}
