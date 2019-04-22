package com.radiosonda;

import com.radiosonda.wizard.Wizard;
import com.radiosonda.new_project_wizard.NewProjectWizardFactory;
import com.radiosonda.wizard.ContextDialog;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class FXMLController implements Initializable {
    
    private File currentFile;
    
    @FXML
    private MenuBar barMenu;
    
    @FXML
    private LineChart<?, ?> temperatureGraph;

    @FXML
    private LineChart<?, ?> humidityGraph;

    @FXML
    private TextArea temperatureValue;

    @FXML
    private TextArea humidityValue;

    @FXML
    private TextArea pressureValue;

    @FXML
    private Button finishButton;
      
    @FXML
    void createNewProjectAction(ActionEvent event) throws IOException {        
        Wizard wizard = NewProjectWizardFactory.create();       // crea los wizardstep y se los manda a wizard      
        wizard.start().ifPresent((results)->{                   // muestra uno por uno los wizardstep
            System.out.println(results);                        // imprime el hashmap 
            setDisabled(false);
        });
        
        

       
    }
    
    @FXML
    public void openAction(ActionEvent event)  throws IOException{
        chooseFile();
        if(currentFile != null){
            System.out.println("File: " + currentFile.getAbsolutePath());
            FileReader fileReader = new FileReader(currentFile);
            String content = new String(Files.readAllBytes(currentFile.toPath()));
            setWindowTitle(currentFile.getName());
            setDisabled(false);            
        }            
    }
    
    @FXML
    void onCloseAction(ActionEvent event) {
        Platform.exit();
    }
    
    private void chooseFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        currentFile = fileChooser.showOpenDialog(barMenu.getScene().getWindow());
        if(currentFile != null){
            setWindowTitle(currentFile.getName());
        }
    }
 
    private void setWindowTitle(String title){
        Stage stage = (Stage) barMenu.getScene().getWindow();
        stage.setTitle(title);        
    }
    
    void setDisabled(boolean state){
        temperatureGraph.setDisable(state);
        humidityGraph.setDisable(state);
        temperatureValue.setDisable(state);
        humidityValue.setDisable(state);
        pressureValue.setDisable(state);
        finishButton.setDisable(state);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setDisabled(true);
    }
    
}
