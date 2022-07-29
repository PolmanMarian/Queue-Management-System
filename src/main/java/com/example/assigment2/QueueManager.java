package com.example.assigment2;

import com.example.assigment2.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class QueueManager extends Application{

    private static Controller controller;

    private static FileWriter file;

    public static FileWriter getFile() {
        return file;
    }

    @Override
    public void start(Stage stage) throws IOException {

        try {
            //file = new FileWriter("C:\\Users\\Marian\\Desktop\\SimulationTxt.txt");
            //file = new FileWriter("C:\\Users\\Marian\\Desktop\\Assigment2\\src\\main\\java\\com\\example\\assigment2\\SimulationFiles\\SimulationTest1.txt");
            //file = new FileWriter("C:\\Users\\Marian\\Desktop\\Assigment2\\src\\main\\java\\com\\example\\assigment2\\SimulationFiles\\SimulationTest2.txt");
            file = new FileWriter("src/main/java/com/example/assigment2/SimulationFiles/Simulation.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(QueueManager.class.getResource("mainView.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        this.controller = fxmlLoader.<Controller>getController();
        Scene scene = new Scene(parent, 600, 400);
        stage.setTitle("UTCN Queue Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static Controller getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch();
    }


}