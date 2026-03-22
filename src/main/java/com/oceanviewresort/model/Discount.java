package com.oceanviewresort.model;

import java.time.LocalDate;

public class Discount {
    private int discountID;
    private String code;
    private String description;
    private double percentage;
    private LocalDate validFrom;
    private LocalDate validTo;

    public Discount(int discountID, String code, String description, double percentage, LocalDate validFrom, LocalDate validTo) {
        this.discountID = discountID;
        this.code = code;
        this.description = description;
        this.percentage = percentage;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public int getDiscountID() { return discountID; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public double getPercentage() { return percentage; }
    public LocalDate getValidFrom() { return validFrom; }
    public LocalDate getValidTo() { return validTo; }
}