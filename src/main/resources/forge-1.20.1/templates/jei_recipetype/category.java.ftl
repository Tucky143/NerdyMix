<#include "../mcitems.ftl">

package ${package}.jei_recipes;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class ${name}RecipeCategory implements IRecipeCategory<${name}Recipe> {
    public final static ResourceLocation UID = new ResourceLocation("${modid}", "${data.getModElement().getRegistryName()}");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation("${modid}", "textures/screens/${data.textureSelector}");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic slotDrawable;

    public ${name}RecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, ${data.width}, ${data.height});
        this.slotDrawable = helper.getSlotDrawable(); // Default Minecraft slot background
        this.icon = helper.createDrawable(TEXTURE, 0, 0, 16, 16); // Small icon from your texture
    }

    @Override
    public RecipeType<${name}Recipe> getRecipeType() {
        return ${JavaModName}JeiPlugin.${name}_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("${data.title}");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ${name}Recipe recipe, IFocusGroup focuses) {
        <#list data.slotList as slot>
            // Add the slot background and item at the same time
            if ("INPUT".equals("${slot.type}")) {
                builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y})
                       .setBackground(this.slotDrawable, -1, -1)
                       .addIngredients(recipe.getIngredients().get(${slot.slotid}));
            } else {
                builder.addSlot(RecipeIngredientRole.OUTPUT, ${slot.x}, ${slot.y})
                       .setBackground(this.slotDrawable, -1, -1)
                       .addItemStack(recipe.getResultItem(null));
            }
        </#list>
    }
}
