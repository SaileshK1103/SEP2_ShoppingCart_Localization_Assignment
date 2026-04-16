package com.assignment.data;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseServiceTest {

  private final DatabaseService dbService = new DatabaseService();

  @Test
  void testGetLabels_Success() throws SQLException {
    // Mock the JDBC objects
    Connection mockConn = mock(Connection.class);
    PreparedStatement mockPstmt = mock(PreparedStatement.class);
    ResultSet mockRs = mock(ResultSet.class);

    // Define what happens when JDBC methods are called
    when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
    when(mockPstmt.executeQuery()).thenReturn(mockRs);
    when(mockRs.next()).thenReturn(true, false); // Return one row, then end
    when(mockRs.getString("msg_key")).thenReturn("msg.price");
    when(mockRs.getString("msg_value")).thenReturn("Price:");

    // Mock the static call to get the connection
    try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
      mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConn);

      Map<String, String> labels = dbService.getLabels("en");

      assertEquals("Price:", labels.get("msg.price"));
      verify(mockPstmt).setString(1, "en");
    }
  }

  @Test
  void testCatchBlocks_FullCoverage() {
    // Mock the static call to THROW an exception
    try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
      mockedStatic.when(DatabaseConnection::getConnection).thenThrow(new SQLException("Database Down"));

      // These calls will now hit the CATCH blocks in DatabaseService
      // and we use assertDoesNotThrow to ensure the app doesn't crash.
      assertDoesNotThrow(() -> dbService.getLabels("fi"));
      assertDoesNotThrow(() -> dbService.saveTransaction(5, 50.0, "ar"));
    }
  }
}