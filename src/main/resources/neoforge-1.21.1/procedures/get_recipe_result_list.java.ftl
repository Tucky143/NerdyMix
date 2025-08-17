<#include "mcitems.ftl">
<#assign recipeName = field$recipe?replace("CUSTOM:", "")>

<#assign typeArray = field_list$type>
<#assign nameArray = field_list$name>
<#assign consumeArray = field_list$consume>

(
    new Object() {
        public List<ItemStack> getResultList() {
            List<ItemStack> itemStackResultList = new ArrayList<>();
            if(world instanceof Level level) {
                net.minecraft.world.item.crafting.RecipeManager manager = level.getRecipeManager();
                List<${recipeName}Recipe> recipes = manager.getAllRecipesFor(${recipeName}Recipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());

                boolean recipeInputNotConsumed = true;

                <#list input_list$entry as entry>
                    <#assign i = entry?index>

                    <#if typeArray[i] == "MCItem">
                        ItemStack ${nameArray[i]}ItemStack = ${entry};
                    <#elseif typeArray[i] == "FluidStack">
                        FluidStack ${nameArray[i]}FluidStack = ${entry};
                    </#if>
                </#list>

                for(${recipeName}Recipe recipe : recipes) {
                    boolean _itemMatch = true
                        <#list input_list$entry as entry>
                            <#assign i = entry?index>

                            <#if typeArray[i] == "MCItem">
                                 && (${nameArray[i]}ItemStack.isEmpty()) ? recipe.${nameArray[i]}ItemInput().ingredient().isEmpty() : recipe.${nameArray[i]}ItemInput().test(${nameArray[i]}ItemStack)
                            </#if>
                        </#list>
                    ;
                    boolean _fluidMatch = true
                        <#list input_list$entry as entry>
                            <#assign i = entry?index>

                            <#if typeArray[i] == "FluidStack">
                                 && SizedFluidIngredient.of(recipe.${nameArray[i]}FluidInput()).test(${nameArray[i]}FluidStack)
                            </#if>
                        </#list>
                    ;

                    if(_itemMatch && _fluidMatch) {
                        itemStackResultList.add(recipe.getResult(0));

                        if(recipeInputNotConsumed) {
                            <#list input_list$entry as entry>
                                <#assign i = entry?index>

                                <#if consumeArray[i] == "TRUE">
                                    <#if typeArray[i] == "MCItem">
                                        ${nameArray[i]}ItemStack.shrink(recipe.${nameArray[i]}ItemInput().count());
                                    <#elseif typeArray[i] == "FluidStack">
                                        ${nameArray[i]}FluidStack.shrink(recipe.${nameArray[i]}FluidInput().getAmount());
                                    </#if>
                                </#if>
                            </#list>

                            recipeInputNotConsumed = false;
                        }
                    }
                }
            }
            return itemStackResultList;
        }
    }.getResultList()
)