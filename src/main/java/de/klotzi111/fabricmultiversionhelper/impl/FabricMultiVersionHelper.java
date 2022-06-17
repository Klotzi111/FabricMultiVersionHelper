package de.klotzi111.fabricmultiversionhelper.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;

import de.klotzi111.fabricmultiversionhelper.impl.mixinselect.config.VersionPredicateTypeAdapterFactory;
import de.klotzi111.util.GsonUtil.GsonUtil;

public class FabricMultiVersionHelper {

	public static final Gson GSON;

	static {
		List<TypeAdapterFactory> factories = new ArrayList<>();
		factories.add(new VersionPredicateTypeAdapterFactory());
		GSON = GsonUtil.getPreConfiguredGsonBuilder(factories)
			.disableHtmlEscaping()
			.setPrettyPrinting()
			.create();
	}

}
