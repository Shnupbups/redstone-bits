package com.shnupbups.redstonebits.properties;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
	public static final BooleanProperty BREAKING = BooleanProperty.of("breaking");
	public static final IntProperty COUNT = IntProperty.of("count", 0, 15);
	public static final BooleanProperty BACKWARDS = BooleanProperty.of("backwards");
	public static final EnumProperty<ResistorMode> RESISTOR_MODE = EnumProperty.of("mode", ResistorMode.class);
}
