package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class AccountingLedgerApp {
    public static void main(String[] args) {

        //Used to get user input
        Scanner userInput = new Scanner(System.in);
        //Controls the main loop
        boolean appOn = true;

        System.out.println(" Welcome to the Accounting Ledger App!");

        //Keeps loop going while appOn is true
        while (appOn) {
            //Method to display home menu
            printHomeMenu();
            System.out.println("Enter your choice: ");
            //Takes user input trims it and makes it upper case in variable userChoice
            String userChoice = userInput.nextLine().trim().toUpperCase();

            //Based off userInput calls the right method
            switch (userChoice) {
                case "D":
                    addDeposit(userInput); //Adds a deposit
                    break;
                case "P":
                    addPayment(userInput); //Adds a payment
                    break;
                case "L":
                    showLedger(userInput); //Displays the ledger
                    break;
                case "X":
                    appOn = false; //Exits the app
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        //Closes scanner
        userInput.close();

    }

    //Method to print home menu
    private static void printHomeMenu() {
        System.out.println("\n=== Home Menu ===\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Ledger");
        System.out.println("X) Exit\n");
    }
    //Method to add deposit using userInput from scanner
    private static void addDeposit(Scanner userInput) {

        try {
            System.out.println("\n=== Add Deposit ===\n");

            //Ask user to enter date and if user enters nothing then it uses today's date otherwise parse provide data from user
            System.out.println("Enter date (yyyy-MM-dd) or press Enter for today: ");
            String dateInput = userInput.nextLine().trim();
            LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

            //Ask user to enter time and if user enters nothing then it uses current time otherwise parse provide data from user
            System.out.println("Enter time (HH:mm:ss) or press Enter for current time: ");
            String timeInput = userInput.nextLine().trim();
            LocalTime time = timeInput.isEmpty() ? LocalTime.now() : LocalTime.parse(timeInput);

            //Ask user for description
            System.out.println("Enter description: ");
            String description = userInput.nextLine().trim();

            //Ask user for vendor
            System.out.println("Enter vendor: ");
            String vendor = userInput.nextLine().trim();

            //Ask user for deposit amount
            System.out.println("Enter deposit amount: ");
            double amount = Double.parseDouble(userInput.nextLine().trim());

            //Checks if the amount is positive
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return;
            }

            //Creates a new Transaction and saves it the CSV files
            Transaction deposit = new Transaction(date, time, description, vendor, amount);
            TransactionManager.saveTransaction(deposit);

            System.out.println("Deposit added successfully!");

            //Catches errors and prints out message
        } catch (Exception e) {
            System.out.println("Error adding deposit: " + e.getMessage());
        }


    }
    //Method to add payment
    private static void addPayment(Scanner userInput) {
        try {
            System.out.println("\n=== Add Payment ===\n");

            //Ask user to enter date and if user enters nothing then it uses today's date otherwise parse provide data from user
            System.out.println("Enter date (yyyy-MM-dd) or press Enter for today: ");
            String dateInput = userInput.nextLine().trim();
            LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

            //Ask user to enter time and if user enters nothing then it uses current time otherwise parse provide data from user
            System.out.println("Enter time (HH:mm:ss) or press Enter for current time: ");
            String timeInput = userInput.nextLine().trim();
            LocalTime time = timeInput.isEmpty() ? LocalTime.now() : LocalTime.parse(timeInput);

            //Ask user for description
            System.out.println("Enter description: ");
            String description = userInput.nextLine().trim();

            //Ask user for vendor
            System.out.println("Enter vendor: ");
            String vendor = userInput.nextLine().trim();

            //Ask user for deposit amount
            System.out.println("Enter payment amount: ");
            double amount = Double.parseDouble(userInput.nextLine().trim());

            //Checks if the amount is positive
            if (amount <= 0) {
                System.out.println("Payment amount must be positive.");
                return;
            }

            //Flips it to negative amount
            amount = -amount;

            //Creates a new Transaction and saves it the CSV files
            Transaction payment = new Transaction(date, time, description, vendor, amount);
            TransactionManager.saveTransaction(payment);

            System.out.println("Payment added successfully!");

            //Catches errors and prints out message
        } catch (Exception e) {
            System.out.println("Error adding payment: " + e.getMessage());
        }
    }
    //Method to show ledger
    private static void showLedger (Scanner userInput) {
        System.out.println("\n=== Ledger ===\n");

        //Loads all transactions
        List<Transaction> transactions = TransactionManager.loadTransactions();

        //Using .isEmpty List method to check if the list empty and prints out message.
        // (returns ture by default)
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        //Using Sort method on transactions to sort newest first then time using lambda operator (->)
        transactions.sort((t1, t2) -> {
            //Compares dates of the transactions
            int compareDate = t2.getDate().compareTo(t1.getDate());
            //If dates are different then return results (! = not)
            //If compareDate is not 0 the dates are different
            if (compareDate != 0) {
                return compareDate;
              //If dates are the same then compare times
            } else {
                return t2.getTime().compareTo(t1.getTime());
            }
        });
            //Prints out the formated ledger header. (the variables are left aligned by - on the left of the #,
            // given char spaces provide by #,
            // and the s stands for string. %n is a platform independent new line)
            System.out.printf("%-12s %-20s %-30s %-20s %-10s%n", "Date", "Time", "Description", "Vendor", "Amount");
            System.out.println("---------------------------------------------------------------------------------------------");

            //For each Transaction in the transactions list it gets printed in the formatted way
            for (Transaction t : transactions) {
                System.out.printf("%-12s %-20s %-30s %-20s %-10.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }

