package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    //Constructor
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //Getters
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }


    //Overrides the default toString() format to chosen format
    @Override
    public String toString() {
        return date + " | " + time + " | " + description + " | " + vendor +" | " + amount;
    }

    //Parse a transaction from CSV line
    public static Transaction fromCSV(String csvLine) {
        String[] parts = csvLine.split("\\|");
        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[1]);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);
    }

    //Convert a transaction to a CSV line
    public String toCSV() {
        return date.toString() + "|" + time.toString() + "|" + description + "|" + vendor + "|" +amount;
    }

}
