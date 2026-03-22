package com.oceanviewresort.observer;

public interface ReservationObserver {
    void update(String guestEmail, String subject, String message);
}