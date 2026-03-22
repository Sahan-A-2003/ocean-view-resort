package com.oceanviewresort.model;

import java.time.LocalDate;

public class Reservation {

    private int reservationID;
    private int userID;
    private int roomID;
    private String guestEmail;

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

    public Reservation(int userID, int roomID, String guestName,String guestEmail,
                       String address, String contact, String roomType,
                       LocalDate checkInDate, LocalDate checkOutDate,
                       int numberOfRooms, int numberOfGuests, String status) {

        this.userID = userID;
        this.roomID = roomID;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.address = address;
        this.contact = contact;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfRooms = numberOfRooms;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
    }

    public int calculateNights() {
        return (int)(checkOutDate.toEpochDay() - checkInDate.toEpochDay());
    }

    public int getReservationID() { return reservationID; }
    public void setReservationID(int reservationID) { this.reservationID = reservationID; }

    public int getUserID() { return userID; }
    public int getRoomID() { return roomID; }
    public String getGuestEmail() { return guestEmail; }

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
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestEmail(String guestEmail) { this.guestEmail = guestEmail; }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }


    public void setNumberOfGuests(int numberOfGuests) {this.numberOfGuests = numberOfGuests;}

    public void setAddress(String address) {this.address = address;}

    public void setContact(String contact) {this.contact = contact; }
}