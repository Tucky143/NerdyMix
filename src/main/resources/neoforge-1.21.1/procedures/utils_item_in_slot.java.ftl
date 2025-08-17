<#include "mcelements.ftl">
<@addTemplate file="templates/get_item_in_slot.java.ftl"/>
/*@ItemStack*/
(
    getItemInSlot(world, ${toBlockPos(input$x, input$y, input$z)}, ${opt.toInt(input$slotid)})
)