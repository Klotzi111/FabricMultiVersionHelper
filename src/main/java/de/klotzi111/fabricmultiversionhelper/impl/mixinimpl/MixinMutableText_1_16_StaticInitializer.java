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

		String SIGNATURE_MutableText_append = MappingHelper.createSignature("(%s)%s", Text.class, MutableText.class);
		String REMAPPED_MutableText_append = MappingHelper.mapMethod(MutableText_class.getName(), "append", SIGNATURE_MutableText_append);
		MutableText_append = MappingHelper.getMethod(MutableText_class, REMAPPED_MutableText_append, SIGNATURE_MutableText_append);

		String SIGNATURE_MutableText_setStyle = MappingHelper.createSignature("(%s)%s", Style.class, MutableText.class);
		String REMAPPED_MutableText_setStyle = MappingHelper.mapMethod(MutableText_class.getName(), "setStyle", SIGNATURE_MutableText_setStyle);
		MutableText_setStyle = MappingHelper.getMethod(MutableText_class, REMAPPED_MutableText_setStyle, SIGNATURE_MutableText_setStyle);
	}

}
