import exceptions.AccountClosedException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.TransactionLimitException;

public class BankAccounts {
    private int accountNo; // 9 digits
    private String accountName;
    private double balance;
    private String status; // active or closed
    private String[] transactionHistory;
    private int transactionCount;
    private static final int MAX_TRANSACTIONS = 10;

    public BankAccounts() {
        this.status = "active";
        this.balance = 0.0;
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }

    public BankAccounts(int accountNo, String accountName) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.status = "active";
        this.balance = 0.0;
        this.transactionHistory = new String[MAX_TRANSACTIONS];
        this.transactionCount = 0;
        addTransaction("Account created");
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getStatus() {
        return status;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
        addTransaction("Account number updated to " + accountNo);
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        addTransaction("Account name updated to " + accountName);
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

    public void deposit(double amount) {
        if (status.equals("closed")) {
            throw new AccountClosedException(String.valueOf(accountNo), "deposit");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "deposit");
        }
        balance += amount;
        addTransaction("Deposited " + amount + ". New balance: " + balance);
        System.out.println("Successfully deposited " + amount);
        System.out.println("Current balance: " + balance);
    }

    public void withdraw(double amount) {
        if (status.equals("closed")) {
            throw new AccountClosedException(String.valueOf(accountNo), "withdrawal");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "withdrawal");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(String.valueOf(accountNo), amount, balance);
        }
        balance -= amount;
        addTransaction("Withdrew " + amount + ". New balance: " + balance);
        System.out.println("Successfully withdrew " + amount);
        System.out.println("Current balance: " + balance);
    }

    public double inquireBalance() {
        System.out.println("Account #" + accountNo + " Balance: " + balance);
        return balance;
    }

    public void transferMoney(int acctno, double amount) {
        if (status.equals("closed")) {
            throw new AccountClosedException(String.valueOf(accountNo), "transfer");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "transfer");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(String.valueOf(accountNo), amount, balance);
        }

        // Check for any transaction limits (example: limit of PHP 10,000 per transfer)
        double transferLimit = 10000.0;  // This could be a property of the account
        if (amount > transferLimit) {
            throw new TransactionLimitException(String.valueOf(accountNo), "transfer", amount, transferLimit);
        }

        // Note: In the actual implementation, the AccountsMain class will handle finding
        // the target account and completing the transfer
        balance -= amount;
        addTransaction("Transferred " + amount + " to account #" + acctno + ". New balance: " + balance);
        System.out.println("Successfully transferred " + amount + " to account #" + acctno);
        System.out.println("Current balance: " + balance);
    }

    public void closeAccount() {
        if (status.equals("closed")) {
            throw new AccountClosedException(String.valueOf(accountNo), "close");
        }
        addTransaction("Account closed. Final balance:" + balance);
        this.status = "closed";
        this.balance = 0.0;
        System.out.println("Account #" + accountNo + " has been closed. All funds have been withdrawn.");
    }

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

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    public void displayCapabilities() {
        System.out.println("\n--- Account Capabilities ---");
        System.out.println("This Regular Bank Account allows:");
        System.out.println("- Deposits");
        System.out.println("- Withdrawals");
        System.out.println("- Money transfers to other accounts");
        System.out.println("- Balance inquiries");
    }
}

