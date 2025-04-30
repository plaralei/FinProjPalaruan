import java.util.*;
import java.time.LocalDateTime.*;

public class generateReport {

    public static void dailyTransactions(BankAccounts[] accounts) {
        String today = java.time.LocalDate.now().toString();
        System.out.println("\n--- Daily Transactions (" + today + ") ---");
        for (BankAccounts account : accounts) {
            if (account != null) {
                for (String t : account.getTransactionHistory()) {
                    if (t != null && t.startsWith(today)) {
                        System.out.println("Account No: " + account.getAccountNo() + ": " + t);
                    }
                }
            }
        }
    }

    public static void editTransaction(BankAccounts account, int index, String newEntry) {
        if (index < 0 || index >= account.getTransactionCount()) {
            System.out.println("Invalid transaction index.");
            return;
        }
        account.editTransaction(index, newEntry);
        System.out.println("Transaction updated successfully.");
    }

    public static void summaryByType(BankAccounts[] accounts, String typeKeyword) {
        System.out.println("\n--- Summary of Transactions: Type = " + typeKeyword + " ---");
        for (BankAccounts account : accounts) {
            if (account != null) {
                for (String t : account.getTransactionHistory()) {
                    if (t != null && t.toLowerCase().contains(typeKeyword.toLowerCase())) {
                        System.out.println("Account No: " + account.getAccountNo() + ": " + t);
                    }
                }
            }
        }
    }

    public static void reportPerAccount(BankAccounts[] accounts) {
        System.out.println("\n=== Full Transaction Report Per Account ===");

        for (BankAccounts account : accounts) {
            if (account != null) {
                System.out.println("\nAccount No: " + account.getAccountNo());
                String[] history = account.getTransactionHistory();
                if (history == null || history.length == 0) {
                    System.out.println("  No transactions.");
                    continue;
                }

                for (String t : history) {
                    if (t != null && !t.trim().isEmpty()) {
                        System.out.println("  " + t);
                    }
                }
            }
        }
    }

    public static void keywordSearch(BankAccounts[] accounts, String keyword) {
        System.out.println("\n--- Keyword Search: \"" + keyword + "\" ---");
        for (BankAccounts account : accounts) {
            if (account != null) {
                for (String t : account.getTransactionHistory()) {
                    if (t != null && t.toLowerCase().contains(keyword.toLowerCase())) {
                        System.out.println("Account No: " + account.getAccountNo() + ": " + t);
                    }
                }
            }
        }
    }
}

