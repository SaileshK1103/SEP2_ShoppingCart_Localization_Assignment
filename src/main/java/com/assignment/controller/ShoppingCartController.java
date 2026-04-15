package com.assignment.controller;

import com.assignment.data.DatabaseService;
import com.assignment.logic.CartService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.Map;

public class ShoppingCartController {
    @FXML private VBox rootContainer;
    @FXML private ComboBox<String> languageSelector;
    @FXML private TextField inputPrice;
    @FXML private TextField inputQuantity;
    @FXML private Label labelPrice;
    @FXML private Label labelQuantity;
    @FXML private Label labelTotalText;
    @FXML private Label labelTotalAmount;
    @FXML private Button btnCalculate;

    private final CartService cartService = new CartService();
    private final DatabaseService dbService = new DatabaseService();

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        languageSelector.getSelectionModel().selectFirst();
        updateLanguage("en");
    }

    @FXML
    private void onLanguageChange() {
        String selected = languageSelector.getValue();
        // Use the service to map the language (Better for testing!)
        String langCode = cartService.mapLanguageToCode(selected);
        updateLanguage(langCode);
    }

    private void updateLanguage(String lang) {
        Map<String, String> labels = dbService.getLabels(lang);

        rootContainer.setNodeOrientation(lang.equalsIgnoreCase("ar")
                ? javafx.geometry.NodeOrientation.RIGHT_TO_LEFT
                : javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);

        labelPrice.setText(labels.getOrDefault("msg.price", "Price"));
        labelQuantity.setText(labels.getOrDefault("msg.quantity", "Quantity"));
        labelTotalText.setText(labels.getOrDefault("msg.total", "Total"));
        btnCalculate.setText(labels.getOrDefault("msg.calculate", "Calculate"));
    }

    @FXML
    private void handleCalculate() {
        // We call the service to handle the logic and error checking
        String result = cartService.formatTotal(inputPrice.getText(), inputQuantity.getText());
        labelTotalAmount.setText(result);

        // Only save to database if the result is a valid number
        if (!result.equals("Invalid Input")) {
            try {
                double total = Double.parseDouble(result.replace(",", "."));
                int quantity = Integer.parseInt(inputQuantity.getText());
                String currentLang = languageSelector.getValue();
                dbService.saveTransaction(quantity, total, currentLang);
            } catch (Exception e) {
                java.util.logging.Logger.getLogger("Controller").log(java.util.logging.Level.WARNING, "Calculation failed", e);
            }
        }
    }
}