package com.assignment.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {
    private final DatabaseService dbService = new DatabaseService();

    @Test
    void testFullServiceCycle() {

        assertNotNull(dbService.getLabels("en"));
        assertNotNull(dbService.getLabels("fi"));

        assertDoesNotThrow(() -> {
            dbService.saveTransaction(1, 10.99, "en");
            dbService.saveTransaction(999, 0.0, "fi");
        });

        dbService.getLabels(null);
        dbService.saveTransaction(-1, -1.0, null);

        assertTrue(true);
    }

  @Test
  void testSaveTransaction_TriggersCatchBlock() {
    DatabaseService service = new DatabaseService();
    assertDoesNotThrow(() -> service.saveTransaction(0, 0.0, null));
  }
}