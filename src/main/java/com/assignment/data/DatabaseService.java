package com.assignment.data;

import com.assignment.data.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseService {
    private static final Logger LOGGER = Logger.getLogger(DatabaseService.class.getName());

    public Map<String, String> getLabels(String langCode) {
        Map<String, String> labels = new HashMap<>();
        String sql = "SELECT msg_key, msg_value FROM ui_messages WHERE language_code = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, langCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    labels.put(rs.getString("msg_key"), rs.getString("msg_value"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching labels for: " + langCode, e);
        }
        return labels;
    }

    public void saveTransaction(int totalItems, double totalCost, String lang) {
        String sql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, totalItems);
            pstmt.setDouble(2, totalCost);
            pstmt.setString(3, lang);
            pstmt.executeUpdate();
            LOGGER.info("✅ Transaction saved to MariaDB!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving transaction", e);
        }
    }
}