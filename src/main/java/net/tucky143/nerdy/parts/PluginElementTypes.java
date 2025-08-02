package net.tucky143.nerdy.parts;

import net.mcreator.element.ModElementType;
import net.tucky143.nerdy.elements.EndBiome;
import net.tucky143.nerdy.elements.EndBiomeGUI;
import net.tucky143.nerdy.elements.Endstone;
import net.tucky143.nerdy.elements.EndstoneGUI;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> ENDBIOME;
    public static ModElementType<?> ENDSTONE;

    public static void load() {

        ENDBIOME = register(
                new ModElementType<>("endbiome", (Character) 'E', EndBiomeGUI::new, EndBiome.class)
        );

        ENDSTONE = register(
                new ModElementType<>("endstone", (Character) null, EndstoneGUI::new, Endstone.class)
        );

    }

}
