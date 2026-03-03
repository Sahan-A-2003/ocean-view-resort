package com.oceanviewresort.service;

import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.factory.RoomFactory;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.model.Room;

import java.sql.SQLException;

public class ReservationService {

    private final ReservationDAOImpl reservationDAO;

    public ReservationService() throws SQLException {
        this.reservationDAO = new ReservationDAOImpl();
    }

    public boolean createReservation(Reservation reservation) {

        int totalRooms = getTotalRoomsByType(reservation.getRoomType());

        int alreadyBooked = reservationDAO.countBookedRooms(
                reservation.getRoomType(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );

        int availableRooms = totalRooms - alreadyBooked;

        if (reservation.getNumberOfRooms() > availableRooms) {

            System.out.println("Not enough rooms available!");
            System.out.println("Available: " + availableRooms);
            return false;
        }

        // Calculate cost
        int nights = reservation.calculateNights();

        Room room = RoomFactory.createRoom(reservation.getRoomType());

        double total = room.calculateCost(nights) *
                reservation.getNumberOfRooms();

        System.out.println("Total Cost: " + total);

        reservation.setStatus("Booked");

        return reservationDAO.addReservation(reservation);
    }

    private int getTotalRoomsByType(String roomType) {

        switch (roomType) {
            case "Standard": return 10;
            case "Deluxe": return 5;
            case "Suite": return 3;
            default: return 0;
        }
    }


}