package com.pluralsight;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    private static final  String FILE_PATH = "transaction.csv";

    //Method to load all transactions into a list called Transaction
    public static List<Transaction> loadTransactions() {
        //Creates an empty list to store transaction objects
        List<Transaction> transactions = new ArrayList<>();
        try {
            //Read all the lines and stores them as strings in a list
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            //Every line is parsed into a Transaction using fromCSV method then added to transactions list
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
