package ${package}.jei_recipes;

<#compress>

import javax.annotation.Nullable;

public record ${name}Recipe(
    <#list data.slotList as slot>
        <#if slot.type == "ITEM_INPUT">
            SizedIngredient ${slot.name}ItemInput,
        <#elseif slot.type == "FLUID_INPUT">
            FluidStack ${slot.name}FluidInput,
        </#if>
    </#list>
    SizedIngredient output
) implements Recipe<RecipeInput> {

    @Override
    public @Nonnull NonNullList<Ingredient> getIngredients() {
        List<SizedIngredient> sizedIngredients = new ArrayList<>();
        <#list data.slotList as slot>
            <#if slot.type == "ITEM_INPUT">
                sizedIngredients.add(${slot.name}ItemInput);
            </#if>
        </#list>

        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(sizedIngredients.size());
        for(SizedIngredient sizedIngredient : sizedIngredients) {
            ingredients.add(sizedIngredient.ingredient());
        }
        return ingredients;
    }

    @Override
    public boolean matches(@NotNull RecipeInput pContainer, @NotNull Level Level) {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack getResult(int resultIndex) {
        ItemStack[] stack = output.getItems();
        return stack[0];
    }

    @Override
    public ItemStack assemble(@NotNull RecipeInput input, @NotNull HolderLookup.Provider holder) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<${name}Recipe> {
        private Type(){}
        public static final RecipeType<${name}Recipe> INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<${name}Recipe> {
        public static final Serializer INSTANCE = new Serializer();

        public final MapCodec<${name}Recipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                <#list data.slotList as slot>
                    <#if slot.type == "ITEM_INPUT">
                        <#if slot.optional>
                            SizedIngredient.FLAT_CODEC.optionalFieldOf("${slot.name}", new SizedIngredient(Ingredient.EMPTY, 1)).forGetter(${name}Recipe::${slot.name}ItemInput),
                        <#else>
                            SizedIngredient.FLAT_CODEC.fieldOf("${slot.name}").forGetter(${name}Recipe::${slot.name}ItemInput),
                        </#if>
                    <#elseif slot.type == "FLUID_INPUT">
                        FluidStack.CODEC.fieldOf("${slot.name}").forGetter(${name}Recipe::${slot.name}FluidInput),
                    </#if>
                </#list>
                SizedIngredient.FLAT_CODEC.fieldOf("output").forGetter(${name}Recipe::output)
            ).apply(instance, Serializer::create${name}Recipe)
        );

        private static final StreamCodec<RegistryFriendlyByteBuf, ${name}Recipe> STREAM_CODEC = StreamCodec.of(
            Serializer::write,
            Serializer::read
        );

        @Override
        public @NotNull MapCodec<${name}Recipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ${name}Recipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ${name}Recipe read(RegistryFriendlyByteBuf buffer) {
            <#list data.slotList as slot>
                <#if slot.type == "ITEM_INPUT">
                    SizedIngredient ${slot.name}ItemInput = SizedIngredient.STREAM_CODEC.decode(buffer);
                <#elseif slot.type == "FLUID_INPUT">
                    FluidStack ${slot.name}FluidInput = FluidStack.STREAM_CODEC.decode(buffer);
                </#if>
            </#list>
            SizedIngredient output = SizedIngredient.STREAM_CODEC.decode(buffer);
            return new ${name}Recipe(
                <#list data.slotList as slot>
                    <#if slot.type == "ITEM_INPUT">
                        ${slot.name}ItemInput,
                    <#elseif slot.type == "FLUID_INPUT">
                        ${slot.name}FluidInput,
                    </#if>
                </#list>
                output
            );
        }

        private static void write(RegistryFriendlyByteBuf buffer, ${name}Recipe recipe) {
            <#list data.slotList as slot>
                <#if slot.type == "ITEM_INPUT">
                    SizedIngredient.STREAM_CODEC.encode(buffer, recipe.${slot.name}ItemInput);
                <#elseif slot.type == "FLUID_INPUT">
                    FluidStack.STREAM_CODEC.encode(buffer, recipe.${slot.name}FluidInput);
                </#if>
            </#list>
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.output);
        }

        static ${name}Recipe create${name}Recipe(
            <#list data.slotList as slot>
                <#if slot.type == "ITEM_INPUT">
                    SizedIngredient ${slot.name}ItemInput,
                <#elseif slot.type == "FLUID_INPUT">
                    FluidStack ${slot.name}FluidInput,
                </#if>
            </#list>
            SizedIngredient output
        ) {
            return new ${name}Recipe(
                <#list data.slotList as slot>
                    <#if slot.type == "ITEM_INPUT">
                        ${slot.name}ItemInput,
                    <#elseif slot.type == "FLUID_INPUT">
                        ${slot.name}FluidInput,
                    </#if>
                </#list>
                output
            );
        }
    }
}</#compress>