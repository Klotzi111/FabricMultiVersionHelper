# FabricMultiVersionHelper

Fabric mod library that assists with building a single mod jar file that support multiple minecraft versions.

## Usage
You can get it via jitpack or compile it yourself.

### Jitpack
Add the following in your build.gradle:

```groovy
repositories {
	maven {
		url "https://jitpack.io"
	}
}

dependencies {
	include(modApi("com.github.Klotzi111:FabricMultiVersionHelper:main-SNAPSHOT"))
}
```

### Compile self
Do the following to use this library mod:
 - Download, build and publish this mod to your local maven repository (use the gradle task `publishToMavenLocal` for that)
 - Add the following in your build.gradle:
 
```groovy
repositories {
	mavenLocal()
}

dependencies {
	include(modApi("de.klotzi111:FabricMultiVersionHelper:1+"))
}
```

## API
Look at the classes in the `api` package tree.

### Mixin Select Config
This library assists with loading mixin classes only when specific mod (also `fabric`, `minecraft` and `ENVIRONMENT`) dependencies are met.

The default path within your mod resources is `mixin/mixinSelect.config.json`.
You can specify `defaultConditions` that are default conditions merged to every condition block but they can be overriden in the condition block.
`mixinConditions` is a map. Its keys are mixin path (relative to mixin package). Its values are a list of condition block where only one block needs to be true in order for that mixin to be loaded.
A condition block can hold multiple versions for multiple modids. For every specified modid in that block must be at least on condition line true in order for the block to evaluate to true. Example:

```json
{
	"minecraft": [
		">=1.18"
	],
	"controlling": [
		">=9.0"
	]
}
```

An empty condition block `{}` is always true. And thus can be used for mixins that should always be loaded.

A condition line can be a string with multiple conditions that must all be true in order for the line to evaluate to true. Example:

```json
">=1.16 <1.17"
```

Here is an example for the content of that file:

```json
{
	"defaultConditions": {
		"ENVIRONMENT": [
			"CLIENT"
		]
	},
	"mixinConditions": {
		"MixinGameOptions": [
			{}
		],
		"versioned.MixinGameOptions_1_17": [
			{
				"minecraft": [
					">=1.17"
				]
			}
		],
		"versioned.MixinGameOptions_1_16": [
			{
				"minecraft": [
					">=1.16 <1.17"
				]
			}
		],
		"versioned.MixinGameOptions_1_14": [
			{
				"minecraft": [
					">=1.14 <1.16"
				]
			}
		],
		"controlling.MixinSortOrder": [
			{
				"minecraft": [
					">=1.18"
				],
				"controlling": [
					">=9.0"
				]
			}
		]
	}
}
```

To use the mixinSelect config create a mixin plugin class in your project (Do not forget to add it to your *.mixins.json file) and make it look like this:

```java
public class MyMixinConfig implements IMixinConfigPlugin {

	// Ensure that you do not load classes of the game or any class that gets an mixin applied.
	// Because those classes will be loaded too early and the mixin processor than fails to apply the mixins to those classes.
	// Also classes referenced in a class that has no mixin loads those referenced classes! Be careful!

	private List<String> mixinClasses = null;

	@Override
	public void onLoad(String mixinPackage) {
		// TODO: replace the example modid with your mod's id
		ModContainer modContainer = FabricLoader.getInstance().getModContainer("my-fancy-mod").get();
		MixinSelectConfig selectConfig = MixinSelectConfig.loadMixinSelectConfig(modContainer);
		HashMap<String, Version> modsWithVersion = ModVersionHelper.getAllModsWithVersion(FabricLoader.getInstance(), true);
		mixinClasses = selectConfig.getAllowedMixins(mixinPackage, this.getClass().getClassLoader(), modsWithVersion);
	}

	@Override
	public List<String> getMixins() {
		return mixinClasses == null ? null : (mixinClasses.isEmpty() ? null : mixinClasses);
	}

	// Other method implementations of IMixinConfigPlugin

}
```
