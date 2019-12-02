package com.shnupbups.redstonebits;

import net.minecraft.state.property.BooleanProperty;

public class ModProperties {
	public static final BooleanProperty BREAKING;
	
	static {
		BREAKING = BooleanProperty.of("breaking");
	}
}
