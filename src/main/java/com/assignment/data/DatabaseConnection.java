package com.assignment.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "shopping_cart_localization");
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "default_user");
    private static final String DB_PASS = System.getenv().getOrDefault("DB_PASSWORD", "");

    private static final String URL = "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME + "?useUnicode=true&characterEncoding=UTF-8";

    public static Connection getConnection() throws SQLException {
        try {
            // Debug print (Safe: doesn't print the password)
            System.out.println("🔍 Connecting to " + DB_NAME + " on " + DB_HOST + " as user: " + DB_USER);

            Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
            System.out.println("✅ Connection Successful!");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Connection Failed! Verify your Environment Variables.");
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
