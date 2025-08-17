(
    new Object() {
        public ItemStack getRandomResult(List<ItemStack> inputList) {
            if (inputList.size() > 0) {
                int index = Mth.nextInt(RandomSource.create(), 0, inputList.size() - 1);
                return inputList.get(index);
            }
            return ItemStack.EMPTY;
        }
    }.getRandomResult(${input$list})
)