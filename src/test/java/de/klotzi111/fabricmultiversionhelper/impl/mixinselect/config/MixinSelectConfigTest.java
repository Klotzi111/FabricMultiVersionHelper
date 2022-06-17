package de.klotzi111.fabricmultiversionhelper.impl.mixinselect.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;

import de.klotzi111.fabricmultiversionhelper.api.mixinselect.MixinSelectConfig;
import de.klotzi111.fabricmultiversionhelper.impl.FabricMultiVersionHelper;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

public class MixinSelectConfigTest {

	private MixinSelectConfig createTestMixinSelectConfig() throws VersionParsingException {
		HashMap<String, MixinSelectAlternativeModConditions> mixinClassConditions = new HashMap<>();
		List<MixinSelectAdditiveModConditions> alternatives = new ArrayList<>();
		List<ModCondition> modConditions = new ArrayList<>();
		modConditions.add(new ModCondition("minecraft", Arrays.asList(">=1.18", ">=1.16 <1.18")));
		modConditions.add(new ModCondition("test", Arrays.asList(">=1.0.0")));
		alternatives.add(new MixinSelectAdditiveModConditions(modConditions));
		modConditions = new ArrayList<>();
		modConditions.add(new ModCondition("minecraft", Arrays.asList("<1.14", "~1.14")));
		modConditions.add(new ModCondition("test", Arrays.asList("<1.0.0")));
		modConditions.add(new ModCondition("ENV", Arrays.asList("other")));
		alternatives.add(new MixinSelectAdditiveModConditions(modConditions));
		MixinSelectAlternativeModConditions minecraftCond = new MixinSelectAlternativeModConditions(alternatives);
		mixinClassConditions.put("MixinMouse", minecraftCond);
		List<MixinSelectAdditiveModConditions> alternatives2 = new ArrayList<>(alternatives);
		alternatives2.remove(0);
		MixinSelectAlternativeModConditions minecraftCond2 = new MixinSelectAlternativeModConditions(alternatives2);
		mixinClassConditions.put("MixinKeyboard", minecraftCond2);

		modConditions = new ArrayList<>();
		modConditions.add(new ModCondition("ENV", Arrays.asList("cool")));
		MixinSelectAdditiveModConditions defaultConditions = new MixinSelectAdditiveModConditions(modConditions);
		MixinSelectConfig config = new MixinSelectConfig(mixinClassConditions, defaultConditions);
		return config;
	}

	@Test
	public void testDeSerializeMixinSelectConfig() throws VersionParsingException {
		MixinSelectConfig config = createTestMixinSelectConfig();

		JsonElement jsonElement = FabricMultiVersionHelper.GSON.toJsonTree(config);
		System.out.println("json:\n" + FabricMultiVersionHelper.GSON.toJson(jsonElement));

		MixinSelectConfig configReadBack = FabricMultiVersionHelper.GSON.fromJson(jsonElement, MixinSelectConfig.class);
		JsonElement jsonElementReadBack = FabricMultiVersionHelper.GSON.toJsonTree(configReadBack);
		assertEquals(jsonElement, jsonElementReadBack);
	}

	@Test
	public void testSerializeGsonMapJsonObject() throws VersionParsingException {
		ModCondition cond = new ModCondition("test", Arrays.asList(">=1.0.0"));
		List<ModCondition> conditions = new ArrayList<>();
		conditions.add(cond);
		@SuppressWarnings("serial")
		JsonElement jsonElement = FabricMultiVersionHelper.GSON.toJsonTree(conditions, new TypeToken<List<ModCondition>>() {}.getType());
		System.out.println("json:\n" + FabricMultiVersionHelper.GSON.toJson(jsonElement));
		assertTrue(jsonElement.getAsJsonObject().get("test").isJsonArray());
	}

	@Test
	public void testMixinSelectConfigMatching() throws VersionParsingException {
		MixinSelectConfig config = createTestMixinSelectConfig();

		List<HashMap<String, Version>> testModsWithVersion = new ArrayList<>();

		HashMap<String, Version> modsWithVersion = new HashMap<>();
		modsWithVersion.put("minecraft", Version.parse("1.14"));
		modsWithVersion.put("test", Version.parse("0.1.3"));
		modsWithVersion.put("ENV", Version.parse("other"));
		testModsWithVersion.add(modsWithVersion);

		modsWithVersion = new HashMap<>();
		modsWithVersion.put("minecraft", Version.parse("1.16"));
		modsWithVersion.put("test", Version.parse("1.1.3"));
		modsWithVersion.put("ENV", Version.parse("cool"));
		testModsWithVersion.add(modsWithVersion);

		for (HashMap<String, Version> mwv : testModsWithVersion) {
			assertTrue(config.isMixinAllowed("MixinMouse", mwv));
		}
	}

}
