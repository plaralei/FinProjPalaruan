package Group2BankSystem;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionManager {
    private static final String FILE_NAME = "transactions.txt";
    private static List<Transaction> transactions = new ArrayList<>();

    public static void saveTransaction(Transaction t) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            out.println(t.transactionId + "," + df.format(t.date) + "," + t.accountNumber + "," + t.type + "," + t.amount + "," + t.description);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> loadTransactions() {
        transactions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 6) {
                    String id = parts[0];
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1]);
                    String accountNumber = parts[2];
                    String type = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    String description = parts[5];
                    transactions.add(new Transaction(id, date, accountNumber, type, amount, description));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static List<Transaction> getTransactions() {
        return loadTransactions();
    }
}