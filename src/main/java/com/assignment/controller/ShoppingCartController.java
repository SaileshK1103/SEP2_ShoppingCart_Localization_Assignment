package com.assignment.controller;

import com.assignment.logic.CartService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.util.ResourceBundle;

public class ShoppingCartController {
    // These IDs must match the fx:id in your layout.fxml
    @FXML
    private ComboBox<String> languageSelector;
    @FXML private TextField inputPrice, inputQuantity;
    @FXML private Label labelPrice, labelQuantity, labelTotalText, labelTotalAmount;
    @FXML private Button btnCalculate;

    private final CartService cartService = new CartService();
    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        // Setup the dropdown options
        languageSelector.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        languageSelector.getSelectionModel().selectFirst();
        updateLanguage("en", "US"); // Default
    }

    @FXML
    private void onLanguageChange() {
        String selected = languageSelector.getValue();
        switch (selected) {
            case "Finnish" -> updateLanguage("fi", "FI");
            case "Swedish" -> updateLanguage("sv", "SE");
            case "Japanese" -> updateLanguage("ja", "JP");
            case "Arabic" -> updateLanguage("ar", "AR");
            default -> updateLanguage("en", "US");
        }
    }

    private void updateLanguage(String lang, String country) {
        Locale locale = new Locale(lang, country);
        bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        // Requirement 2: Dynamic UI updates
        labelPrice.setText(bundle.getString("msg.price"));
        labelQuantity.setText(bundle.getString("msg.quantity"));
        labelTotalText.setText(bundle.getString("msg.total"));
        btnCalculate.setText(bundle.getString("msg.calculate"));
    }

    @FXML
    private void handleCalculate() {
        try {
            double price = Double.parseDouble(inputPrice.getText());
            int quantity = Integer.parseInt(inputQuantity.getText());

            // Use your Logic Layer
            double total = cartService.calculateItemTotal(price, quantity);

            labelTotalAmount.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            labelTotalAmount.setText("Invalid Input");
        }
    }
}
