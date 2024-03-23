package net.nerdypuzzle.forgemixins.element;

import net.mcreator.element.GeneratableElement;
import net.mcreator.workspace.elements.ModElement;

import java.util.List;

public class Mixin extends GeneratableElement {

    public List<String> mixins = List.of(getModElement().getName() + "Mixin");
    public Mixin(ModElement element) {
        super(element);
    }

}
