package net.tucky143.nerdy;

import freemarker.template.Template;
import net.mcreator.element.ModElementType;
import net.mcreator.generator.Generator;
import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.generator.template.InlineTemplatesHandler;
import net.mcreator.generator.template.base.BaseDataModelProvider;
import net.mcreator.io.FileIO;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.mcreator.plugin.events.ui.ModElementGUIEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.ui.modgui.BiomeGUI;
import net.mcreator.ui.modgui.ModElementGUI;
import net.tucky143.nerdy.elements.EndBiomeGUI;
import net.tucky143.nerdy.elements.Mixin;
import net.tucky143.nerdy.elements.MixinGUI;
import net.tucky143.nerdy.parts.PluginElementTypes;
import net.tucky143.nerdy.parts.PluginActions;
import net.tucky143.nerdy.parts.PluginEventTriggers;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static net.mcreator.element.ModElementTypeLoader.register;

public class Launcher extends JavaPlugin {

	private static final Logger LOG = LogManager.getLogger("Mixins");
	public static PluginActions ACTION_REGISTRY;
	public static Set<Plugin> PLUGIN_INSTANCE = new HashSet<>();
	public static void disableComponent(ModElementGUI gui, Field field) throws Exception {
		field.setAccessible(true);
		((JComponent)field.get(gui)).setEnabled(false);
	}

	public Launcher(Plugin plugin) {
		super(plugin);
		PLUGIN_INSTANCE.add(plugin);

		addListener(ModElementGUIEvent.BeforeLoading.class, event -> SwingUtilities.invokeLater(() -> {
			PluginEventTriggers.dependencyWarning(event.getMCreator(), event.getModElementGUI());
			PluginEventTriggers.interceptProcedurePanel(event.getMCreator(), event.getModElementGUI());
		}));

		addListener(MCreatorLoadedEvent.class, event -> {
			ACTION_REGISTRY = new PluginActions(event.getMCreator());
			SwingUtilities.invokeLater(() -> PluginEventTriggers.modifyMenus(event.getMCreator()));
			Generator currentGenerator = event.getMCreator().getGenerator();
			if (currentGenerator != null) {
				if (currentGenerator.getGeneratorConfiguration().getGeneratorFlavor() == GeneratorFlavor.FORGE) {
					Set<String> fileNames = PluginLoader.INSTANCE.getResourcesInPackage(currentGenerator.getGeneratorName() + ".workspacebase");
					Map<String, Object> dataModel = (new BaseDataModelProvider(event.getMCreator().getWorkspace().getGenerator())).provide();
					Iterator var4 = fileNames.iterator();

					while (var4.hasNext()) {
						String file = (String) var4.next();
						if (file.contains("build.gradle")) {
							InputStream stream = PluginLoader.INSTANCE.getResourceAsStream(file);
							File generatorFile = new File(event.getMCreator().getWorkspace().getWorkspaceFolder(), file.replace(currentGenerator.getGeneratorName() + "/workspacebase", ""));
							try {
								String contents = IOUtils.toString(stream, StandardCharsets.UTF_8);
								Template freemarkerTemplate = InlineTemplatesHandler.getTemplate(contents);
								StringWriter stringWriter = new StringWriter();
								freemarkerTemplate.process(dataModel, stringWriter, InlineTemplatesHandler.getConfiguration().getObjectWrapper());
								FileIO.writeStringToFile(stringWriter.getBuffer().toString(), generatorFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		});

		addListener(PreGeneratorsLoadingEvent.class, event -> register(new ModElementType<>("mixin", 'M', MixinGUI::new, Mixin.class)));

		addListener(PreGeneratorsLoadingEvent.class, event -> PluginElementTypes.load());
		addListener(ModElementGUIEvent.AfterLoading.class, event -> {
			if (event.getModElementGUI() instanceof BiomeGUI biome) {
				if (EndBiomeGUI.isEndBiome(biome.getElementFromGUI().getModElement().getName(), null, event.getMCreator())) {
					try {
						disableComponent(biome, BiomeGUI.class.getDeclaredField("spawnBiome"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("spawnBiomeNether"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("spawnInCaves"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("underwaterBlock"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genTemperature"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genHumidity"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genContinentalness"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genErosion"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("genWeirdness"));
						disableComponent(biome, BiomeGUI.class.getDeclaredField("treesPerChunk"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		LOG.info("End biomes plugin was loaded");
		LOG.info("Mixins plugin was loaded");
	}

}