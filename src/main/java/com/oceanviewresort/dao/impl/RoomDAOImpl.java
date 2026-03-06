package com.oceanviewresort.dao.impl;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.dao.RoomDAO;
import com.oceanviewresort.factory.RoomFactory;
import com.oceanviewresort.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private final Connection connection;

    public RoomDAOImpl() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public Room getRoomById(int id) {

        String sql = "SELECT * FROM room WHERE roomID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return RoomFactory.createRoom(
                        rs.getInt("roomID"),
                        rs.getString("roomType"),
                        rs.getInt("roomNumber"),
                        rs.getInt("bedCount"),
                        rs.getDouble("pricePerNight"),
                        rs.getString("paymentStatus"),
                        rs.getString("description")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Room getRoomByNumber(int roomNumber) {
        return null;
    }

    @Override
    public List<Room> getAllRooms() {

        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT * FROM room";

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                rooms.add(RoomFactory.createRoom(
                        rs.getInt("roomID"),
                        rs.getString("roomType"),
                        rs.getInt("roomNumber"),
                        rs.getInt("bedCount"),
                        rs.getDouble("pricePerNight"),
                        rs.getString("paymentStatus"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public boolean addRoom(Room room) {

        String sql = "INSERT INTO room (roomType, roomNumber, bedCount, pricePerNight, paymentStatus, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, room.getRoomType());
            stmt.setInt(2, room.getRoomNumber());
            stmt.setInt(3, room.getBedCount());
            stmt.setDouble(4, room.getPricePerNight());
            stmt.setString(5, room.getPaymentStatus());
            stmt.setString(6, room.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRoom(Room room) {

        String sql = "UPDATE room SET roomType=?, roomNumber=?, bedCount=?, pricePerNight=?, paymentStatus=?, description=? WHERE roomID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, room.getRoomType());
            stmt.setInt(2, room.getRoomNumber());
            stmt.setInt(3, room.getBedCount());
            stmt.setDouble(4, room.getPricePerNight());
            stmt.setString(5, room.getPaymentStatus());
            stmt.setString(6, room.getDescription());
            stmt.setInt(7, room.getRoomID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRoom(int id) {

        String sql = "DELETE FROM room WHERE roomID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}