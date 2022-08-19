package com.workshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            ScrollPane scrollPane = loadFXML("main");

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Sample JavaFX application");
            primaryStage.setMaximized(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    private static ScrollPane loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}