package com.radiosonda;

import com.radiosonda.services.SumService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SampleController {

    @Autowired
    SumService sumService;

    @FXML
    private Button actionButton;

    @FXML
    private TextField input1;

    @FXML
    private TextField input2;

    @FXML
    private Label resultLabel;

    @FXML
    void doSomthing(ActionEvent event) {
        String text1 = input1.getText();
        String text2 = input2.getText();

        String result =  sumService.calcSum(text1, text2);

        resultLabel.setText(result);
    }

    @FXML
    private void initialize() {
    }
}
