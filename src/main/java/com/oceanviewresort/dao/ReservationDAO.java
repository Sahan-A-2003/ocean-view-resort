package com.oceanviewresort.dao;

import com.oceanviewresort.model.Reservation;
import java.util.List;

public interface ReservationDAO {

    boolean addReservation(Reservation reservation);

    Reservation getReservationById(int id);

    List<Reservation> getAllReservations();

    boolean updateStatus(int reservationID, String status);

    boolean deleteReservation(int id);
}
