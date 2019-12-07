package com.shnupbups.redstonebits;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
	public static final BooleanProperty BREAKING = BooleanProperty.of("breaking");
	public static final IntProperty COUNT = IntProperty.of("count", 0, 15);
}
