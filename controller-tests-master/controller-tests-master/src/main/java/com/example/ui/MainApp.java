package com.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("TripPlanner");
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(700);
        navigateTo("Login");
        primaryStage.show();
    }

    public static void navigateTo(String screen) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            MainApp.class.getResource("/fxml/" + screen + ".fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            MainApp.class.getResource("/css/style.css").toExternalForm()
        );
        primaryStage.setScene(scene);
    }

    public static Stage getPrimaryStage() { return primaryStage; }

    public static void main(String[] args) { launch(args); }
}
