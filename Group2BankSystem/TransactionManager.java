package Group2BankSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionManager {
    private static final String TRANSACTIONS_FILE = "transactions.dat";
    private static List<GenerateReportPanel.Transaction> transactions;

    static {
        transactions = loadTransactions();
    }

    public static List<GenerateReportPanel.Transaction> getTransactions() {
        return transactions;
    }

    public static void addTransaction(String accountNumber, String type, double amount, String description) {
        String transactionId = generateTransactionId();
        GenerateReportPanel.Transaction transaction = new GenerateReportPanel.Transaction(
                transactionId, new Date(), accountNumber, type, amount, description
        );
        transactions.add(transaction);
        saveTransactions();
    }

    private static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    @SuppressWarnings("unchecked")
    private static List<GenerateReportPanel.Transaction> loadTransactions() {
        List<GenerateReportPanel.Transaction> loadedTransactions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TRANSACTIONS_FILE))) {
            loadedTransactions = (List<GenerateReportPanel.Transaction>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No transactions file found. Starting with empty transactions list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return loadedTransactions;
    }

    private static void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRANSACTIONS_FILE))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }
}