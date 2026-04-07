package com.assignment.data;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {
    private final DatabaseService dbService = new DatabaseService();

    @Test
    void testGetLabelsFromDatabase() {
        // This tests if your DB connection works and if data exists for English
        Map<String, String> labels = dbService.getLabels("en");

        assertNotNull(labels, "Labels map should not be null");
        // Check if at least one key we know exists is there
        assertTrue(labels.containsKey("msg.price"), "Database should contain 'msg.price' for English");
    }

    @Test
    void testSaveTransaction() {
        // This tests if the INSERT query runs without crashing
        // We use a dummy 99.99 total to check later if needed
        assertDoesNotThrow(() -> {
            dbService.saveTransaction(1, 99.99, "Test");
        }, "Saving a transaction to the database should not throw an exception");
    }
}