package Group2BankSystem;

import java.util.Date;

public class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.accountType = "Checking Account";
        this.overdraftLimit = 100.0;
    }

    public boolean encashCheck(double amount) {
        if (!isActive || amount <= 0 || amount > (balance + overdraftLimit)) {
            return false;
        }
        balance -= amount;
        updateLastModifiedDate();
        return true;
    }

    public double getCreditBalance() {
        return balance + overdraftLimit;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s - Balance: %.2f - Credit Balance: %.2f %s",
                accountType, accountNumber, accountHolderName, balance, getCreditBalance(),
                isActive ? "Active" : "Closed");
    }
}