package com.example.assigment2.Controller;
import com.example.assigment2.BusinessLogic.SelectionPolicy;
import com.example.assigment2.BusinessLogic.SimulationManager;
import com.example.assigment2.Model.Statistics;
import com.example.assigment2.QueueManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private ComboBox<SelectionPolicy> strategyDropBox;
    @FXML
    private TextField clients;
    @FXML
    private TextField queues;
    @FXML
    private TextField simulationTime;
    @FXML
    private TextField minArrivalTime;
    @FXML
    private TextField maxArrivalTime;
    @FXML
    private TextField minServiceTime;
    @FXML
    private TextField maxServiceTime;
    @FXML
    private TextArea console;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        strategyDropBox.setItems(FXCollections.observableArrayList(SelectionPolicy.SHORTEST_TIME,SelectionPolicy.SHORTEST_QUEUE));
    }

    @FXML
    public void onStartSimulationClick(ActionEvent actionEvent) {

        Integer nrClients;
        Integer nrQueues;
        Integer minArrival;
        Integer maxArrival;
        Integer minService;
        Integer maxService;
        Integer timeLimit;


        try {

            nrClients = Integer.valueOf(clients.getText());
            nrQueues = Integer.valueOf(queues.getText());

            minArrival = Integer.valueOf(minArrivalTime.getText());
            maxArrival = Integer.valueOf(maxArrivalTime.getText());

            minService = Integer.valueOf(minServiceTime.getText());
            maxService = Integer.valueOf(maxServiceTime.getText());

            timeLimit = Integer.valueOf(simulationTime.getText());

        } catch (IllegalArgumentException e) {
            console.setText("Invalid input, Try again -_-");
            return;
        }

        Statistics.reset();


        SimulationManager simulationManager = new SimulationManager(strategyDropBox.getValue(), timeLimit, maxArrival, minArrival, minService, maxService, nrQueues, nrClients);
        console.clear();
        Thread thread= new Thread(simulationManager);
        thread.start();

    }

    ///Printing log
    public void print(String message){

        FileWriter file = QueueManager.getFile();

        try {
            file.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            //console.clear();
            console.appendText(message);
            }
        });
    }


}