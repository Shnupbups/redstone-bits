package com.shnupbups.redstonebits.properties;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class RBProperties {
	public static final BooleanProperty BREAKING = BooleanProperty.of("breaking");
	public static final BooleanProperty BACKWARDS = BooleanProperty.of("backwards");
	public static final EnumProperty<ResistorMode> RESISTOR_MODE = EnumProperty.of("mode", ResistorMode.class);
}
