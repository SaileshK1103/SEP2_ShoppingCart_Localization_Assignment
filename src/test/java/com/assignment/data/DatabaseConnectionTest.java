package com.assignment.data;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

  @Test
  void testGetConnection_Success() throws SQLException {
    Connection mockConn = mock(Connection.class);

    try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), any(), any()))
          .thenReturn(mockConn);

      Connection conn = DatabaseConnection.getConnection();
      assertNotNull(conn);
      assertEquals(mockConn, conn);
    }
  }

  @Test
  void testGetConnection_Failure_ThrowsIllegalStateException() {
    // Create the exception OUTSIDE the mockStatic block to avoid UnfinishedStubbingException
    SQLException simulatedError = new SQLException("Network error");

    try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), any(), any()))
          .thenThrow(simulatedError);

      // Per your source code, this should wrap the SQLException in an IllegalStateException
      assertThrows(IllegalStateException.class, DatabaseConnection::getConnection);
    }
  }

  @Test
  void testConstructorIsPrivate() throws Exception {
    java.lang.reflect.Constructor<DatabaseConnection> constructor = DatabaseConnection.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    // This triggers the constructor and the "Utility class" exception inside it
    assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
  }

  @Test
  void testUrlGenerationBranches() throws SQLException {
    // This forces the code to exercise the ternary operators (null checks) for DB_HOST and DB_NAME
    Connection mockConn = mock(Connection.class);
    try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), any(), any()))
          .thenReturn(mockConn);

      // Even if env variables are null, this exercises the default "localhost" branch
      assertNotNull(DatabaseConnection.getConnection());
    }
  }
}