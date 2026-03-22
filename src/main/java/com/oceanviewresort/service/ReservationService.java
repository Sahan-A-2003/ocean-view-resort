package com.oceanviewresort.service;

import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.factory.RoomFactory;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.model.Room;
import com.oceanviewresort.observer.ReservationObserver;
import com.oceanviewresort.observer.ReservationSubject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements ReservationSubject {

    private final ReservationDAOImpl reservationDAO;
    private final List<ReservationObserver> observers = new ArrayList<>();

    public ReservationService() throws SQLException {
        this.reservationDAO = new ReservationDAOImpl();
    }

    // Observer pattern methods
    @Override
    public void registerObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ReservationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String guestEmail, String subject, String message) {
        for (ReservationObserver observer : observers) {
            observer.update(guestEmail, subject, message);
        }
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
        boolean added = reservationDAO.addReservation(reservation);

        if (added) {
            // Notify EmailService
            String htmlMessage = """
            <html>
            <body style="font-family: Arial; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:white; border-radius:10px; overflow:hidden; box-shadow:0 0 10px rgba(0,0,0,0.1);">
            
                    <div style="background-color:#28a745; color:white; padding:15px; text-align:center;">
                        <h2>✅ Reservation Confirmed</h2>
                    </div>
            
                    <div style="padding:20px;">
                        <p>Dear <b>%s</b>,</p>
            
                        <p>Your reservation has been successfully confirmed. 🎉</p>
            
                        <table style="width:100%%;">
                            <tr><td><b>Reservation ID:</b></td><td>%d</td></tr>
                            <tr><td><b>Room Type:</b></td><td>%s</td></tr>
                            <tr><td><b>Check-in:</b></td><td>%s</td></tr>
                            <tr><td><b>Check-out:</b></td><td>%s</td></tr>
                            <tr><td><b>Guests:</b></td><td>%d</td></tr>
                            <tr><td><b>Total Cost:</b></td><td>$%.2f</td></tr>
                        </table>
            
                        <p style="margin-top:20px;">We look forward to welcoming you! 🌊</p>
                    </div>
            
                    <div style="background:#eee; padding:10px; text-align:center; font-size:12px;">
                        Ocean View Resort
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                    reservation.getGuestName(),
                    reservation.getReservationID(),
                    reservation.getRoomType(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getNumberOfGuests(),
                    total
            );

            notifyObservers(
                    reservation.getGuestEmail(),
                    "Reservation Confirmed",
                    htmlMessage
            );
        }

        return added;
    }

    private int getTotalRoomsByType(String roomType) {

        switch (roomType) {
            case "Standard": return 10;
            case "Deluxe": return 5;
            case "Suite": return 3;
            default: return 0;
        }
    }

    public boolean cancelReservation(Reservation reservation) {
        boolean cancelled = reservationDAO.updateStatus(reservation.getReservationID(), "Cancelled");

        if (cancelled) {
            String htmlMessage = """
            <html>
            <body style="font-family: Arial; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:white; border-radius:10px;">
                    <div style="background-color:#dc3545; color:white; padding:15px; text-align:center;">
                        <h2>❌ Reservation Cancelled</h2>
                    </div>
            
                    <div style="padding:20px;">
                        <p>Dear %s,</p>
                        <p>Your reservation has been cancelled.</p>
            
                        <p><b>Reservation ID:</b> %d</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                    reservation.getGuestName(),
                    reservation.getReservationID()
            );

            notifyObservers(
                    reservation.getGuestEmail(),
                    "Reservation Cancelled",
                    htmlMessage
            );
        }

        return cancelled;
    }

    public boolean editReservation(Reservation reservation) {
        boolean updated = reservationDAO.updateReservation(reservation);

        if (updated) {
            String htmlMessage = """
            <html>
            <body style="font-family: Arial; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:white; border-radius:10px;">
                    <div style="background-color:#007bff; color:white; padding:15px; text-align:center;">
                        <h2>🔄 Reservation Updated</h2>
                    </div>
            
                    <div style="padding:20px;">
                        <p>Dear %s,</p>
                        <p>Your reservation has been updated successfully.</p>
            
                        <p><b>Reservation ID:</b> %d</p>
                        <p><b>Room Type:</b> %s</p>
                        <p><b>Check-in:</b> %s</p>
                        <p><b>Check-out:</b> %s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                    reservation.getGuestName(),
                    reservation.getReservationID(),
                    reservation.getRoomType(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate()
            );

            notifyObservers(
                    reservation.getGuestEmail(),
                    "Reservation Updated",
                    htmlMessage
            );
        }

        return updated;
    }


}