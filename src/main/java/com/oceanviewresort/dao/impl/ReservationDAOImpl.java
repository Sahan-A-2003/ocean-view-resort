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

        String sql = "INSERT INTO reservation\n" +
                "(userID, roomID, guestName, guestEmail, address, contact, roomType,\n" +
                " checkInDate, checkOutDate, numberOfRooms, numberOfGuests, status)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, r.getUserID());
            stmt.setInt(2, r.getRoomID());
            stmt.setString(3, r.getGuestName());
            stmt.setString(4, r.getGuestEmail());
            stmt.setString(5, r.getAddress());
            stmt.setString(6, r.getContact());
            stmt.setString(7, r.getRoomType());
            stmt.setDate(8, Date.valueOf(r.getCheckInDate()));
            stmt.setDate(9, Date.valueOf(r.getCheckOutDate()));
            stmt.setInt(10, r.getNumberOfRooms());
            stmt.setInt(11, r.getNumberOfGuests());
            stmt.setString(12, r.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reservation getReservationById(int id) {

        String sql = "SELECT * FROM reservation WHERE reservationID=?";

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
        String sql = "SELECT * FROM reservation";

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Reservation r = new Reservation();

                r.setReservationID(rs.getInt("reservationID"));
                r.setRoomID(rs.getInt("roomID"));
                r.setRoomType(rs.getString("roomType"));
                r.setGuestName(rs.getString("guestName"));
                r.setGuestEmail(rs.getString("guestEmail"));

                r.setCheckInDate(rs.getDate("checkInDate").toLocalDate());
                r.setCheckOutDate(rs.getDate("checkOutDate").toLocalDate());

                r.setStatus(rs.getString("status"));

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Reservation> searchReservations(String keyword) {

        List<Reservation> list = new ArrayList<>();

        String sql = "SELECT * FROM reservation " +
                "WHERE reservationID LIKE ? OR roomID LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

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

        String sql = "UPDATE reservation SET status=? WHERE reservationID=?";

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

        String sql = "DELETE FROM reservation WHERE reservationID=?";

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
                "FROM reservation " +
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

    public boolean updateReservation(Reservation r) {
        String sql = "UPDATE reservation SET guestName=?,guestEmail=?, roomID=?, roomType=?, checkInDate=?, checkOutDate=?, numberOfRooms=?, numberOfGuests=? WHERE reservationID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getGuestName());
            ps.setString(2, r.getGuestEmail());
            ps.setInt(3, r.getRoomID());
            ps.setString(4, r.getRoomType());
            ps.setDate(5, java.sql.Date.valueOf(r.getCheckInDate()));
            ps.setDate(6, java.sql.Date.valueOf(r.getCheckOutDate()));
            ps.setInt(7, r.getNumberOfRooms());
            ps.setInt(8, r.getNumberOfGuests());
            ps.setInt(9, r.getReservationID());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservationsByRoomNumber(int roomNumber) {

        List<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT * FROM reservation WHERE roomID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, roomNumber);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Reservation reservation = new Reservation();

                reservation.setReservationID(rs.getInt("reservationID"));
                reservation.setRoomID(rs.getInt("roomID"));
                reservation.setGuestName(rs.getString("guestName"));
                reservation.setGuestEmail(rs.getString("guestEmail"));

                reservation.setCheckInDate(
                        rs.getDate("checkInDate").toLocalDate()
                );

                reservation.setCheckOutDate(
                        rs.getDate("checkOutDate").toLocalDate()
                );

                reservation.setStatus(rs.getString("status"));

                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
