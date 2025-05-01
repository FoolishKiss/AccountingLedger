package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountingLedgerApp {
    public static void main(String[] args) {

        //Used to get user input
        Scanner userInput = new Scanner(System.in);
        //Controls the main loop
        boolean appOn = true;

        System.out.println("Welcome to the Accounting Ledger App!");

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

    //Method to display home menu
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
    //Method to add payment using userInput from scanner
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
    //Method to display ledger using userInput from scanner
    private static void showLedger (Scanner userInput) {
        //Loads all transactions from csv file using TransactionManager
        List<Transaction> transactions = TransactionManager.loadTransactions();

        //Using .isEmpty List method to check if the list is empty and prints out message.
        // (.isEmpty returns ture by default)
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        //Controls the ledger loop
        boolean ledgerMenu = true;

        //Keeps user in ledger menu until they exit back home
        while (ledgerMenu) {

            //Method to display ledger menu
            printLedgerMenu();

            //Ask user to pick a choice from the menu
            System.out.println("Enter your choice: ");

            //Stores the option user picked in variable userChoice. Trims and makes upper case
            String userChoice = userInput.nextLine().trim().toUpperCase();

            //Based off userChoice calls the right method
            switch (userChoice) {
                case "A" :
                    displayTransactions(transactions); //
                    break;
                case "D" :
                    displayTransactions(filterDeposits(transactions));
                    break;
                case "P" :
                    displayTransactions(filterPayments(transactions));
                    break;
                case "R" :
                    showReportsMenu(userInput, transactions);
                    break;
                case "H" :
                    ledgerMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        }

        }
    //Method to display ledger menu
    private static void printLedgerMenu() {
        System.out.println("\n=== Ledger Menu ===\n");
        System.out.println("A) All Transactions");
        System.out.println("D) Deposits Only");
        System.out.println("P) Payments Only");
        System.out.println("R) Reports");
        System.out.println("H) Home\n");
    }
    //Method to display any list of transactions passed to it
    private static void displayTransactions(List<Transaction> transactions) {
        //Using .isEmpty List method to check if the list is empty and prints out message.
        // (returns ture by default)
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions found.");
            return;
        }

        //Using Sort method on transactions to sort newest first then by time using (->)
        //Lambda operator - takes input and maps to the body  (inputs -> action)
        transactions.sort((t1, t2) -> {
            //Compares dates of the transactions
            int compareDate = t2.getDate().compareTo(t1.getDate());
            //If dates are different then return results
            //If compareDate is not 0 the dates are different (! = not)
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
    //Loops through all transactions, and adds only positive transactions to a list, then returns that filtered list
    private static List<Transaction> filterDeposits(List<Transaction> transactions) {
        //Creates an empty list to store deposit transactions.
        List<Transaction> deposits = new ArrayList<>();
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            //Checks to see if amount is positive then adds to deposits list
            if (t.getAmount() > 0) {
                deposits.add(t);
            }
        }
        return deposits;
    }
    //Loops through all transactions, and adds only negative transactions to a list, then returns that filtered list
    private static List<Transaction> filterPayments(List<Transaction> transactions) {
        //Creates an empty list to store payments transactions.
        List<Transaction> payments = new ArrayList<>();
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            //Checks to see if amount is negative then adds to payment list
            if (t.getAmount() < 0) {
                payments.add(t);
            }
        }
        return payments;
    }
    //Takes full list of transactions and will return only transactions from current month
    private static List<Transaction> filterMonthToDate(List<Transaction> transactions) {
        //Creates an empty list to store filtered transactions.
        List<Transaction> monthToDate = new ArrayList<>();
        //Get current date
        LocalDate now = LocalDate.now();
        //To get month and year
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            //Checks to see if the year matches the current year and the month matches the current month.
            if (t.getDate().getYear() == currentYear && t.getDate().getMonthValue() == currentMonth) {
                monthToDate.add(t);

            }
        }
        return monthToDate;
    }
    //Takes full list of transactions and will return only transactions from previous month
    private static List<Transaction> filterPreviousMonth(List<Transaction> transactions) {
        //Creates an empty list to store filtered transactions.
        List<Transaction> previousMonth = new ArrayList<>();
        //Get current date
        LocalDate now = LocalDate.now();
        //Subtracts one month from current day
        LocalDate lastMonth = now.minusMonths(1);
        //Month and year
        int lastMonthValue = lastMonth.getMonthValue();
        int lastMonthYear = lastMonth.getYear();
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            //Checks to see if the year matches the lastMonthYear and if the months matches lastMonthValue
            //and if both matches add it to the list.
            if (t.getDate().getYear() == lastMonthYear && t.getDate().getMonthValue() == lastMonthValue) {
                previousMonth.add(t);

            }
        }
        return previousMonth;
    }
    //Takes full list of transactions and will return only transactions from last year
    private static List<Transaction> filterPreviousYear(List<Transaction> transactions) {
        //Creates an empty list to store last year transactions.
        List<Transaction> previousYearTransactions = new ArrayList<>();
        //Get current date
        LocalDate now = LocalDate.now();
        //Subtracts one year from current year
        int lastYear = now.getYear() - 1;
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            //Checks to see if the year matches the last year and adds it to the list
            if (t.getDate().getYear() == lastYear) {
                previousYearTransactions.add(t);

            }
        }
        return previousYearTransactions;
    }
    //Takes full list of transactions and will return only transactions from Jan 1st of the year to current day
    private static List<Transaction> filterYearToDate(List<Transaction> transactions) {
        //Creates an empty list to store matching transactions.
        List<Transaction> yearToDate = new ArrayList<>();
        //Get current date
        LocalDate now = LocalDate.now();
        //Jan 1st of current year
        LocalDate startOfYear = LocalDate.of(now.getYear(), 1,1);
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            LocalDate transactionDate = t.getDate();
            //Checks to see if the transaction date is not before Jan 1 and not after today
            if (! transactionDate.isBefore(startOfYear) && ! transactionDate.isAfter(now)) {
                //Adds to the list if it passes both checks
                yearToDate.add(t);

            }
        }
        return yearToDate;
    }
    //Takes full list of transactions and will return only transactions that matches the vendor name the user input
    private static List<Transaction> filterByVendor(List<Transaction> transactions, String vendorName) {
        //Creates an empty list to store matching transactions.
        List<Transaction> matches = new ArrayList<>();
        //Loops through every Transaction in the transactions list.
        for (Transaction t : transactions) {
            //Checks to see if transactions vendor is same as the user input. (.contains() allows partial matches)
            if (t.getVendor().toLowerCase().contains(vendorName.toLowerCase())) {
                //Adds to the list if it matches
                matches.add(t);
            }
        }
        return matches;
    }
    //Method to display reports menu
    private static void showReportsMenu (Scanner userInput, List<Transaction> transactions) {

        //Controls the ledger loop
        boolean reportsMenu = true;

        //Keeps user in reports menu until they exit back to ledger
        while (reportsMenu) {

            //Method to display Reports menu
            printReportsMenu();

            //Ask user to pick a choice from the menu
            System.out.println("Enter your choice: ");

            //Stores the option user picked in variable userChoice. Trims and makes upper case
            String userChoice = userInput.nextLine().trim().toUpperCase();

            //Based off userChoice calls the right method
            switch (userChoice) {
                case "1" :
                    displayTransactions(filterMonthToDate(transactions));
                    break;
                case "2" :
                    displayTransactions(filterPreviousMonth(transactions));
                    break;
                case "3" :
                    displayTransactions(filterYearToDate(transactions));
                    break;
                case "4" :
                    displayTransactions(filterPreviousYear(transactions));
                    break;
                case "5" :
                    System.out.println("Enter vendor name to search: ");
                    String vendorName = userInput.nextLine().trim().toLowerCase();
                    displayTransactions(filterByVendor(transactions, vendorName));
                    break;
                case "0" :
                    reportsMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        }

    }
    //Method to display ledger menu
    private static void printReportsMenu() {
        System.out.println("\n=== Report Menu ===\n");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back to Ledger Menu\n");
    }



    }

