(
    new Object() {
        public ItemStack get(List<ItemStack> inputList, int i) {
            if (inputList.size() > i) {
                return ${input$list}.get(${input$index});
            }
            return ItemStack.EMPTY;
        }
    }.get(${input$list}, ${input$index})
)