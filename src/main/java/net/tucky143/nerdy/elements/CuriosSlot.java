package net.tucky143.nerdy.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.workspace.elements.ModElement;

import java.util.ArrayList;
import java.util.List;

public class CuriosSlot extends GeneratableElement {

    public String texture;
    public String name;
    public int amount;
    public List<String> entities = new ArrayList<>();  // <-- Add this line
    public CuriosSlot(ModElement element) {
        super(element);
    }

}
