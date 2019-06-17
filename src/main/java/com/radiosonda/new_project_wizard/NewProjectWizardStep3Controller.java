package com.radiosonda.new_project_wizard;

import com.radiosonda.SondeGeneratorContainer;
import com.radiosonda.serialrx.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.flowable.FlowableInterval;
import io.reactivex.internal.operators.observable.ObservableInterval;
import io.reactivex.schedulers.Schedulers;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author manuel
 */
public class NewProjectWizardStep3Controller implements Initializable {
    public Disposable subscription;

    @FXML
    private DialogPane pane;

    @FXML
    private TextField pressureField;

    @FXML
    private TextField humidityField;

    @FXML
    private TextField temperatureField;

    public void setSelectedPort(String selectedPort) {
        subscription = SondeGeneratorContainer.createSondeFlowable().subscribe(this::updateSondeData);
    }


    private void updateSondeData(SondeData sondeData){
        System.out.println(sondeData);
        Platform.runLater(()->{
            pressureField.setText(String.valueOf(sondeData.pressure));
            humidityField.setText(String.valueOf(sondeData.humidity));
            temperatureField.setText(String.valueOf(sondeData.temperature));
        });
    }


    /**
     * Initializes the controller class.
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.getButtonTypes()
                .add(new ButtonType("Finalizar", ButtonBar.ButtonData.OK_DONE));
        pane.getButtonTypes()
                .add(new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE));
        System.out.println("stuff initialized");
    }
}
