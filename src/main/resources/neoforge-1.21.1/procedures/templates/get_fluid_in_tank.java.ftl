private static FluidStack getFluidInTank(LevelAccessor world, BlockPos pos, int tank) {
    FluidStack fluid = FluidStack.EMPTY;
    if(world instanceof ILevelExtension extension) {
        IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
        if(fluidHandler != null) {
            fluid = fluidHandler.getFluidInTank(tank);
        }
    }
    return fluid;
}