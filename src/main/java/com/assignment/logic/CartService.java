package com.assignment.logic;

import com.assignment.model.CartItem;
import java.util.List;
import java.util.Locale;

public class CartService {

    public double calculateItemTotal(double price, int quantity) {
        if (price < 0 || quantity < 0) return 0.0;
        return price * quantity;
    }

    public double calculateGrandTotal(List<CartItem> items) {
        if (items == null) return 0.0;
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public String mapLanguageToCode(String selected) {
        if (selected == null) return "en";
        return switch (selected) {
            case "Finnish" -> "fi";
            case "Swedish" -> "sv";
            case "Japanese" -> "ja";
            case "Arabic" -> "ar";
            default -> "en";
        };
    }

    /**
     * Handles string parsing and formatting.
     * Moving this here allows 100% coverage on calculation logic.
     */
    public String formatTotal(String priceStr, String qtyStr) {
        try {
            double p = Double.parseDouble(priceStr);
            int q = Integer.parseInt(qtyStr);
            if (p < 0 || q < 0) return "0.00";
            // Use Locale.US to force the dot separator
            return String.format(Locale.US, "%.2f", calculateItemTotal(p, q));
        } catch (NumberFormatException | NullPointerException e) {
            return "Invalid Input";
        }
    }
}