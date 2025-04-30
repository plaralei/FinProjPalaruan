package Group2BankSystem;

import java.util.Date;

public class InvestmentAccount extends BankAccount {
    private double interestRate;
    private double totalInterestEarned;

    public InvestmentAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.accountType = "Investment Account";
        this.interestRate = 0.05; // 5% annual interest rate by default
        this.totalInterestEarned = 0.0;
    }

    public double computeMonthlyInterest() {
        if (!isActive) {
            return 0.0;
        }
        double monthlyInterest = balance * (interestRate / 12.0);
        balance += monthlyInterest;
        totalInterestEarned += monthlyInterest;
        updateLastModifiedDate();
        return monthlyInterest;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s - Balance: %.2f - Interest Rate: %.2f%% - Total Interest: %.2f %s",
                accountType, accountNumber, accountHolderName, balance, interestRate * 100,
                totalInterestEarned, isActive ? "Active" : "Closed");
    }
}
