package com.radiosonda.new_project_wizard;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import com.radiosonda.wizard.ContextDialog;

/**
 * @author manuel
 */
public class NewProjectWizardStep2 extends ContextDialog {

    private final NewProjectWizardStep2Controller controller;
    
    public NewProjectWizardStep2() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewProjectWizardStep2.fxml"));
        controller = new NewProjectWizardStep2Controller();
        loader.setController(controller);
        DialogPane pane = loader.load();     
        
        this.setDialogPane(pane);   
        this.setResultConverter((param) -> {
            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE && controller.port != null) {
                HashMap<String, String> result = new HashMap<>();
                result.put("port", controller.port);
                return result;            
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Data unavailable!");
                alert.showAndWait();
                return null;
            }
        });
    }
}
