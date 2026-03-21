package com.assignment.model;

/**
 * Model representing a single item in the shopping cart.
 * Using a Record (Java 14+) for clean, immutable data modeling.
 */
public record Item (String name, double price, int quantity) {
    /**
     * Business logic stays inside the model or service.
     * Calculating the total for this specific item.
     */
    public double getTotalPrice() {
        return price * quantity;
    }
}
