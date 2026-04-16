package com.assignment.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
  private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

  private static final String DB_HOST = System.getenv("DB_HOST");
  private static final String DB_NAME = System.getenv("DB_NAME");
  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

  private DatabaseConnection() {
    throw new IllegalStateException("Utility class");
  }

  public static Connection getConnection() throws SQLException {
    String host = (DB_HOST != null) ? DB_HOST : "localhost";
    String name = (DB_NAME != null) ? DB_NAME : "shopping_cart_localization";
    String url = "jdbc:mysql://" + host + ":3307/" + name + "?useUnicode=true&characterEncoding=UTF-8";

    try {
      LOGGER.info("🔍 Connecting to DB...");
      return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
    } catch (SQLException e) {
      String errorMsg = "Connection Failed to database: " + url;
      LOGGER.log(Level.SEVERE, errorMsg);
      throw new IllegalStateException(errorMsg, e);
    }
  }
}