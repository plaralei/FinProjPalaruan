package Group2BankSystem.exceptions;

/**
 * Exception thrown when a banking operation requires more funds than are available in the account.
 * This exception is used when withdrawals, transfers, or payments exceed the available balance.
 */
public class InsufficientFundsException extends RuntimeException {
    
    private final String accountNumber;
    private final double requestedAmount;
    private final double availableBalance;
    
    /**
     * Constructs an InsufficientFundsException with account details
     *
     * @param accountNumber The account number where the operation was attempted
     * @param requestedAmount The amount that was requested for the operation
     * @param availableBalance The actual balance available in the account
     */
    public InsufficientFundsException(String accountNumber, double requestedAmount, double availableBalance) {
        super(String.format("Insufficient funds in account %s. Requested: %.2f, Available: %.2f", 
              accountNumber, requestedAmount, availableBalance));
        this.accountNumber = accountNumber;
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }
    
    /**
     * @return The account number where insufficient funds were encountered
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * @return The amount that was requested for the transaction
     */
    public double getRequestedAmount() {
        return requestedAmount;
    }
    
    /**
     * @return The available balance in the account
     */
    public double getAvailableBalance() {
        return availableBalance;
    }
    
    /**
     * @return The shortage amount (requested amount minus available balance)
     */
    public double getShortageAmount() {
        return requestedAmount - availableBalance;
    }
}
