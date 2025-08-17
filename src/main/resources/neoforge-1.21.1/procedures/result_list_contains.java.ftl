<#include "mcitems.ftl">
/*@ItemStack*/

(
    new Object() {
        public boolean contains(List<ItemStack> inputList, ItemStack content) {
            if (inputList.size() > 0) {
                for (ItemStack stack : inputList) {
                    if (stack.is(content.getItem())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }.contains(${input$list}, ${mappedMCItemToItemStackCode(input$element)})
)