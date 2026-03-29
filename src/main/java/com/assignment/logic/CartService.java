package com.assignment.logic;

import com.assignment.model.CartItem;

import java.util.List;

/**
 * Service class for handling shopping cart calculations.
 * Satisfies Requirement #3: Isolated logic for unit testing.
 */
public class CartService {
    /**
     * Calculates the total for a single item (Price * Quantity).
     */
    public double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }

    /**
     * Calculates the grand total for a list of CartItems.
     * This makes the project extendable for future database/multi-item tasks.
     */
    public double calculateGrandTotal(List<CartItem> items) {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}
