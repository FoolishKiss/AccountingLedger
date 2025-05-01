package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    //Constant variable that holds the file the app reads from and writes to
    private static final  String FILE_PATH = "transaction.csv";

    //Method reads from the file and gives a list of all transactions
    public static List<Transaction> loadTransactions() {
        //Creates an empty list to store transaction objects
        List<Transaction> transactions = new ArrayList<>();
        try {
            //Read all the lines from the file
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            //Loops through every line and is parsed into a Transaction using fromCSV method then added to transactions list
            for (String line : lines) {
                Transaction t = Transaction.fromCSV(line);
                transactions.add(t);
            }
            //If error like file not found catch the exception and print message
        } catch (IOException e) {
            System.out.println("Error reading transaction file: " + e.getMessage());
        }
        //returns the loaded list
        return transactions;
    }

    //Method to append a transaction to the file
    public static void saveTransaction(Transaction transaction) {
        //Use BufferedWriter to append to transactions.csv
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            //writes the transaction using the toCSV()
            writer.write(transaction.toCSV());
            writer.newLine();

            //Catches writing errors and prints out message
        } catch (IOException e) {
            System.out.println("Error writing transaction: " + e.getMessage());
        }
    }
}
