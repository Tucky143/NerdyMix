package net.tucky143.nerdy.parts;

import net.mcreator.element.ModElementType;
import net.tucky143.nerdy.elements.EndBiome;
import net.tucky143.nerdy.elements.Endstone;
import net.mcreator.element.ModElementTypeLoader;
import net.mcreator.generator.GeneratorFlavor;
import net.tucky143.nerdy.elements.*;
import net.tucky143.nerdy.ui.modgui.*;

import static net.mcreator.element.ModElementTypeLoader.register;
import static net.mcreator.generator.GeneratorFlavor.BaseLanguage.JAVA;

public class PluginElementTypes {
    public static ModElementType<?> ENDBIOME;
    public static ModElementType<?> ENDSTONE;
    public static ModElementType<?> ANIMATEDBLOCK;
    public static ModElementType<?> ANIMATEDITEM;
    public static ModElementType<?> ANIMATEDENTITY;
    public static ModElementType<?> ANIMATEDARMOR;
    public static ModElementType<?> CURIOSBAUBLE;
    public static ModElementType<?> CURIOSSLOT;
    public static ModElementType<?> BLOCKSTATES;
    public static ModElementType<?> ATTRIBUTE;
    public static ModElementType<?> CONFIG;
    public static ModElementType<?> PARTICLEMODEL;
    public static ModElementType<?> LOOTMODIFIER;

    public static void load() {
        ENDBIOME = register(
                new ModElementType<>("endbiome", (Character) 'E', EndBiomeGUI::new, EndBiome.class)
        );

        ENDSTONE = register(
                new ModElementType<>("endstone", (Character) null, EndstoneGUI::new, Endstone.class)
        );

        ANIMATEDBLOCK = register(
                new ModElementType<>("animatedblock", (Character) null, AnimatedBlockGUI::new, AnimatedBlock.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ANIMATEDITEM = register(
                new ModElementType<>("animateditem", (Character) 'I', AnimatedItemGUI::new, AnimatedItem.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ANIMATEDENTITY = register(
                new ModElementType<>("animatedentity", (Character) 'E', AnimatedEntityGUI::new, AnimatedEntity.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ANIMATEDARMOR = register(
                new ModElementType<>("animatedarmor", (Character) 'A', AnimatedArmorGUI::new, AnimatedArmor.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        CURIOSBAUBLE = register(
                new ModElementType<>("curiosbauble", (Character) 'B', CuriosBaubleGUI::new, CuriosBauble.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        CURIOSSLOT = register(
                new ModElementType<>("curiosslot", (Character) 'S', CuriosSlotGUI::new, CuriosSlot.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        BLOCKSTATES = register(
                new ModElementType<>("blockstates", (Character) null, BlockstatesGUI::new, Blockstates.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        ATTRIBUTE = register(
                new ModElementType<>("attribute", (Character) null, AttributeGUI::new, Attribute.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        CONFIG = register(
                new ModElementType<>("config", (Character) null, ConfigGUI::new, Config.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        PARTICLEMODEL = register(
                new ModElementType<>("particlemodel", (Character) null, ParticleModelGUI::new, ParticleModel.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));

        LOOTMODIFIER = register(
                new ModElementType<>("lootmodifier", (Character) 'L', LootModifierGUI::new, LootModifier.class)
        ).coveredOn(GeneratorFlavor.baseLanguage(JAVA));
    }


}
