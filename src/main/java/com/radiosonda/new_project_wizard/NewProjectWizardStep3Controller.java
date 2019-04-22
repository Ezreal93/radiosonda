package com.radiosonda.new_project_wizard;

import com.radiosonda.serialrx.CharStreamToLines;
import com.radiosonda.serialrx.DataExtractor;
import com.radiosonda.serialrx.SimpleSerialPort;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

    public static SimpleSerialPort port;
    public static Disposable subscription;
    private String selectedPort;
    
       
    @FXML
    private DialogPane pane;

    @FXML
    private TextField pressureField;

    @FXML
    private TextField humidityField;

    @FXML
    private TextField temperatureField;
    
    public void setSelectedPort(String selectedPort) {
        this.selectedPort = selectedPort;
        pressureField.setText(selectedPort);
        temperatureField.setText(selectedPort);
        humidityField.setText(selectedPort);
        
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

        Flowable<Character> myObservable = Flowable.create((emitter) -> {
            port = new SimpleSerialPort(selectedPort);            
            port.setOnDataReceivedListener((Character data) -> {
                emitter.onNext(data);
            });
        }, BackpressureStrategy.BUFFER);
        
//        ArrayList<Character> arrayList = new ArrayList<>(1024);
    
        System.out.println("init");
    }
}
