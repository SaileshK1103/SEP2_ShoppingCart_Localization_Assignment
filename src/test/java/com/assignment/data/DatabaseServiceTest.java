package com.assignment.data;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {
    private final DatabaseService dbService = new DatabaseService();

  @Test
  void testGetLabels_Success() {
    // Happy path: fetches labels for existing languages
    Map<String, String> enLabels = dbService.getLabels("en");
    Map<String, String> fiLabels = dbService.getLabels("fi");

    assertNotNull(enLabels, "Labels map should not be null");
    assertNotNull(fiLabels, "Labels map should not be null");
  }

  @Test
  void testGetLabels_TriggersCatchBlock() {
    // Logic path: Trigger SQLException/Error by passing invalid parameters
    // This ensures the catch block in getLabels is covered
    assertDoesNotThrow(() -> {
      dbService.getLabels(null);
    }, "Method should handle SQLException internally and not throw it");
  }

  @Test
  void testSaveTransaction_Success() {
    assertDoesNotThrow(() -> {
      dbService.saveTransaction(2, 25.50, "en");
    }, "Standard transaction should save without error");
  }

  @Test
  void testSaveTransaction_TriggersCatchBlock() {
    // Logic path: Trigger SQLException/Error (e.g., passing null to a non-null column)
    // This ensures the catch block in saveTransaction is covered
    assertDoesNotThrow(() -> {
      dbService.saveTransaction(-1, -1.0, null);
    }, "Method should handle SQLException internally and not throw it");
  }
}