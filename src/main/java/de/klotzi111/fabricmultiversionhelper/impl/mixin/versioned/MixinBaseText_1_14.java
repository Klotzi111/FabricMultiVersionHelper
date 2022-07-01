package de.klotzi111.fabricmultiversionhelper.impl.mixin.versioned;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import de.klotzi111.fabricmultiversionhelper.api.mapping.MappingHelper;
import de.klotzi111.fabricmultiversionhelper.api.text.IMutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

// works for mc >= 1.14
@Mixin(targets = "net/minecraft/text/BaseText")
public abstract class MixinBaseText_1_14 implements IMutableText {

	@Unique
	private static final Method BaseText_append;

	@Unique
	private static final Method BaseText_setStyle;

	@Unique
	private static final Method BaseText_deepCopy;

	static {
		String CLASS_NAME_BaseText = "net.minecraft.class_2554"; // "net.minecraft.text.BaseText";
		Class<?> BaseText_class = MappingHelper.mapAndLoadClass(CLASS_NAME_BaseText, MappingHelper.CLASS_MAPPER_FUNCTION);

		// these intermediary names are intentially not intermediary looking because in that mc version the methods have plain names
		BaseText_append = MappingHelper.mapAndGetMethod(BaseText_class, "append", Text.class, Text.class);
		BaseText_setStyle = MappingHelper.mapAndGetMethod(BaseText_class, "setStyle", Text.class, Style.class);
		BaseText_deepCopy = MappingHelper.mapAndGetMethod(Text.class, "deepCopy", Text.class);
	}

	@Override
	public IMutableText fmvh$append(Text text) {
		try {
			BaseText_append.invoke(this, text);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new UnsupportedOperationException("Failed to invoke method \"BaseText::append\" with reflection", e);
		}
		return this;
	}

	@Override
	public IMutableText fmvh$setStyle(Style style) {
		try {
			BaseText_setStyle.invoke(this, style);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new UnsupportedOperationException("Failed to invoke method \"BaseText::setStyle\" with reflection", e);
		}
		return this;
	}

	@Override
	public IMutableText fmvh$copy() {
		try {
			// the upcast to IMutableText despite the return value being Text is a bit unsafe
			return (IMutableText) BaseText_deepCopy.invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new UnsupportedOperationException("Failed to invoke method \"BaseText::deepCopy\" with reflection", e);
		}
	}
}
