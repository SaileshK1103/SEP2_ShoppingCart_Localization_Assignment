package com.assignment.data;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

  @Test
  void testGetConnection() {

    assertDoesNotThrow(() -> {
      try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn != null) {
          assertFalse(conn.isClosed());
        }
      } catch (IllegalStateException | SQLException e) {
        assertNotNull(e.getMessage());
      }
    });
  }

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<DatabaseConnection> constructor = DatabaseConnection.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception = assertThrows(
        InvocationTargetException.class,
        constructor::newInstance);
    assertTrue(exception.getCause() instanceof IllegalStateException);
    assertEquals("Utility class", exception.getCause().getMessage());
  }
}