package com.radiosonda;

import com.radiosonda.serialrx.SondeData;
import com.radiosonda.wizard.Wizard;
import com.radiosonda.new_project_wizard.NewProjectWizardFactory;
import com.radiosonda.wizard.ContextDialog;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.ResourceBundle;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class FXMLController implements Initializable {
    
    private File currentFile;
    
    @FXML
    private MenuBar barMenu;

    @FXML
    private ScatterChart<?, ?> temperatureVsPressure;

    @FXML
    private ScatterChart<?, ?> humidityVsPressure;

    @FXML
    private NumberAxis pressureAxis1;

    @FXML
    private NumberAxis temperatureAxis;

    @FXML
    private NumberAxis pressureAxis2;

    @FXML
    private NumberAxis humidityAxis;



    @FXML
    private TextField temperatureField;

    @FXML
    private TextField humidityField;

    @FXML
    private TextField pressureField;

    @FXML
    private Button finishButton;

    private Disposable subscription;

    private HashMap<String, String> sessionContext;
    private XYChart.Series temperatureVsPressureSeries;
    private XYChart.Series humidityVsPressureSeries;


    @FXML
    void createNewProjectAction(ActionEvent event) throws IOException {        
        Wizard wizard = NewProjectWizardFactory.create();       // crea los wizardstep y se los manda a wizard      
        wizard.start().ifPresent((results)->{                   // muestra uno por uno los wizardstep
            this.sessionContext = results;
            System.out.println(results);                        // imprime el hashmap 
            setDisabled(false);
            subscription = SondeGeneratorContainer.createSondeFlowable().subscribe(this::onSondeDataReceived);
        });
    }

    private void onSondeDataReceived(SondeData sondeData){
        System.out.println(sondeData);
        System.out.println("pirru sucks dick");

        setControlsData(sondeData);
        appendToCharts(sondeData);
        //todo agregar a estructura
    }

    private void setControlsData(SondeData sondeData){
        Platform.runLater(() -> {
            this.humidityField.setText(String.valueOf(sondeData.humidity));
            this.pressureField.setText(String.valueOf(sondeData.pressure));
            this.temperatureField.setText(String.valueOf(sondeData.temperature));
        });
    }

    private void appendToCharts(SondeData sondeData){
        Platform.runLater(() -> {
            humidityVsPressureSeries.getData().add(new XYChart.Data(sondeData.pressure , sondeData.humidity));
            temperatureVsPressureSeries.getData().add(new XYChart.Data(sondeData.pressure , sondeData.temperature));
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
        temperatureVsPressure.setDisable(state);
        humidityVsPressure.setDisable(state);
        temperatureField.setDisable(state);
        humidityField.setDisable(state);
        pressureField.setDisable(state);
        finishButton.setDisable(state);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setDisabled(true);
        temperatureVsPressureSeries = new XYChart.Series();
        temperatureVsPressureSeries.setName("Session Actual");
        temperatureVsPressureSeries.getData().add(new XYChart.Data(200 , 0));
        temperatureVsPressure.getData().addAll(temperatureVsPressureSeries);

        humidityVsPressureSeries = new XYChart.Series();
        humidityVsPressureSeries.setName("Session Actual");
        humidityVsPressureSeries.getData().add(new XYChart.Data(200 , 50));
        humidityVsPressure.getData().addAll(humidityVsPressureSeries);
    }


    @FXML
    void onFinishSession(ActionEvent event) {
        //todo Detener captura
        //todo Guardar archivo
        //todo deshabilitar controles
    }


}
