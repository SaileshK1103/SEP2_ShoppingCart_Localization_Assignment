package com.assignment.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseService {
    // 1. Fetch labels from DB based on language
    public Map<String, String> getLabels(String langCode) {
        Map<String, String> labels = new HashMap<>();
        String sql = "SELECT msg_key, msg_value FROM ui_messages WHERE language_code = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, langCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                labels.put(rs.getString("msg_key"), rs.getString("msg_value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    // 2. Save the final cart transaction
    public void saveTransaction(int totalItems, double totalCost, String lang) {
        String sql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, totalItems);
            pstmt.setDouble(2, totalCost);
            pstmt.setString(3, lang);
            pstmt.executeUpdate();
            System.out.println("✅ Transaction saved to MariaDB!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
