private static void setItemWithAmount(LevelAccessor world, BlockPos pos, ItemStack item, int slotid) {
    if(world instanceof ILevelExtension extension) {
        IItemHandler itemHandler = extension.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
        if(itemHandler != null) {
            int itemAmount = itemHandler.getStackInSlot(slotid).getCount() + item.getCount();
            if(itemAmount > 64) {
                itemAmount = 64;
            }

            if(itemHandler instanceof IItemHandlerModifiable modify) {
                modify.setStackInSlot(slotid, new ItemStack(item.getItem(), itemAmount));
            }
        }
    }
}
