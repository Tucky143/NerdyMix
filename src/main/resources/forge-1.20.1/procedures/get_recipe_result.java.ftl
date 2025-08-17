<#include "mcitems.ftl">
<#assign recipeName = field$recipe?replace("CUSTOM:", "")>
/*@ItemStack*/

(
    new Object() {
        public ItemStack getResult() {
            if(world instanceof Level level) {
                net.minecraft.world.item.crafting.RecipeManager manager = level.getRecipeManager();
                List<${recipeName}Recipe> recipes = manager.getAllRecipesFor(${recipeName}Recipe.Type.INSTANCE);
                for(${recipeName}Recipe recipe : recipes) {
                    NonNullList<Ingredient> ingredients = recipe.getIngredients();
                    <#list input_list$entry as entry>
                        if(!ingredients.get(${entry?index}).test(${mappedMCItemToItemStackCode(entry)})) {
                            continue;
                        }
                    </#list>
                    return recipe.getResultItem(null);
                }
            }
            return ItemStack.EMPTY;
        }
    }.getResult()
)