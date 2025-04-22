/**
 * CreditCardAccount - A specialized bank account for credit card operations
 * Extends BankAccounts with credit card functionality
 *
 * @author Student
 * @version 1.1
 */
public class CreditCardAccount extends BankAccounts {
    private double creditLimit;
    private double charges;

    /**
     * Default constructor
     */
    public CreditCardAccount() {
        super();
        this.creditLimit = 0.0;
        this.charges = 0.0;
        addTransaction("Credit card account created");
    }

    /**
     * Parameterized constructor with charges
     *
     * @param accountNo   9-digit account number
     * @param accountName Name of the account holder
     * @param creditLimit Credit limit amount
     */
    public CreditCardAccount(int accountNo, String accountName, double creditLimit) {
        super(accountNo, accountName);
        this.creditLimit = creditLimit;
        this.charges = 0.0;
        addTransaction("Credit card account created with credit limit: " + creditLimit);
    }

    /**
     * Parameterized constructor with charges
     *
     * @param accountNo   9-digit account number
     * @param accountName Name of the account holder
     * @param creditLimit Credit limit amount
     * @param charges     Initial charges
     */
    public CreditCardAccount(int accountNo, String accountName, double creditLimit, double charges) {
        super(accountNo, accountName);
        this.creditLimit = creditLimit;
        this.charges = charges;
        addTransaction("Credit card account created with credit limit: " + creditLimit +
                " and initial charges: " + charges);
    }

    /**
     * Get the credit limit
     *
     * @return The credit limit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * Get the current charges
     *
     * @return The current charges
     */
    public double getCharges() {
        return charges;
    }

    /**
     * Pay off card balance
     *
     * @param amount The amount to pay
     * @throws IllegalArgumentException if amount exceeds charges
     */
    public void payCard(double amount) {
        if (getStatus().equals("closed")) {
            throw new IllegalArgumentException("Cannot make payment on a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
        if (amount > charges) {
            throw new IllegalArgumentException("Payment exceeds current charges (" + charges + ").");
        }

        charges -= amount;
        addTransaction("Payment of " + amount + " processed. Remaining charges: " + charges);
        System.out.println("Payment of " + amount + " processed successfully.");
        System.out.println("Remaining charges: " + charges);
        System.out.println("Available credit: " + (creditLimit - charges));
    }

    /**
     * Get the available credit
     *
     * @return The available credit
     */
    public double inquireAvailableCredit() {
        double availableCredit = creditLimit - charges;
        System.out.println("Account #" + getAccountNo() + " Available Credit:" + availableCredit);
        System.out.println("Credit Limit:  + creditLimit");
        System.out.println("Current Charges: " + charges);
        return availableCredit;
    }

    /**
     * Charge an amount to the card
     *
     * @param amount The amount to charge
     * @throws IllegalArgumentException if amount exceeds available credit
     */
    public void chargeToCard(double amount) {
        if (getStatus().equals("closed")) {
            throw new IllegalArgumentException("Cannot charge to a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Charge amount must be positive.");
        }
        if (amount > (creditLimit - charges)) {
            throw new IllegalArgumentException("Charge exceeds available credit (" +
                    (creditLimit - charges) + ").");
        }

        charges += amount;
        addTransaction("Charge of " + amount + " processed. Updated charges: " + charges);
        System.out.println("Charge of " + amount + " processed successfully.");
        System.out.println("Updated charges: " + charges);
        System.out.println("Available credit: " + (creditLimit - charges));
    }

    /**
     * Get a cash advance
     *
     * @param amount The amount to advance
     * @throws IllegalArgumentException if amount exceeds 50% of available credit
     */
    public void getCashAdvance(double amount) {
        if (getStatus().equals("closed")) {
            throw new IllegalArgumentException("Cannot get cash advance on a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Cash advance amount must be positive.");
        }

        double availableCredit = creditLimit - charges;
        double maxAdvance = availableCredit * 0.5;

        if (amount > maxAdvance) {
            throw new IllegalArgumentException("Cash advance exceeds 50% of available credit (" +
                    maxAdvance + ").");
        }

        charges += amount;
        addTransaction("Cash advance of " + amount + " processed. Updated charges: " + charges);
        System.out.println("Cash advance of " + amount + " processed successfully.");
        System.out.println("Updated charges: " + charges);
        System.out.println("Available credit: " + (creditLimit - charges));
    }

    /**
     * Deposit is not allowed for credit card accounts
     *
     * @param amount The amount to deposit
     * @throws UnsupportedOperationException always
     */
    @Override
    public void deposit(double amount) {
        throw new UnsupportedOperationException("Deposits are not allowed for Credit Card Accounts. " +
                "Please use payCard instead.");
    }

    /**
     * Withdraw is not allowed for credit card accounts
     *
     * @param amount The amount to withdraw
     * @throws UnsupportedOperationException always
     */
    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed for Credit Card Accounts. " +
                "Please use getCashAdvance instead.");
    }

    /**
     * Transfer is not allowed for credit card accounts
     *
     * @param acctno The account number to transfer to
     * @param amount The amount to transfer
     * @throws UnsupportedOperationException always
     */
    @Override
    public void transferMoney(int acctno, double amount) {
        throw new UnsupportedOperationException("Transfers are not allowed for Credit Card Accounts.");
    }

    /**
     * String representation of the credit card account
     *
     * @return Formatted account information
     */
    @Override
    public String toString() {
        String accountInfo = "\n--- Account Information ---" +
                "\nAccount Type: Credit Card Account" +
                "\nAccount No: " + getAccountNo() +
                "\nName: " + getAccountName() +
                "\nCredit Limit: " + creditLimit +
                "\nCurrent Charges: " + charges +
                "\nAvailable Credit: " + (creditLimit - charges) +
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
        System.out.println("This Credit Card Account allows:");
        System.out.println("- Card payments (to reduce charges)");
        System.out.println("- Charging purchases to the card");
        System.out.println("- Cash advances (up to 50% of available credit)");
        System.out.println("- Available credit inquiries");
        System.out.println("\nLimitations:");
        System.out.println("- No direct deposits");
        System.out.println("- No direct withdrawals");
        System.out.println("- No money transfers");
        System.out.println("- Credit limit: " + creditLimit);
        System.out.println("- Maximum cash advance available:" + ((creditLimit - charges) * 0.5));
    }
}