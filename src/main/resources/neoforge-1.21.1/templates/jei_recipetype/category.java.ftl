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
import mezz.jei.api.gui.IRecipeSlotsView;
import mezz.jei.api.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class ${name}RecipeCategory implements IRecipeCategory<${name}Recipe> {
    public static final ResourceLocation UID = ResourceLocation.parse("${modid}:${data.registryPath}");
    public static final ResourceLocation TEXTURE = ResourceLocation.parse("${modid}:textures/screens/${data.textureSelector}");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic slotDrawable;

    public ${name}RecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, ${data.width}, ${data.height});
        this.icon = helper.createDrawable(TEXTURE, 0, 0, 16, 16);

        // Use JEI's built-in slot drawable (matches vanilla Minecraft style)
        this.slotDrawable = helper.getSlotDrawable();
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
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public void draw(${name}Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // Draw the background texture
        this.background.draw(guiGraphics);

        // Render tooltips for hovered ingredients or slots
        recipeSlotsView.drawTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ${name}Recipe recipe, IFocusGroup focuses) {
        <#list data.slotList as slot>
            if ("INPUT".equals("${slot.type}")) {
                builder.addSlot(RecipeIngredientRole.INPUT, ${slot.x}, ${slot.y})
                       .setBackground(this.slotDrawable, -1, -1)
                       .addIngredients(recipe.getIngredients().get(${slot.slotid}));
            } else {
                builder.addSlot(RecipeIngredientRole.OUTPUT, ${slot.x}, ${slot.y})
                       .setBackground(this.slotDrawable, -1, -1)
                       .addItemStack(recipe.getResultItem(Minecraft.getInstance().level));
            }
        </#list>
    }
}