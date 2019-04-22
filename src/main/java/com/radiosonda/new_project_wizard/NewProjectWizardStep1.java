package com.radiosonda.new_project_wizard;

import com.radiosonda.wizard.ContextDialog;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;

/**
 * @author manuel
 */
public class NewProjectWizardStep1 extends ContextDialog {

    private final NewProjectWizardStep1Controller controller;

    public NewProjectWizardStep1() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewProjectWizardStep1.fxml"));
        controller = new NewProjectWizardStep1Controller();
        loader.setController(controller);
        DialogPane pane = loader.load();

        this.setDialogPane(pane);
        this.setResultConverter((param) -> {
            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                HashMap<String, String> result = new HashMap<>();
                result.put("user", controller.userProperty().get());
                result.put("probe", controller.probeProperty().get());
                return result;
            } else {
                return null;
            }

        });
    }
}
