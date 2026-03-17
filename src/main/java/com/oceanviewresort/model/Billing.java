package com.oceanviewresort.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Billing {

    private int billID;
    private int reservationID;
    private double baseAmount;
    private double discountAmount;
    private double totalAmount;
    private LocalDateTime billingDate;
    private String paymentStatus;
    private String guestName;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double pricePerNight;

    public Billing(){}

    public Billing(int billID,int reservationID,double baseAmount,double discountAmount,double totalAmount,String paymentStatus){
        this.billID=billID;
        this.reservationID=reservationID;
        this.baseAmount=baseAmount;
        this.discountAmount=discountAmount;
        this.totalAmount=totalAmount;
        this.paymentStatus=paymentStatus;
    }

    public int getBillID(){ return billID; }
    public int getReservationID(){ return reservationID; }
    public double getBaseAmount(){ return baseAmount; }
    public double getDiscountAmount(){ return discountAmount; }
    public double getTotalAmount(){ return totalAmount; }
    public String getPaymentStatus(){ return paymentStatus; }

    public void setBillID(int billID){ this.billID=billID; }
    public void setReservationID(int reservationID){ this.reservationID=reservationID; }
    public void setBaseAmount(double baseAmount){ this.baseAmount=baseAmount; }
    public void setDiscountAmount(double discountAmount){ this.discountAmount=discountAmount; }
    public void setTotalAmount(double totalAmount){ this.totalAmount=totalAmount; }
    public void setPaymentStatus(String paymentStatus){ this.paymentStatus=paymentStatus; }

    // Getter and Setter for guestName
    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    // Getter and Setter for roomNumber
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    // Getter and Setter for checkInDate
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    // Getter and Setter for checkOutDate
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    // Getter and Setter for pricePerNight
    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

}