package Group2BankSystem;

import java.util.Date;

public class CreditCardAccount extends BankAccount {
    private double creditLimit;
    private double availableCredit;

    public CreditCardAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.accountType = "Credit Card Account";
        this.creditLimit = 1000.0;
        this.availableCredit = creditLimit;
    }

    public boolean chargeToCard(double amount) {
        if (!isActive || amount <= 0 || amount > availableCredit) {
            return false;
        }
        balance -= amount;
        availableCredit -= amount;
        updateLastModifiedDate();
        return true;
    }

    public boolean payCard(double amount) { if (!isActive || amount <= 0) {
        return false;
    }
        balance += amount;
        availableCredit += amount;

        if (balance > 0) {
            availableCredit = creditLimit;
            balance = 0;
        }

        updateLastModifiedDate();
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s - Balance Due: %.2f - Credit Limit: %.2f - Available: %.2f %s",
                accountType, accountNumber, accountHolderName, -balance, creditLimit, availableCredit,
                isActive ? "Active" : "Closed");
    }
}
