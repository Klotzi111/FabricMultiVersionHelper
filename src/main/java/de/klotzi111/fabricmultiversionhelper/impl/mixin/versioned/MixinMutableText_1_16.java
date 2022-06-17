package de.klotzi111.fabricmultiversionhelper.impl.mixin.versioned;

import java.lang.reflect.InvocationTargetException;

import org.spongepowered.asm.mixin.Mixin;

import de.klotzi111.fabricmultiversionhelper.api.text.IMutableText;
import de.klotzi111.fabricmultiversionhelper.impl.mixinimpl.MixinMutableText_1_16_StaticInitializer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

// works for mc >= 1.16 and < 1.19 because in 1.19 it is a class
@Mixin(MutableText.class)
public abstract interface MixinMutableText_1_16 extends IMutableText {

	// we need to call the methods on 'MutableText' via reflection because this is a class but on older mc version where this mixin applies the target is an interface that causes bytecode incompatibility

	@Override
	public default IMutableText fmvh$append(Text text) {
		try {
			MixinMutableText_1_16_StaticInitializer.MutableText_append.invoke(this, text);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new UnsupportedOperationException("Failed to invoke method \"MutableText::append\" with reflection", e);
		}
		return this;
	}

	@Override
	public default IMutableText fmvh$setStyle(Style style) {
		try {
			MixinMutableText_1_16_StaticInitializer.MutableText_setStyle.invoke(this, style);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new UnsupportedOperationException("Failed to invoke method \"MutableText::setStyle\" with reflection", e);
		}
		return this;
	}

	@Override
	public default IMutableText fmvh$copy() {
		return (IMutableText) ((Text) (Object) this).copy();
	}
}
