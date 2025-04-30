import java.util.*;
import java.time.LocalDateTime.*;

public class generateReport extends BankAccounts {

    public static void dailyTransactions(List<BankAccounts> accounts) {
        String today = java.time.LocalDate.now().toString();
        System.out.println("\n--- Daily Transactions (" + today + ") ---");

        for (BankAccounts account : accounts) {
            if (account != null) {
                boolean hasTodayTransaction = false;
                for (String t : account.getTransactionHistory()) {
                    if (t != null && t.startsWith(today)) {
                        if (!hasTodayTransaction) {
                            String accountType = account.getClass().getSimpleName();
                            System.out.println("\nAccount No: " + account.getAccountNo() + " (" + accountType + ")");
                            hasTodayTransaction = true;
                        }
                        System.out.println("  " + t);
                    }
                }
            }
        }
    }

    public static void editTransaction(List<BankAccounts> accounts, int accountNo, int index, String newEntry) {
        for (BankAccounts account : accounts) {
            if (account != null && account.getAccountNo() == accountNo) {
                if (index < 0 || index >= account.getTransactionCount()) {
                    System.out.println("Invalid transaction index.");
                } else {
                    account.editTransaction(index, newEntry);
                    System.out.println("Transaction updated successfully.");
                }
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public static void summaryByType(List<BankAccounts> accounts, String typeKeyword) {
        System.out.println("\n--- Summary of Transactions: Type = " + typeKeyword + " ---");

        for (BankAccounts account : accounts) {
            if (account != null) {
                List<String> matches = new ArrayList<>();
                for (String t : account.getTransactionHistory()) {
                    if (t != null && t.toLowerCase().contains(typeKeyword.toLowerCase())) {
                        matches.add(t);
                    }
                }
                if (!matches.isEmpty()) {
                    System.out.println("\nAccount No: " + account.getAccountNo());
                    for (String t : matches) {
                        System.out.println("  " + t);
                    }
                }
            }
        }
    }


    public static void keywordSearch(List<BankAccounts> accounts, String keyword) {
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

