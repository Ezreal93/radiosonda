package com.radiosonda.new_project_wizard;

import com.radiosonda.serialrx.SimpleSerialPort;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;

/**
 * FXML Controller class
 *
 * @author manuel
 */
public class NewProjectWizardStep2Controller implements Initializable {

    public String port;
     
    @FXML
    private DialogPane pane;
    
    @FXML
    private ComboBox<String> portSelector;
    
    @FXML
    void onPortSelected(ActionEvent event) {        
            this.port = portSelector.getValue();            
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.getButtonTypes()
                .add(new ButtonType("Siguiente", ButtonBar.ButtonData.OK_DONE));
        pane.getButtonTypes()
                .add(new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE));
        
        String[] ports = SimpleSerialPort.getPorts();
        ObservableList<String> options = FXCollections.observableArrayList(ports);
        portSelector.setItems(options);
        System.out.println("select initilized");
    }    
    
}
