/**
 * CheckingAccount - A specialized bank account for check transactions
 * Extends BankAccounts with check-specific functionality
 *
 * @author Student
 * @version 1.1
 */
public class CheckingAccount extends BankAccounts {
    private double minimumBalance;

    /**
     * Default constructor
     */
    public CheckingAccount() {
        super();
        this.minimumBalance = 0.0;
        addTransaction("Checking account created");
    }

    /**
     * Parameterized constructor
     *
     * @param accountNo      9-digit account number
     * @param accountName    Name of the account holder
     * @param minimumBalance Minimum balance required
     */
    public CheckingAccount(int accountNo, String accountName, double minimumBalance) {
        super(accountNo, accountName);
        this.minimumBalance = minimumBalance;
        addTransaction("Checking account created with minimum balance: " + minimumBalance);
    }

    /**
     * Get the minimum balance
     *
     * @return The minimum balance
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Encash a check
     *
     * @param amount The amount to encash
     * @throws IllegalArgumentException if amount exceeds balance or goes below minimum
     */
    public void encashCheck(double amount) {
        if (getStatus().equals("closed")) {
            throw new IllegalArgumentException("Cannot encash check on a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Check amount must be positive.");
        }
        if (amount > super.inquireBalance()) {
            throw new IllegalArgumentException("Insufficient balance to encash the check. Current balance: " + super.inquireBalance());
        }
        if ((super.inquireBalance() - amount) < minimumBalance) {
            throw new IllegalArgumentException("Encashing would drop balance below minimum required (" +
                    minimumBalance + "). Available for encashment: " +
                    (super.inquireBalance() - minimumBalance));
        }

        // Use setBalance to avoid the withdraw override
        setBalance(super.inquireBalance() - amount);
        addTransaction("Check encashed for " + amount + ". New balance: " + super.inquireBalance());
        System.out.println("Check encashed successfully for " + amount);
        System.out.println("Current balance: " + super.inquireBalance());
        System.out.println("Available for encashment: " + (super.inquireBalance() - minimumBalance));
    }

    /**
     * Withdraw is not allowed for checking accounts
     *
     * @param amount The amount to withdraw
     * @throws UnsupportedOperationException always
     */
    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Direct withdrawals are not allowed for Checking Accounts. " +
                "Please use encashCheck instead.");
    }

    /**
     * String representation of the checking account
     *
     * @return Formatted account information
     */
    @Override
    public String toString() {
        String accountInfo = "\n--- Account Information ---" +
                "\nAccount Type: Checking Account" +
                "\nAccount No: " + getAccountNo() +
                "\nName: " + getAccountName() +
                "\nBalance: " + super.inquireBalance() +
                "\nMinimum Balance: " + minimumBalance +
                "\nAvailable for encashment: " + (super.inquireBalance() - minimumBalance) +
                "\nStatus: " + getStatus();

        System.out.println(accountInfo);
        return accountInfo;
    }

    /**
     * Display account capabilities
     */
    @Override
    public void displayCapabilities() {
        System.out.println("\n--- Account Capabilities ---");
        System.out.println("This Checking Account allows:");
        System.out.println("- Deposits");
        System.out.println("- Check encashment");
        System.out.println("- Money transfers to other accounts");
        System.out.println("- Balance inquiries");
        System.out.println("\nLimitations:");
        System.out.println("- No direct withdrawals (use encash check instead)");
        System.out.println("- Minimum balance requirement: " + minimumBalance);
        System.out.println("- Available for encashment:" + (super.inquireBalance() - minimumBalance));
    }
}