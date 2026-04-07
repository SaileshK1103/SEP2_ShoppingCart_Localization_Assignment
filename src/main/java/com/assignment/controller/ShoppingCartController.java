package com.assignment.controller;

import com.assignment.data.DatabaseService;
import com.assignment.logic.CartService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ShoppingCartController {
    @FXML private VBox rootContainer;
    @FXML private ComboBox<String> languageSelector;
    @FXML private TextField inputPrice, inputQuantity;
    @FXML private Label labelPrice, labelQuantity, labelTotalText, labelTotalAmount;

    @FXML private Button btnCalculate;

    private final CartService cartService = new CartService();

    private final DatabaseService dbService = new DatabaseService();
    private Map<String, String> dbLabels;
    private ResourceBundle bundle;

    @FXML
    public void initialize() {
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
       // update the ui labels using resource bundle properties
        /*Locale locale = new Locale(lang, country);
        bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        // Requirement 2: Dynamic UI updates
        labelPrice.setText(bundle.getString("msg.price"));
        labelQuantity.setText(bundle.getString("msg.quantity"));
        labelTotalText.setText(bundle.getString("msg.total"));
        btnCalculate.setText(bundle.getString("msg.calculate"));*/

        // update ui labels using database localization
        // Fetch labels from the Database instead of ResourceBundle
        Map<String, String> dbLabels = dbService.getLabels(lang);

        // --- RTL LOGIC ---
        // If language is Arabic, flip the screen. Otherwise, keep it standard.
        if (lang.equalsIgnoreCase("ar")) {
            rootContainer.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
        } else {
            rootContainer.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        }

        labelPrice.setText(dbLabels.getOrDefault("msg.price", "Price"));
        labelQuantity.setText(dbLabels.getOrDefault("msg.quantity", "Quantity"));
        labelTotalText.setText(dbLabels.getOrDefault("msg.total", "Total"));
        btnCalculate.setText(dbLabels.getOrDefault("msg.calculate", "Calculate"));
    }

    @FXML
    private void handleCalculate() {
        // Resource bundle method: week2
        /*try {
            double price = Double.parseDouble(inputPrice.getText());
            int quantity = Integer.parseInt(inputQuantity.getText());

            // Use your Logic Layer
            double total = cartService.calculateItemTotal(price, quantity);

            labelTotalAmount.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            labelTotalAmount.setText("Invalid Input");
        }*/
        // Save to Database: Week3
        try {
            double price = Double.parseDouble(inputPrice.getText());
            int quantity = Integer.parseInt(inputQuantity.getText());

            double total = cartService.calculateItemTotal(price, quantity);
            labelTotalAmount.setText(String.format("%.2f", total));

            // 3. SAVE TO DB: This satisfies the persistence requirement
            String currentLang = languageSelector.getValue();
            dbService.saveTransaction(quantity, total, currentLang);

        } catch (NumberFormatException e) {
            labelTotalAmount.setText("Invalid Input");
        }


    }
}
