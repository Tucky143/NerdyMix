package net.tucky143.nerdy.parts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mcreator.io.FileIO;
import net.mcreator.ui.MCreator;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.nerdy.elements.CuriosBauble;
import net.tucky143.nerdy.elements.CuriosSlot;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CuriosSlotManager {

    public static void createBaubleFile(MCreator mcreator) {
        Set<String> addedSlotTypes = new HashSet<>();
        String namespace = mcreator.getWorkspace().getWorkspaceSettings().getModID();
        File workspaceFolder = mcreator.getWorkspace().getWorkspaceFolder();

        for (ModElement element : mcreator.getWorkspace().getModElements()) {
            if (element.getGeneratableElement() instanceof CuriosBauble slot) {
                String type = slot.slotType;
                int amount = slot.slotAmount;

                String normalizedType = type;
                String checkType = type.toUpperCase();
                if (checkType.equals("HEAD") || checkType.equals("NECKLACE") || checkType.equals("BACK") ||
                        checkType.equals("BODY") || checkType.equals("BRACELET") || checkType.equals("HANDS") ||
                        checkType.equals("RING") || checkType.equals("BELT") || checkType.equals("CHARM") ||
                        checkType.equals("CURIO")) {
                    normalizedType = type.toLowerCase();
                }

                // Prevent duplicate file creation
                if (addedSlotTypes.add(normalizedType)) {
                    JsonObject slotJson = new JsonObject();
                    slotJson.addProperty("size", amount);

                    File slotFile = new File(workspaceFolder, "src/main/resources/data/" + namespace + "/curios/slots/" + normalizedType + ".json");
                    slotFile.getParentFile().mkdirs();
                    FileIO.writeStringToFile(slotJson.toString(), slotFile);

                    System.out.println("Generated: " + slotFile.getAbsolutePath());
                }
                createEntityFileWithSelectedSlot(mcreator, slot);
            }
        }
        mcreator.reloadWorkspaceTabContents();
    }
    public static void createSlotFile(MCreator mcreator){
        String namespace = mcreator.getWorkspace().getWorkspaceSettings().getModID();
        File workspaceFolder = mcreator.getWorkspace().getWorkspaceFolder();

        for (ModElement element : mcreator.getWorkspace().getModElements()) {
            if (element.getGeneratableElement() instanceof CuriosSlot slot) {
                String registryName = element.getRegistryName();
                JsonObject curiosSlotJson = getJsonObject(slot, registryName);

                // File Path: .../curios/entities/(registryname)_curios_slot.json
                File targetFile = new File(workspaceFolder, "src/main/resources/data/" + namespace + "/curios/entities/" + registryName + "_curios_slot.json");
                targetFile.getParentFile().mkdirs();
                FileIO.writeStringToFile(curiosSlotJson.toString(), targetFile);

                System.out.println("Generated Curios Entity Slot file: " + targetFile.getAbsolutePath());
            }
        }
        mcreator.reloadWorkspaceTabContents();
    }

    private static @NotNull JsonObject getJsonObject(CuriosSlot slot, String registryName) {
        String additionalEntity = String.valueOf(slot.entities);  // Adjust this if the field is named differently

        JsonObject curiosSlotJson = new JsonObject();

        // Entities Array: ["player", slot.entities]
        JsonArray entitiesArray = new JsonArray();
        entitiesArray.add("player");  // always add player
        addEntities(entitiesArray, slot.entities);


        // Slots Array: [registryName]
        JsonArray slotsArray = new JsonArray();
        slotsArray.add(registryName);

        curiosSlotJson.add("entities", entitiesArray);
        curiosSlotJson.add("slots", slotsArray);
        return curiosSlotJson;
    }

    private static void addEntities(JsonArray entitiesArray, List<String> entities) {
        if (entities == null || entities.isEmpty()) return;

        for (String entity : entities) {
            if (entity != null && !entity.trim().isEmpty()) {
                entitiesArray.add(entity.trim());
            }
        }
    }
    private static void createEntityFileWithSelectedSlot(MCreator mcreator, CuriosBauble bauble) {
        JsonObject json = new JsonObject();

        // Entities array with only "player"
        JsonArray entitiesArray = new JsonArray();
        entitiesArray.add("player");

        String namespace = mcreator.getWorkspace().getWorkspaceSettings().getModID();
        File workspaceFolder = mcreator.getWorkspace().getWorkspaceFolder();
        String selectedSlotType = bauble.slotType;
        String registryName = bauble.getModElement().getRegistryName();
        // Slots array with selectedSlotType (from GUI)
        JsonArray slotsArray = new JsonArray();
        if (selectedSlotType != null && !selectedSlotType.isEmpty()) {
            slotsArray.add(selectedSlotType.toLowerCase());
        }

        json.add("entities", entitiesArray);
        json.add("slots", slotsArray);

        // Create the file path
        File file = new File(workspaceFolder, "src/main/resources/data/" + namespace + "/curios/entities/" + registryName + "_curios_entity.json");
        file.getParentFile().mkdirs();

        FileIO.writeStringToFile(json.toString(), file);

        System.out.println("Created entity file for bauble: " + file.getAbsolutePath());
    }


}
