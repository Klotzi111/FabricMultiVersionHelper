package de.klotzi111.fabricmultiversionhelper.impl.mixin.versioned;

import org.spongepowered.asm.mixin.Mixin;

import de.klotzi111.fabricmultiversionhelper.api.text.IMutableText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

// works mc >= 1.19
@Mixin(MutableText.class)
public abstract class MixinMutableText_1_19 implements IMutableText {

	@Override
	public IMutableText fmvh$append(Text text) {
		((MutableText) (Object) this).append(text);
		return this;
	}

	@Override
	public IMutableText fmvh$setStyle(Style style) {
		((MutableText) (Object) this).setStyle(style);
		return this;
	}

	@Override
	public IMutableText fmvh$copy() {
		return (IMutableText) ((Text) (Object) this).copy();
	}
}
