package com.oceanviewresort.observer;

public interface ReservationSubject {
    void registerObserver(ReservationObserver observer);
    void removeObserver(ReservationObserver observer);
    void notifyObservers(String guestEmail, String subject, String message);
}