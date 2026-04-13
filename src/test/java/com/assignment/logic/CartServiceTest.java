package com.assignment.logic;

import com.assignment.model.CartItem;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
    private final CartService service = new CartService();

    @Test
    void testCalculateItemTotal() {
        // Normal case
        assertEquals(50.00, service.calculateItemTotal(12.50, 4), 0.001);
        // Edge cases for negative price and quantity (hits both parts of the || condition)
        assertEquals(0.0, service.calculateItemTotal(-10.0, 5), 0.001);
        assertEquals(0.0, service.calculateItemTotal(10.0, -5), 0.001);
    }

    @Test
    void testCalculateGrandTotal() {
        List<CartItem> items = List.of(
                new CartItem("Apple", 2.0, 5),
                new CartItem("Banana", 3.0, 2)
        );
        assertEquals(16.00, service.calculateGrandTotal(items), 0.001);
        // Hits the null check
        assertEquals(0.0, service.calculateGrandTotal(null), 0.001);
        // Hits the empty list path
        assertEquals(0.0, service.calculateGrandTotal(Collections.emptyList()), 0.001);
    }

    @Test
    void testLanguageMapping() {
        // Hits every branch of the switch
        assertEquals("fi", service.mapLanguageToCode("Finnish"));
        assertEquals("sv", service.mapLanguageToCode("Swedish"));
        assertEquals("ja", service.mapLanguageToCode("Japanese"));
        assertEquals("ar", service.mapLanguageToCode("Arabic"));
        // Hits default and null check
        assertEquals("en", service.mapLanguageToCode("English"));
        assertEquals("en", service.mapLanguageToCode("Unknown"));
        assertEquals("en", service.mapLanguageToCode(null));
    }

    @Test
    void testFormatTotal() {
        // Success path
        assertEquals("50.00", service.formatTotal("10.0", "5"));

        // Negative input path (hits the if (p < 0 || q < 0) condition)
        assertEquals("0.00", service.formatTotal("-10", "5"));
        assertEquals("0.00", service.formatTotal("10", "-5"));

        // Catch block: NumberFormatException path
        assertEquals("Invalid Input", service.formatTotal("abc", "5"));

        // Catch block: NullPointerException path
        assertEquals("Invalid Input", service.formatTotal(null, "5"));
        assertEquals("Invalid Input", service.formatTotal("10", null));
    }
}