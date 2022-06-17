package de.klotzi111.fabricmultiversionhelper.api.text;

import java.util.List;

import com.mojang.brigadier.Message;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

/**
 * This interface is merged into the mutable class implementing {@link Text} via mixins.
 * If you have a {@link Text} object you can cast it to {@link IMutableText} and use the methods in here.
 * <br>
 * Injected class for Minecraft version:
 * since 1.14: BaseText
 * since 1.16: MutableText
 */
@Environment(EnvType.CLIENT)
public interface IMutableText extends Text {

	IMutableText fmvh$append(Text text);

	default IMutableText fmvh$append(String string) {
		return fmvh$append(TextWrapper.literal(string));
	}

	// kinda unnecessary
	default List<Text> fmvh$getSiblings() {
		return ((Text) (Object) this).getSiblings();
	}

	IMutableText fmvh$setStyle(Style style);

	// kinda unnecessary
	default Style fmvh$getStyle() {
		return ((Text) (Object) this).getStyle();
	}

	// kinda unnecessary
	default String fmvh$asString() {
		return ((Message) (Object) this).getString();
	}

	/**
	 * Is at least a shallow copy but is a deep copy on minecraft < 1.16
	 *
	 * @return the copy
	 */
	IMutableText fmvh$copy();
}
