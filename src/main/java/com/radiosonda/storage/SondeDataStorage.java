package com.radiosonda.storage;

import com.radiosonda.FXMLController;
import com.radiosonda.serialrx.SondeData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SondeDataStorage {
    private File file;
    private List<SondeData> sessionData;

    public SondeDataStorage setFile(File file) {
        this.file = file;
        return this;
    }

    public SondeDataStorage setSessionData(List<SondeData> sessionData) {
        this.sessionData = sessionData;
        return this;
    }

    private Optional<BufferedWriter>createWritterFromFile() {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.ofNullable(writer);
    }

    public void save(){
        BufferedWriter writer = createWritterFromFile().orElseThrow(() -> new RuntimeException("could not create writter"));
        saveAsCSV(writer);
    }

    private void saveAsCSV(BufferedWriter writer){
        try {
            TableModel model = convertToModel();
            CSVTranslator csvTranslator = new CSVTranslator(writer);
            csvTranslator.print(model);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("could not save");
        }
    }

    private TableModel convertToModel(){
        List<String> headers = Arrays.asList("timestamp", "pressure", "temperature", "humidity");
        TableModel tableModel = new TableModel();
        tableModel.data = sessionDataToModelData();
        tableModel.headers = headers;
        return tableModel;
    }

    List<List<String>> sessionDataToModelData(){
        return sessionData.stream().map(sondeData -> {
            List<String> data = new ArrayList<>();
            data.add(String.valueOf(sondeData.timestamp));
            data.add(String.valueOf(sondeData.pressure));
            data.add(String.valueOf(sondeData.temperature));
            data.add(String.valueOf(sondeData.humidity));
            return data;
        }).collect(Collectors.toList());
    }
}
