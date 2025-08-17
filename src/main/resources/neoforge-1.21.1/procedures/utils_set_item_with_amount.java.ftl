<#include "mcelements.ftl">
<#include "mcitems.ftl">
<@addTemplate file="templates/set_item_with_amount.java.ftl"/>
{
    setItemWithAmount(world, ${toBlockPos(input$x, input$y, input$z)}, ${mappedMCItemToItemStackCode(input$item)}, ${opt.toInt(input$slotid)});
}