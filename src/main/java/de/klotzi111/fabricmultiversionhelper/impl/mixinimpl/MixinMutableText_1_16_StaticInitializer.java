package de.klotzi111.fabricmultiversionhelper.impl.mixinimpl;

import java.lang.reflect.Method;

import de.klotzi111.fabricmultiversionhelper.api.mapping.MappingHelper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

// this class is required because interfaces can not have static initializer blocks
// this class also can not be in the mixin interface because mixin does not allow direct access to classes inside the mixin package
public class MixinMutableText_1_16_StaticInitializer {
	public static final Method MutableText_append;

	public static final Method MutableText_setStyle;

	static {
		String CLASS_NAME_MutableText = "net.minecraft.class_5250"; // "net.minecraft.text.MutableText";
		Class<?> MutableText_class = MappingHelper.mapAndLoadClass(CLASS_NAME_MutableText, MappingHelper.CLASS_MAPPER_FUNCTION);

		MutableText_append = MappingHelper.mapAndGetMethod(MutableText_class, "method_10852", MutableText.class, Text.class);
		MutableText_setStyle = MappingHelper.mapAndGetMethod(MutableText_class, "method_10862", MutableText.class, Style.class);
	}

}
