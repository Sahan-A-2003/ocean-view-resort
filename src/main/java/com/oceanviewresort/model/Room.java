package com.oceanviewresort.model;

import java.time.LocalDateTime;

public class Room {

    private int roomID;
    private String roomType;
    private int roomNumber;
    private int bedCount;
    private double pricePerNight;
    private String paymentStatus;
    private String description;
    private LocalDateTime createdAt;

    public Room() {}

    public Room(int roomID, String roomType, int roomNumber,
                int bedCount, double pricePerNight,
                String paymentStatus, String description) {

        this.roomID = roomID;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.bedCount = bedCount;
        this.pricePerNight = pricePerNight;
        this.paymentStatus = paymentStatus;
        this.description = description;
    }

    public double calculateCost(int nights) {
        return nights * pricePerNight;
    }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public int getBedCount() { return bedCount; }
    public void setBedCount(int bedCount) { this.bedCount = bedCount; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
