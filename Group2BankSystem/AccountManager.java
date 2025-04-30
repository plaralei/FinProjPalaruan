package Group2BankSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private static final String ACCOUNTS_FILE = "accounts.dat";
    private static List<BankAccount> accounts;

    static {
        accounts = loadAccounts();
    }

    public static List<BankAccount> getAccounts() {
        return accounts;
    }

    public static BankAccount getAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public static void addAccount(BankAccount account) {
        accounts.add(account);
        saveAccounts();
    }

    public static boolean updateAccount(BankAccount updatedAccount) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(updatedAccount.getAccountNumber())) {
                accounts.set(i, updatedAccount);
                saveAccounts();
                return true;
            }
        }
        return false;
    }

    public static boolean deleteAccount(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
                accounts.remove(i);
                saveAccounts();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static List<BankAccount> loadAccounts() {
        List<BankAccount> loadedAccounts = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNTS_FILE))) {
            loadedAccounts = (List<BankAccount>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No accounts file found. Starting with empty accounts list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
            e.printStackTrace();
        }
        return loadedAccounts;
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }
}