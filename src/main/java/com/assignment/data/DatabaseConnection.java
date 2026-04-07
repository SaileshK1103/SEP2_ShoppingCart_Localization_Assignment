package com.assignment.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USERNAME");
    private static final String DB_PASS = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        // Build the URL dynamically from environment variables
        String url = "jdbc:mysql://" +
                (DB_HOST != null ? DB_HOST : "localhost") + ":3306/" +
                (DB_NAME != null ? DB_NAME : "shopping_cart_localization") +
                "?useUnicode=true&characterEncoding=UTF-8";
        try {
            System.out.println("🔍 Connecting to DB as user: " + DB_USER);
            // If DB_USER or DB_PASS are null, this will throw an exception (secure)
            Connection conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
            System.out.println("✅ Connection Successful!");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Connection Failed! Environment variables missing or incorrect.");
            throw e;
        }
    }

    /*public static void main(String[] args) {
        System.out.println("🧪 Testing Connection...");
        System.out.println("👤 Detected User: " + DB_USER);

        if (DB_PASS == null || DB_PASS.isEmpty()) {
            System.err.println("❌ ERROR: Password not found in Environment Variables!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
            if (conn != null) {
                System.out.println("✅ SUCCESS: Java successfully connected to MariaDB using Environment Variables!");
            }
        } catch (SQLException e) {
            System.err.println("❌ CONNECTION FAILED: " + e.getMessage());
        }
    }*/
}
