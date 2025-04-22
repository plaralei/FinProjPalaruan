/**
 * BankAccounts - Super class that represents a basic bank account
 * This class serves as the parent class for specialized account types
 *
 * @author Student
 * @version 1.1
 */
public class BankAccounts {
    private int accountNo; // 9 digits
    private String accountName;
    private double balance;
    private String status; // active or closed
    private String[] transactionHistory;
    private int transactionCount;
    private static final int MAX_TRANSACTIONS = 10;

    /**
     * Default constructor
     * Sets status to active and balance to 0
     */
    public BankAccounts() {
        this.status = "active";
        this.balance = 0.0;
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }

    /**
     * Parameterized constructor
     *
     * @param accountNo    9-digit account number
     * @param accountName  Name of the account holder
     */
    public BankAccounts(int accountNo, String accountName) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.status = "active";
        this.balance = 0.0;
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.transactionCount = 0;
        addTransaction("Account created");
    }

    /**
     * Get the account number
     *
     * @return The account number
     */
    public int getAccountNo() {
        return accountNo;
    }

    /**
     * Get the account name
     *
     * @return The account name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Get the account status
     *
     * @return The account status (active or closed)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the account number
     *
     * @param accountNo The new account number
     */
    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
        addTransaction("Account number updated to " + accountNo);
    }

    /**
     * Set the account name
     *
     * @param accountName The new account name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
        addTransaction("Account name updated to " + accountName);
    }

    /**
     * Add a transaction to the history
     *
     * @param transaction The transaction description
     */
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

    /**
     * Deposit money into the account
     *
     * @param amount The amount to deposit
     * @throws IllegalArgumentException if amount is negative or account is closed
     */
    public void deposit(double amount) {
        if (status.equals("closed")) {
            throw new IllegalArgumentException("Cannot deposit to a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        addTransaction("Deposited " + amount + ". New balance: " + balance);
        System.out.println("Successfully deposited" + amount);
        System.out.println("Current balance: " + balance);
    }

    /**
     * Withdraw money from the account
     *
     * @param amount The amount to withdraw
     * @throws IllegalArgumentException if insufficient balance or account is closed
     */
    public void withdraw(double amount) {
        if (status.equals("closed")) {
            throw new IllegalArgumentException("Cannot withdraw from a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal. Current balance: " + balance);
        }
        balance -= amount;
        addTransaction("Withdrew" + amount + ". New balance: " + balance);
        System.out.println("Successfully withdrew" + amount);
        System.out.println("Current balance: " + balance);
    }

    /**
     * Get the current balance
     *
     * @return The current balance
     */
    public double inquireBalance() {
        System.out.println("Account #" + accountNo + " Balance: " + balance);
        return balance;
    }

    /**
     * Transfer money to another account
     *
     * @param acctno The account number to transfer to
     * @param amount The amount to transfer
     * @throws IllegalArgumentException if insufficient balance or invalid target account
     */
    public void transferMoney(int acctno, double amount) {
        if (status.equals("closed")) {
            throw new IllegalArgumentException("Cannot transfer from a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance for transfer. Current balance: " + balance);
        }

        // Note: In the actual implementation, the AccountsMain class will handle finding
        // the target account and completing the transfer
        balance -= amount;
        addTransaction("Transferred " + amount + " to account #" + acctno + ". New balance: " + balance);
        System.out.println("Successfully transferred " + amount + " to account #" + acctno);
        System.out.println("Current balance: " + balance);
    }

    /**
     * Close the account
     * Sets status to closed and withdraws all money
     */
    public void closeAccount() {
        if (status.equals("closed")) {
            throw new IllegalArgumentException("Account is already closed.");
        }
        addTransaction("Account closed. Final balance:" + balance);
        this.status = "closed";
        this.balance = 0.0;
        System.out.println("Account #" + accountNo + " has been closed. All funds have been withdrawn.");
    }

    /**
     * Display transaction history
     */
    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History for Account #" + accountNo + " ---");
        if (transactionCount == 0) {
            System.out.println("No transactions recorded.");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println((i + 1) + ". " + transactionHistory[i]);
            }
        }
    }

    /**
     * String representation of the account
     *
     * @return Formatted account information
     */
    @Override
    public String toString() {
        String accountInfo = "\n--- Account Information ---" +
                "\nAccount Type: Regular Bank Account" +
                "\nAccount No: " + accountNo +
                "\nName: " + accountName +
                "\nBalance:" + balance +
                "\nStatus: " + status;

        System.out.println(accountInfo);
        return accountInfo;
    }

    /**
     * Set the balance directly (protected method for subclasses)
     *
     * @param balance The new balance value
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Set the status directly (protected method for subclasses)
     *
     * @param status The new status value
     */
    protected void setStatus(String status) {
        this.status = status;
    }

    /**
     * Display account capabilities
     */
    public void displayCapabilities() {
        System.out.println("\n--- Account Capabilities ---");
        System.out.println("This Regular Bank Account allows:");
        System.out.println("- Deposits");
        System.out.println("- Withdrawals");
        System.out.println("- Money transfers to other accounts");
        System.out.println("- Balance inquiries");
    }
}