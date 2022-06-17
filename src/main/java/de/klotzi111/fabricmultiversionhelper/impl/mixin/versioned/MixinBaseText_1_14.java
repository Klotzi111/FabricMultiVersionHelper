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

		String SIGNATURE_BaseText_append = MappingHelper.createSignature("(%s)%s", Text.class, Text.class);
		String REMAPPED_BaseText_append = MappingHelper.mapMethod(BaseText_class.getName(), "append", SIGNATURE_BaseText_append);
		BaseText_append = MappingHelper.getMethod(BaseText_class, REMAPPED_BaseText_append, SIGNATURE_BaseText_append);

		String SIGNATURE_BaseText_setStyle = MappingHelper.createSignature("(%s)%s", Style.class, Text.class);
		String REMAPPED_BaseText_setStyle = MappingHelper.mapMethod(BaseText_class.getName(), "setStyle", SIGNATURE_BaseText_setStyle);
		BaseText_setStyle = MappingHelper.getMethod(BaseText_class, REMAPPED_BaseText_setStyle, SIGNATURE_BaseText_setStyle);

		String SIGNATURE_Text_deepCopy = MappingHelper.createSignature("()%s", Text.class);
		String REMAPPED_Text_deepCopy = MappingHelper.mapMethod(Text.class.getName(), "deepCopy", SIGNATURE_Text_deepCopy);
		BaseText_deepCopy = MappingHelper.getMethod(Text.class, REMAPPED_Text_deepCopy, SIGNATURE_Text_deepCopy);
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
