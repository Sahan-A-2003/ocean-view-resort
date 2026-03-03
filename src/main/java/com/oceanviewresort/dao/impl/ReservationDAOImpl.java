package com.oceanviewresort.dao.impl;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.dao.ReservationDAO;
import com.oceanviewresort.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private final Connection connection;

    public ReservationDAOImpl() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean addReservation(Reservation r) {

        String sql = "INSERT INTO reservations " +
                "(userID, roomID, guestName, address, contact, roomType, " +
                "checkInDate, checkOutDate, numberOfRooms, numberOfGuests, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, r.getUserID());
            stmt.setInt(2, r.getRoomID());
            stmt.setString(3, r.getGuestName());
            stmt.setString(4, r.getAddress());
            stmt.setString(5, r.getContact());
            stmt.setString(6, r.getRoomType());
            stmt.setDate(7, Date.valueOf(r.getCheckInDate()));
            stmt.setDate(8, Date.valueOf(r.getCheckOutDate()));
            stmt.setInt(9, r.getNumberOfRooms());
            stmt.setInt(10, r.getNumberOfGuests());
            stmt.setString(11, r.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reservation getReservationById(int id) {

        String sql = "SELECT * FROM reservations WHERE reservationID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Reservation r = new Reservation();
                r.setReservationID(rs.getInt("reservationID"));
                r.setStatus(rs.getString("status"));
                return r;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reservation> getAllReservations() {

        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Reservation r = new Reservation();
                r.setReservationID(rs.getInt("reservationID"));
                r.setStatus(rs.getString("status"));

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateStatus(int reservationID, String status) {

        String sql = "UPDATE reservations SET status=? WHERE reservationID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, reservationID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteReservation(int id) {

        String sql = "DELETE FROM reservations WHERE reservationID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countBookedRooms(String roomType,
                                java.time.LocalDate checkIn,
                                java.time.LocalDate checkOut) {

        String sql = "SELECT SUM(numberOfRooms) as total " +
                "FROM reservations " +
                "WHERE roomType=? " +
                "AND status='Booked' " +
                "AND checkInDate < ? " +
                "AND checkOutDate > ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, roomType);
            stmt.setDate(2, java.sql.Date.valueOf(checkOut));
            stmt.setDate(3, java.sql.Date.valueOf(checkIn));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
