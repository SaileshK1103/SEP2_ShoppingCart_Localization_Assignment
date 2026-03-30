package com.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void testRecordData() {
        // This one test covers the 3 automatic methods (name, price, quantity)
        CartItem item = new CartItem("Apple", 1.5, 5);
        assertEquals("Apple", item.name());
        assertEquals(1.5, item.price());
        assertEquals(5, item.quantity());
    }

    @Test
    void testGetTotalPrice() {
        // This covers the 1 method you actually wrote
        CartItem item = new CartItem("Apple", 1.5, 5);
        assertEquals(7.5, item.getTotalPrice(), 0.001);
    }
}