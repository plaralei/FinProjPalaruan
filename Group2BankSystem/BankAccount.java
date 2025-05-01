package Group2BankSystem;

import exceptions.*;
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
        if (!isActive) {
            throw new AccountClosedException(accountNumber, "deposit");
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "deposit", 
                "Deposit amount must be greater than zero");
        }
        
        balance += amount;
        updateLastModifiedDate();
        return true;
    }


    public boolean withdraw(double amount) {
        if (!isActive) {
            throw new AccountClosedException(accountNumber, "withdrawal");
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "withdrawal", 
                "Withdrawal amount must be greater than zero");
        }
        
        if (amount > balance) {
            throw new InsufficientFundsException(accountNumber, amount, balance);
        }
        
        balance -= amount;
        updateLastModifiedDate();
        return true;
    }


    public boolean transfer(BankAccount targetAccount, double amount) {
        if (!isActive) {
            throw new AccountClosedException(accountNumber, "transfer");
        }
        
        if (targetAccount == null) {
            throw new InvalidAccountException("unknown", "Target account does not exist");
        }
        
        if (!targetAccount.isActive()) {
            throw new AccountClosedException(targetAccount.getAccountNumber(), "transfer to");
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "transfer", 
                "Transfer amount must be greater than zero");
        }
        
        if (amount > balance) {
            throw new InsufficientFundsException(accountNumber, amount, balance);
        }
        
        // Check for any transaction limits (example: limit of $10,000 per transfer)
        double transferLimit = 10000.0;  // This could be a property of the account
        if (amount > transferLimit) {
            throw new TransactionLimitException(accountNumber, "transfer", amount, transferLimit);
        }
        
        balance -= amount;
        targetAccount.deposit(amount);
        updateLastModifiedDate();
        return true;
    }


    public void closeAccount() {
        if (!isActive) {
            throw new AccountClosedException(accountNumber, "close");
        }
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

