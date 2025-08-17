Blockly.Blocks["ingredient_list_mutator_container"] = {
    init: function () {
        this.appendDummyInput()
            .appendField(javabridge.t("blockly.block.ingredient_list_mutator.container"));
        this.appendStatementInput("STACK");
        this.contextMenu = false;
        this.setColour("#a55b67");
    }
};

Blockly.Blocks["ingredient_list_mutator_input"] = {
    init: function () {
        this.appendDummyInput()
            .appendField(javabridge.t("blockly.block.ingredient_list_mutator.ingredient"));
        this.setPreviousStatement(true);
        this.setNextStatement(true);
        this.contextMenu = false;
        this.setColour("#a55b67");
    }
};

Blockly.Extensions.registerMutator(
    "ingredient_list_mutator",
    simpleRepeatingInputMixin(
        "ingredient_list_mutator_container",
        "ingredient_list_mutator_input",
        "entry",

        // List item builder
        function (thisBlock, inputName, index) {
            const input = thisBlock
                .appendValueInput(inputName + index)
                .setCheck("MCItem")
                .setAlign(Blockly.Input.Align.RIGHT);

            // Dropdown change input type check
            input.appendField(javabridge.t("blockly.block.ingredient_list.ingredient_type"))
            input.appendField(
                new Blockly.FieldDropdown(
                    [
                        [ "Item", "MCItem" ],
                        [ "Fluid", "FluidStack" ]
                    ],
                    function (newType) {
                        this.getSourceBlock()
                            .getInput(inputName + index)
                            .setCheck(newType);
                    }
                ),
                "type" + index
            );

            // Ingredient name for input
            input.appendField(javabridge.t("blockly.block.ingredient_list.ingredient_name"));
            input.appendField(
                new Blockly.FieldTextInput("input_name"),
                "name" + index
            );

            // Consume given ItemStack/FluidStack if checked
            input.appendField(javabridge.t("blockly.block.ingredient_list.ingredient_consume"));
            input.appendField(
                new Blockly.FieldCheckbox("FALSE"),
                "consume" + index
            );

            input.appendField(javabridge.t("blockly.block.ingredient_list.ingredient_input"));
        },
        // Returns MCItem instead of dummy input
        true,

        // Field name checks
        [ "type", "name", "consume" ],

        // Disabled if mutator is empty
        true
    ),
    undefined,
    [ "ingredient_list_mutator_input" ]
);

