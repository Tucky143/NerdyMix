private static ItemStack getItemInSlot(LevelAccessor world, BlockPos pos, int slot) {
    ItemStack item = ItemStack.EMPTY;
	if(world instanceof ILevelExtension extension) {
		IItemHandler itemHandler = extension.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
		if(itemHandler != null) {
		    item = itemHandler.getStackInSlot(slot);
		}
	}
	return item;
}