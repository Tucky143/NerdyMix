package net.tucky143.nerdy.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.Fluid;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

import java.util.ArrayList;
import java.util.List;

public class JeiRecipe extends GeneratableElement {

    public String category;
    public MItemBlock output;
    public int outputAmount;
    public List<JeiRecipeListEntry> inputs = new ArrayList<>();

    public static class JeiRecipeListEntry {
        public JeiRecipeListEntry() {}

        public String type;
        public String name;
        @ModElementReference
        public MItemBlock itemInput;
        public int itemAmount;
        @ModElementReference
        public Fluid fluidInput;
        public int fluidAmount;
    }

    public JeiRecipe(ModElement element) {
        super(element);
    }

}
