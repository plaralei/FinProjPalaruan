package Group2BankSystem;


import java.io.Serializable;
import java.util.Date;


/**
 * Base BankAccount class that serves as a parent class for all account types
 */
public class BankAccount implements Serializable {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected String accountType;
    protected boolean isActive;
    protected Date dateCreated;
    protected Date dateLastUpdated;


    public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.accountType = "Bank Account";
        this.isActive = true;
        this.dateCreated = new Date();
        this.dateLastUpdated = new Date();
    }


    // Constructor for loading from existing data
    public BankAccount(String accountNumber, String accountHolderName, double balance,
                       boolean isActive, Date dateCreated, Date dateLastUpdated) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = "Bank Account";
        this.isActive = isActive;
        this.dateCreated = dateCreated;
        this.dateLastUpdated = dateLastUpdated;
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


    // Common account operations
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        updateLastModifiedDate();
        return true;
    }


    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        updateLastModifiedDate();
        return true;
    }


    public boolean transfer(BankAccount targetAccount, double amount) {
        if (amount <= 0 || amount > balance || !isActive || !targetAccount.isActive()) {
            return false;
        }
        balance -= amount;
        targetAccount.deposit(amount);
        updateLastModifiedDate();
        return true;
    }


    public void closeAccount() {
        isActive = false;
        updateLastModifiedDate();
    }


    @Override
    public String toString() {
        return String.format("%s [%s] - %s - Balance: $%.2f %s",
                accountType, accountNumber, accountHolderName, balance,
                isActive ? "Active" : "Closed");
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    public int getAccountNumberInt() {
        return 0;
    }
}

