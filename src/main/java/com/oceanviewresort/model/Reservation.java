package com.oceanviewresort.model;

import java.time.LocalDate;

public class Reservation {

    private int reservationID;
    private int userID;
    private int roomID;

    private String guestName;
    private String address;
    private String contact;
    private String roomType;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int numberOfRooms;
    private int numberOfGuests;

    private String status;

    public Reservation() {}

    public Reservation(int userID, int roomID, String guestName,
                       String address, String contact, String roomType,
                       LocalDate checkInDate, LocalDate checkOutDate,
                       int numberOfRooms, int numberOfGuests, String status) {

        this.userID = userID;
        this.roomID = roomID;
        this.guestName = guestName;
        this.address = address;
        this.contact = contact;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfRooms = numberOfRooms;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
    }

    // Business Logic
    public int calculateNights() {
        return (int)(checkOutDate.toEpochDay() - checkInDate.toEpochDay());
    }

    // Getters and Setters

    public int getReservationID() { return reservationID; }
    public void setReservationID(int reservationID) { this.reservationID = reservationID; }

    public int getUserID() { return userID; }
    public int getRoomID() { return roomID; }

    public String getGuestName() { return guestName; }
    public String getAddress() { return address; }
    public String getContact() { return contact; }
    public String getRoomType() { return roomType; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public int getNumberOfGuests() { return numberOfGuests; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}