package de.klotzi111.fabricmultiversionhelper.api.text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import de.klotzi111.fabricmultiversionhelper.api.mapping.MappingHelper;
import de.klotzi111.fabricmultiversionhelper.api.version.MinecraftVersionHelper;
import de.klotzi111.fabricmultiversionhelper.impl.error.ErrorHandler;
import net.minecraft.text.*;

/**
 * This class contains wrapper methods that deal with the Text/TextContent change in Minecraft 1.19.
 * The methods use cast to Object and back style because in the old Minecraft versions these classes are a type of that casted type and this prevents compile errors with newer Minecraft versions
 */
public class TextWrapper {

	private static final boolean IS_1_19 = MinecraftVersionHelper.isMCVersionAtLeast("1.19");

	// We can not give the real type in generics because TextContent from mc 1.19 is not in older versions and Text from older versions in no longer a super class of LiteralTextContent
	private static final Constructor<?> LiteralTextContent_constructor;

	private static final Constructor<Style> Style_constructor;
	private static final Method Text_asString;

	static {
		if (!IS_1_19) {
			// This weird looking load of the class and the constructor despite both being publicly visible and existing in all versions of minecraft is necessary because this class is a record in mc 1.19 and this causes compile problems with java 8
			String CLASS_NAME_LiteralTextContent = "net.minecraft.class_2585"; // "net.minecraft.text.LiteralTextContent";
			Class<?> LiteralTextContent_class = MappingHelper.mapAndLoadClass(CLASS_NAME_LiteralTextContent, MappingHelper.CLASS_MAPPER_FUNCTION);
			LiteralTextContent_constructor = MappingHelper.getConstructor(LiteralTextContent_class, String.class);

			Text_asString = MappingHelper.mapAndGetMethod(Text.class, "method_10851", String.class);
		} else {
			LiteralTextContent_constructor = null;
			Text_asString = null;
		}

		if (!MinecraftVersionHelper.isMCVersionAtLeast("1.16")) {
			Style_constructor = MappingHelper.getConstructor(Style.class);
		} else {
			Style_constructor = null;
		}
	}

	public static Style emptyStyle() {
		if (!MinecraftVersionHelper.isMCVersionAtLeast("1.16")) {
			try {
				Object[] instanceArgs = new Object[] {};
				return Style_constructor.newInstance(instanceArgs);
			} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
				ErrorHandler.handleReflectionException(e, "Failed to create new instance of \"%s\"", "Style");
			}
			return null;
		} else {
			return Style.EMPTY;
		}
	}

	public static Text literal(String string) {
		if (IS_1_19) {
			return Text.literal(string);
		} else {
			try {
				Object[] instanceArgs = new Object[] {string};
				return (Text) LiteralTextContent_constructor.newInstance(instanceArgs);
			} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
				ErrorHandler.handleReflectionException(e, "Failed to create new instance of \"%s\"", "LiteralTextContent");
			}
			return empty();
		}
	}

	public static Text translatable(String key) {
		if (IS_1_19) {
			return Text.translatable(key);
		} else {
			// mc versions < 1.16 only have constructor with key and objects
			return (Text) (Object) new TranslatableTextContent(key, new Object[0]);
		}
	}

	public static Text translatable(String key, Object... args) {
		if (IS_1_19) {
			return Text.translatable(key, args);
		} else {
			return (Text) (Object) new TranslatableTextContent(key, args);
		}
	}

	private static final Text EMPTY_LITERAL = literal("");

	public static Text empty() {
		if (IS_1_19) {
			return Text.empty();
		} else {
			return EMPTY_LITERAL;
		}
	}

	public static Text keybind(String string) {
		if (IS_1_19) {
			return Text.keybind(string);
		} else {
			return (Text) (Object) new KeybindTextContent(string);
		}
	}

	public static Text nbt(String rawPath, boolean interpret, Optional<Text> separator, NbtDataSource dataSource) {
		if (IS_1_19) {
			return Text.nbt(rawPath, interpret, separator, dataSource);
		} else {
			return (Text) (Object) new NbtTextContent(rawPath, interpret, separator, dataSource);
		}
	}

	public static Text score(String name, String objective) {
		if (IS_1_19) {
			return Text.score(name, objective);
		} else {
			return (Text) (Object) new ScoreTextContent(name, objective);
		}
	}

	public static Text selector(String pattern, Optional<Text> separator) {
		if (IS_1_19) {
			return Text.selector(pattern, separator);
		} else {
			return (Text) (Object) new SelectorTextContent(pattern, separator);
		}
	}

	// this should return the same as IMutableText#fmvh$asString
	public static String getAsString(Text text) {
		if (IS_1_19) {
			return text.getString();
		} else {
			try {
				return (String) Text_asString.invoke(text);
			} catch (IllegalAccessException | InvocationTargetException e) {
				ErrorHandler.handleReflectionException(e, "Failed to invoke \"%s\"", "Text::asString");
			}
			return "";
		}
	}
}
