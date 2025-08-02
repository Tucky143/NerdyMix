package net.tucky143.nerdy.parts;

import net.mcreator.element.ModElementType;
import net.tucky143.nerdy.elements.*;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> ENDBIOME;
    public static ModElementType<?> ENDSTONE;
    public static ModElementType<?> JEIRECIPETYPE;
    public static ModElementType<?> JEIRECIPE;
    public static ModElementType<?> ANVILRECIPE;
    public static ModElementType<?> JEIINFORMATION;

    public static void load() {
        JEIRECIPETYPE = register(
                new ModElementType<>("jeirecipetype", (Character) 'C', JeiRecipeTypeGUI::new, JeiRecipeType.class)
        );

        JEIRECIPE = register(
                new ModElementType<>("jeirecipe", (Character) 'R', JeiRecipeGUI::new, JeiRecipe.class)
        );

        ANVILRECIPE = register(
                new ModElementType<>("anvilrecipe", (Character) 'A', AnvilRecipeGUI::new, AnvilRecipe.class)
        );

        JEIINFORMATION = register(
                new ModElementType<>("jeiinformation", (Character) 'I', JeiInformationGUI::new, JeiInformation.class)
        );

        ENDBIOME = register(
                new ModElementType<>("endbiome", (Character) 'E', EndBiomeGUI::new, EndBiome.class)
        );

        ENDSTONE = register(
                new ModElementType<>("endstone", (Character) null, EndstoneGUI::new, Endstone.class)
        );

    }

}
