/*
public class InvestmentAccount extends BankAccounts {
    private final double minimumBalance;
    private final double interest; // Interest rate (e.g., 0.10 for 10%)


    public InvestmentAccount() {
        super();
        this.minimumBalance = 0.0;
        this.interest = 0.0;
        addTransaction("Investment account created");
    }


    public InvestmentAccount(int accountNo, String accountName, double minimumBalance, double interest) {
        super(accountNo, accountName);
        this.minimumBalance = minimumBalance;
        this.interest = interest;
        addTransaction("Investment account created with minimum balance: " + minimumBalance +
                " and interest rate: " + (interest * 100) + "%");
    }


    public double getMinimumBalance() {
        return minimumBalance;
    }


    public double getInterest() {
        return interest;
    }


    public void addInvestment(double amount) {
        if (getStatus().equals("closed")) {
            throw new IllegalArgumentException("Cannot add investment to a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Investment amount must be positive.");
        }
        super.setBalance(super.inquireBalance() + amount);
        addTransaction("Investment of " + amount + " added. New balance: " + super.inquireBalance());
        System.out.println("Investment of " + amount + " added successfully.");
        System.out.println("Current balance: " + super.inquireBalance());
        System.out.println("Current investment value: " + inquireInvestmentValue());
    }


    public double inquireInvestmentValue() {
        double currentValue = super.inquireBalance() * (1 + interest);
        System.out.println("Account #" + getAccountNo() + " Investment Value: " + currentValue +
                " (includes " + (interest * 100) + "% interest)");
        return currentValue;
    }


    @Override
    public void closeAccount() {
        if (getStatus().equals("closed")) {
            throw new IllegalArgumentException("Account is already closed.");
        }
        double finalValue = super.inquireBalance() * (1 + interest);
        addTransaction("Account closed. Final investment value: " + finalValue);
        setStatus("closed");
        setBalance(0.0);
        System.out.println("Investment account #" + getAccountNo() + " has been closed.");
        System.out.println("Final investment value withdrawn: " + finalValue);
    }


    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed for Investment Accounts. " +
                "You can only close the account to withdraw funds.");
    }


    @Override
    public void transferMoney(int acctno, double amount) {
        throw new UnsupportedOperationException("Transfers are not allowed for Investment Accounts. " +
                "You can only close the account to withdraw funds.");
    }

    @Override
    public String toString() {
        String accountInfo = "\n--- Account Information ---" +
                "\nAccount Type: Investment Account" +
                "\nAccount No: " + getAccountNo() +
                "\nName: " + getAccountName() +
                "\nBase Balance: " + super.inquireBalance() +
                "\nInterest Rate: " + (interest * 100) + "%" +
                "\nCurrent Investment Value: " + (super.inquireBalance() * (1 + interest)) +
                "\nMinimum Balance: " + minimumBalance +
                "\nStatus: " + getStatus();

        System.out.println(accountInfo);
        return accountInfo;
    }


    @Override
    public void displayCapabilities() {
        System.out.println("\n--- Account Capabilities ---");
        System.out.println("This Investment Account allows:");
        System.out.println("- Adding investments (deposits)");
        System.out.println("- Inquiring investment value (with " + (interest * 100) + "% interest)");
        System.out.println("- Closing account (withdraws investment value)");
        System.out.println("\nLimitations:");
        System.out.println("- No direct withdrawals");
        System.out.println("- No money transfers");
        System.out.println("- Minimum balance requirement:" + minimumBalance);
    }
}

 */