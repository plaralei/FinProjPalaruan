package Group2BankSystem;

import java.io.Serializable;
import java.util.Date;

public class BankAccount extends MainFrame implements Serializable {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected String accountType;
    protected boolean isActive;
    protected Date dateCreated;
    protected Date dateLastUpdated;
    private String[] transactionHistory;
    private int transactionCount;
    private static final int MAX_TRANSACTIONS = 10;

    public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.accountType = "Bank Account";
        this.isActive = true;
        this.dateCreated = new Date();
        this.dateLastUpdated = new Date();
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.transactionCount = 0;
        addTransaction("Account created with initial deposit: " + initialDeposit);
    }

    public BankAccount(String accountNumber, String accountHolderName, double balance,
                       boolean isActive, Date dateCreated, Date dateLastUpdated) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = "Bank Account";
        this.isActive = isActive;
        this.dateCreated = dateCreated;
        this.dateLastUpdated = dateLastUpdated;
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        this.dateLastUpdated = new Date();
        addTransaction("Account holder name updated to " + accountHolderName);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        this.dateLastUpdated = new Date();
        addTransaction(active ? "Account activated" : "Account deactivated");
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void updateLastModifiedDate() {
        this.dateLastUpdated = new Date();
    }

    protected void addTransaction(String transaction) {
        if (transactionCount < MAX_TRANSACTIONS) {
            transactionHistory[transactionCount++] = transaction;
        } else {
            // Shift transactions to make room for the new one
            for (int i = 0; i < MAX_TRANSACTIONS - 1; i++) {
                transactionHistory[i] = transactionHistory[i + 1];
            }
            transactionHistory[MAX_TRANSACTIONS - 1] = transaction;
        }
    }

    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History for Account #" + accountNumber + " ---");
        if (transactionCount == 0) {
            System.out.println("No transactions recorded.");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println((i + 1) + ". " + transactionHistory[i]);
            }
        }
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        updateLastModifiedDate();
        addTransaction("Deposited " + amount + ". New balance: " + balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        updateLastModifiedDate();
        addTransaction("Withdrew " + amount + ". New balance: " + balance);
        return true;
    }

    public boolean transfer(BankAccount targetAccount, double amount) {
        if (amount <= 0 || amount > balance || !isActive || !targetAccount.isActive()) {
            return false;
        }
        balance -= amount;
        targetAccount.deposit(amount);
        updateLastModifiedDate();
        addTransaction("Transferred " + amount + " to account #" + targetAccount.getAccountNumber() + ". New balance: " + balance);
        return true;
    }

    public void closeAccount() {
        isActive = false;
        updateLastModifiedDate();
        addTransaction("Account closed. Final balance: " + balance);
        balance = 0.0;
        System.out.println("Account #" + accountNumber + " has been closed. All funds have been withdrawn.");
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s - Balance: %.2f %s",
                accountType, accountNumber, accountHolderName, balance,
                isActive ? "Active" : "Closed");
    }

    public void displayCapabilities() {
        System.out.println("\n--- Account Capabilities ---");
        System.out.println("This Bank Account allows:");
        System.out.println("- Deposits");
        System.out.println("- Withdrawals");
        System.out.println("- Money transfers to other accounts");
        System.out.println("- Balance inquiries");
        System.out.println("- Transaction history inquiries");
    }
}
