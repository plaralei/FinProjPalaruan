import exceptions.AccountClosedException;
import exceptions.InvalidAmountException;

public class CreditCardAccount extends BankAccounts {
    private double creditLimit;
    private double charges;

    public CreditCardAccount() {
        super();
        this.creditLimit = 0.0;
        this.charges = 0.0;
        addTransaction("Credit card account created");
    }

    public CreditCardAccount(int accountNo, String accountName, double creditLimit) {
        super(accountNo, accountName);
        this.creditLimit = creditLimit;
        this.charges = 0.0;
        addTransaction("Credit card account created with credit limit: " + creditLimit);
    }

    public CreditCardAccount(int accountNo, String accountName, double creditLimit, double charges) {
        super(accountNo, accountName);
        this.creditLimit = creditLimit;
        this.charges = charges;
        addTransaction("Credit card account created with credit limit: " + creditLimit +
                " and initial charges: " + charges);
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getCharges() {
        return charges;
    }

    public void payCard(double amount) {
        if (getStatus().equals("closed")) {
            throw new AccountClosedException(String.valueOf(getAccountNo()), "payment");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "card payment");
        }
        if (amount > charges) {
            throw new InvalidAmountException(amount, "card payment", 
                "Payment exceeds current charges (" + charges + ").");
        }

        charges -= amount;
        addTransaction("Payment of " + amount + " processed. Remaining charges: " + charges);
        System.out.println("Payment of " + amount + " processed successfully.");
        System.out.println("Remaining charges: " + charges);
        System.out.println("Available credit: " + (creditLimit - charges));
    }

    public double inquireAvailableCredit() {
        double availableCredit = creditLimit - charges;
        System.out.println("Account #" + getAccountNo() + " Available Credit:" + availableCredit);
        System.out.println("Credit Limit:  + creditLimit");
        System.out.println("Current Charges: " + charges);
        return availableCredit;
    }

    public void chargeToCard(double amount) {
        if (getStatus().equals("closed")) {
            throw new AccountClosedException(String.valueOf(getAccountNo()), "charge");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "card charge");
        }
        if (amount > (creditLimit - charges)) {
            throw new InvalidAmountException(amount, "card charge",
                "Charge exceeds available credit (" + (creditLimit - charges) + ").");
        }

        charges += amount;
        addTransaction("Charge of " + amount + " processed. Updated charges: " + charges);
        System.out.println("Charge of " + amount + " processed successfully.");
        System.out.println("Updated charges: " + charges);
        System.out.println("Available credit: " + (creditLimit - charges));
    }

    public void getCashAdvance(double amount) {
        if (getStatus().equals("closed")) {
            throw new AccountClosedException(String.valueOf(getAccountNo()), "cash advance");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount, "cash advance");
        }

        double availableCredit = creditLimit - charges;
        double maxAdvance = availableCredit * 0.5;

        if (amount > maxAdvance) {
            throw new InvalidAmountException(amount, "cash advance",
                "Cash advance exceeds 50% of available credit (" + maxAdvance + ").");
        }

        charges += amount;
        addTransaction("Cash advance of " + amount + " processed. Updated charges: " + charges);
        System.out.println("Cash advance of " + amount + " processed successfully.");
        System.out.println("Updated charges: " + charges);
        System.out.println("Available credit: " + (creditLimit - charges));
    }

    @Override
    public void deposit(double amount) {
        throw new UnsupportedOperationException("Deposits are not allowed for Credit Card Accounts. " +
                "Please use payCard instead.");
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed for Credit Card Accounts. " +
                "Please use getCashAdvance instead.");
    }

    @Override
    public void transferMoney(int acctno, double amount) {
        throw new UnsupportedOperationException("Transfers are not allowed for Credit Card Accounts.");
    }

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
