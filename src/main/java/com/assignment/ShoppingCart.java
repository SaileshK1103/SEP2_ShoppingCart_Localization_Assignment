package com.assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShoppingCart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML from the resources folder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/assignment/view/layout.fxml"));
        Parent root = loader.load();

        // Requirement: Your name in the title
        primaryStage.setTitle("SAILESH KARKI / Shopping Cart App");

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
