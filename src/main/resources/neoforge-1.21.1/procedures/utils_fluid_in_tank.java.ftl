<#include "mcelements.ftl">
<@addTemplate file="templates/get_fluid_in_tank.java.ftl"/>

(
    getFluidInTank(world, ${toBlockPos(input$x, input$y, input$z)}, ${opt.toInt(input$tankid)})
)
