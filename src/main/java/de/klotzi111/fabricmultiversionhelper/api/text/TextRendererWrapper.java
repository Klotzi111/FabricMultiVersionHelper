package de.klotzi111.fabricmultiversionhelper.api.text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.klotzi111.fabricmultiversionhelper.api.mapping.MappingHelper;
import de.klotzi111.fabricmultiversionhelper.api.version.MinecraftVersionHelper;
import de.klotzi111.fabricmultiversionhelper.impl.error.ErrorHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class TextRendererWrapper {

	// We need this inner class because otherwise when this class gets loaded the runtime complains about classes it can not find
	private static class TextRendererWrapper_1_16 {

		public static int getWidth(TextRenderer textRenderer, Text text) {
			return textRenderer.getWidth(text);
		}
	}

	private static final Method TextRenderer_getStringWidth;

	static {
		if (!MinecraftVersionHelper.isMCVersionAtLeast("1.16")) {
			TextRenderer_getStringWidth = MappingHelper.mapAndGetMethod(TextRenderer.class, "method_1727", int.class, String.class);
		} else {
			TextRenderer_getStringWidth = null;
		}
	}

	public static int getWidth(TextRenderer textRenderer, Text text) {
		if (MinecraftVersionHelper.isMCVersionAtLeast("1.16")) {
			return TextRendererWrapper_1_16.getWidth(textRenderer, text);
		} else {
			try {
				return (int) TextRenderer_getStringWidth.invoke(textRenderer, TextWrapper.getAsString(text));
			} catch (IllegalAccessException | InvocationTargetException e) {
				ErrorHandler.handleReflectionException(e, "Failed to invoke \"%s\"", "TextRenderer::getStringWidth");
			}
		}
		return 0;
	}

}
