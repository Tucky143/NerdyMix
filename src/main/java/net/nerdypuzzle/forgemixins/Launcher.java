package net.nerdypuzzle.forgemixins;

import freemarker.template.Template;
import net.mcreator.element.ModElementType;
import net.mcreator.generator.Generator;
import net.mcreator.generator.template.InlineTemplatesHandler;
import net.mcreator.generator.template.base.BaseDataModelProvider;
import net.mcreator.io.FileIO;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.nerdypuzzle.forgemixins.element.Mixin;
import net.nerdypuzzle.forgemixins.element.MixinGUI;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static net.mcreator.element.ModElementTypeLoader.register;

public class Launcher extends JavaPlugin {

	private static final Logger LOG = LogManager.getLogger("Forge mixins");

	public Launcher(Plugin plugin) {
		super(plugin);

		addListener(MCreatorLoadedEvent.class, event -> {
			Generator currentGenerator = event.getMCreator().getGenerator();
			if (currentGenerator != null) {
				Set<String> fileNames = PluginLoader.INSTANCE.getResourcesInPackage(currentGenerator.getGeneratorName() + ".workspacebase");
				Map<String, Object> dataModel = (new BaseDataModelProvider(event.getMCreator().getWorkspace().getGenerator())).provide();
				Iterator var4 = fileNames.iterator();

				while(var4.hasNext()) {
					String file = (String)var4.next();
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
		});

		addListener(PreGeneratorsLoadingEvent.class, event -> register(new ModElementType<>("mixin", 'M', MixinGUI::new, Mixin.class)));

		LOG.info("Forge mixins plugin was loaded");
	}

}