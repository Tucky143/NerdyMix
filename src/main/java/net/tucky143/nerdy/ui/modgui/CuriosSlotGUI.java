package net.tucky143.nerdy.ui.modgui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mcreator.element.parts.TextureHolder;
import net.mcreator.generator.GeneratorUtils;
import net.mcreator.io.FileIO;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TypedTextureSelectorDialog;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.TextureSelectionButton;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.ui.workspace.resources.ResourceFilterModel;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.ui.workspace.resources.WorkspacePanelResources;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.nerdy.elements.CuriosSlot;
import net.tucky143.nerdy.parts.CuriosSlotManager;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CuriosSlotGUI extends ModElementGUI<CuriosSlot> {

    private TextureSelectionButton texture;
    private final VTextField name;
    private final JSpinner amount;
    private final JTextArea entitiesField;

    private final ValidationGroup page1group = new ValidationGroup();

    public CuriosSlotGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        name = new VTextField(17);
        amount = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        entitiesField = new JTextArea(3, 20);
        entitiesField.setLineWrap(true);
        entitiesField.setWrapStyleWord(true);
        this.initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        texture = new TextureSelectionButton(new TypedTextureSelectorDialog(this.mcreator, TextureType.SCREEN));
        this.texture.setOpaque(false);

        JComponent textureComponent = PanelUtils.totalCenterInPanel(
                ComponentUtils.squareAndBorder(
                        HelpUtils.wrapWithHelpButton(this.withEntry("curios/slot_texture"), this.texture),
                        L10N.t("elementgui.common.texture")
                )
        );

        JPanel pane1 = new JPanel(new BorderLayout());
        pane1.setOpaque(false);
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 0, 2));
        mainPanel.setOpaque(false);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/slot_name"), L10N.label("elementgui.curiosslot.slot_name")));
        mainPanel.add(name);
        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/custom_slot_amount"), L10N.label("elementgui.curiosslot.slot_amount")));
        mainPanel.add(amount);

        mainPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("curios/entities"), L10N.label("elementgui.curiosslot.entities")));
        mainPanel.add(new JScrollPane(entitiesField));

        name.setValidator(new TextFieldValidator(this.name, L10N.t("elementgui.curiosslot.needs_name")));
        name.enableRealtimeValidation();
        page1group.addValidationElement(name);
        page1group.addValidationElement(texture);

        if (!this.isEditingMode()) {
            String readableNameFromModElement = net.mcreator.util.StringUtils.machineToReadableName(this.modElement.getName());
            name.setText(readableNameFromModElement);
        }

        pane1.add("Center", PanelUtils.totalCenterInPanel(PanelUtils.northAndCenterElement(textureComponent, mainPanel)));
        addPage(pane1);
    }

    protected AggregatedValidationResult validatePage(int page) {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("curios_api"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.curiosbauble.needs_api"));
        return new AggregatedValidationResult(new ValidationGroup[]{this.page1group});
    }

    public void openInEditingMode(CuriosSlot slot) {
        texture.setTexture(new TextureHolder(getModElement().getWorkspace(), StringUtils.removeEnd(slot.texture, ".png")));
        name.setText(slot.name);
        amount.setValue(slot.amount);

        // Fill entities field
        StringBuilder entitiesText = new StringBuilder();
        for (String entity : slot.entities) {
            entitiesText.append(entity).append("\n");
        }
        entitiesField.setText(entitiesText.toString());
    }

    @Override
    protected void afterGeneratableElementStored() {
        // Copy texture as before
        if (texture.hasTexture()) {
            FileIO.copyFile(
                    new File(GeneratorUtils.getSpecificRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration(), "mod_assets_root"),
                            "textures/screens/" + texture.getTextureHolder().name() + ".png"),
                    new File(GeneratorUtils.getResourceRoot(mcreator.getWorkspace(), mcreator.getWorkspace().getGeneratorConfiguration()),
                            "assets/curios/textures/slot/" + texture.getTextureHolder().name() + ".png")
            );
        }
        CuriosSlotManager.createSlotFile(mcreator);
    }


    public CuriosSlot getElementFromGUI() {
        CuriosSlot slot = new CuriosSlot(this.modElement);
        slot.texture = texture.getTextureHolder().name() + ".png";
        slot.name = name.getText();
        slot.amount = (int) amount.getValue();

        // Parse entities input (split by commas or line breaks)
        String[] entitiesInput = entitiesField.getText().split("[,\\n]+");
        for (String e : entitiesInput) {
            if (!e.trim().isEmpty()) {
                slot.entities.add(e.trim());
            }
        }

        return slot;
    }

    @Override
    public URI contextURL() throws URISyntaxException {
        return null;
    }
}
