package com.assignment.logic;

import com.assignment.model.CartItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    @Test
    void calculateItemTotal() {
        CartService service = new CartService();
        // $12.50 * 4 = $50.00
        assertEquals(50.00, service.calculateItemTotal(12.50, 4), 0.001);
    }

    @Test
    void calculateGrandTotal() {
        CartService service = new CartService();

        // List with two items: ($2 * 5) + ($3 * 2) = $16.00
        List<CartItem> items = List.of(
                new CartItem("Apple", 2.0, 5),
                new CartItem("Banana", 3.0, 2)
        );

        assertEquals(16.00, service.calculateGrandTotal(items), 0.001);
    }
}