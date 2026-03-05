package com.oceanviewresort.dao.impl;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.dao.DiscountDAO;
import com.oceanviewresort.model.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAOImpl implements DiscountDAO {

    @Override
    public List<Discount> getActiveDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM Discount WHERE validTo >= CURRENT_DATE";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                discounts.add(new Discount(
                        rs.getInt("discountID"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getDouble("percentage"),
                        rs.getDate("validFrom").toLocalDate(),
                        rs.getDate("validTo").toLocalDate()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return discounts;
    }
}
