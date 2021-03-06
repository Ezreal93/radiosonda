package com.radiosonda;

import com.radiosonda.serialrx.SondeData;
import com.radiosonda.storage.SondeDataStorage;
import com.radiosonda.wizard.Wizard;
import com.radiosonda.new_project_wizard.NewProjectWizardFactory;
import com.radiosonda.wizard.ContextDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

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
import javafx.scene.control.*;
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
            this.initializeGraphs(sessionContext.get("probe"));
            setDisabled(false);
            subscription = SondeGeneratorContainer.createSondeFlowable().subscribe(this::onSondeDataReceived);
        });
    }

    private void onSondeDataReceived(SondeData sondeData){
        System.out.println(sondeData);
        setControlsData(sondeData);
        appendToCharts(sondeData);
        appendToSessionRecord(sondeData);
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

    private List<SondeData> sessionRecords = new ArrayList<>();

    private void appendToSessionRecord(SondeData sondeData){
        sessionRecords.add(sondeData);
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

    private void initializeGraphs(String sessionName){
        temperatureVsPressureSeries = new XYChart.Series();
        temperatureVsPressureSeries.setName(sessionName);
        temperatureVsPressure.getData().addAll(temperatureVsPressureSeries);

        humidityVsPressureSeries = new XYChart.Series();
        humidityVsPressureSeries.setName(sessionName);
        humidityVsPressure.getData().addAll(humidityVsPressureSeries);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setDisabled(true);
    }


    @FXML
    void onFinishSession(ActionEvent event) {
        subscription.dispose();
        saveFile();
    }


    private void saveFile(){
        System.out.println(sessionRecords);
        try {
            showFileDialog(true).ifPresent(file -> new SondeDataStorage().setFile(file).setSessionData(sessionRecords).save());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save status");
            alert.setHeaderText(null);
            alert.setContentText("The file was saved successfully");
            alert.showAndWait();

            setDisabled(true);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save status");
            alert.setHeaderText(null);
            alert.setContentText("The file could not be saved");
            alert.showAndWait();
        }
    }


    private Optional<File> showFileDialog(boolean isSave) {
        FileChooser fileChooser = new FileChooser();
        File file;
        if (isSave) {
            file = fileChooser.showSaveDialog(null);
        } else {
            file = fileChooser.showOpenDialog(null);
        }
        return Optional.ofNullable(file);
    }

}
