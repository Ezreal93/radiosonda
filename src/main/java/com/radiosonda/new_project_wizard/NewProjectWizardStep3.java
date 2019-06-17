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
public class NewProjectWizardStep3 extends ContextDialog {

    private final NewProjectWizardStep3Controller controller;
    
    public NewProjectWizardStep3() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewProjectWizardStep3.fxml"));
        controller = new NewProjectWizardStep3Controller();
        loader.setController(controller);
        DialogPane pane = loader.load();
        
        this.setDialogPane(pane);
        this.setResultConverter((param) -> {
            controller.subscription.dispose();
            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return new HashMap<>();
            } else {
                return null;
            }

        });
    }
    
    @Override
    public void setContext(HashMap<String, String> result){
        super.setContext(result);
        this.controller.setSelectedPort(this.result.get("port"));
    }
}
