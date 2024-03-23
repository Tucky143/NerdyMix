package net.nerdypuzzle.forgemixins.element;

import net.mcreator.generator.GeneratorTemplate;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.ide.CodeEditorView;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.views.ViewBase;
import net.mcreator.workspace.elements.FolderElement;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MixinGUI extends ModElementGUI<Mixin> {
    private final CodeEditorView codeEditorView;
    private Mixin generatableElement;

    public MixinGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        generatableElement = new Mixin(modElement);
        List<File> modElementFiles = mcreator.getGenerator().getModElementGeneratorTemplatesList(generatableElement).stream().map(GeneratorTemplate::getFile).toList();
        File modElementFile = (File)modElementFiles.get(0);
        if (!editingMode) {
            modElement.setParentFolder(FolderElement.dummyFromPath(modElement.getFolderPath()));
            mcreator.getGenerator().generateElement(generatableElement);
            mcreator.getWorkspace().addModElement(modElement);
            mcreator.getWorkspace().getModElementManager().storeModElement(generatableElement);
            modElement.setCodeLock(true);
        }
        this.codeEditorView = new CodeEditorView(mcreator, modElementFile);

        this.initGUI();
        super.finalizeGUI();
    }

    public ViewBase showView() {
        return this.codeEditorView.showView();
    }
    protected void initGUI() {
    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult.PASS();
    }

    public void openInEditingMode(Mixin generatableElement) {
    }

    public Mixin getElementFromGUI() {
        return new Mixin(modElement);
    }
}
