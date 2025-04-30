package Group2BankSystem.exceptions;

/**
 * Exception thrown when a transaction exceeds defined limits.
 * This exception is used when operations exceed daily limits, 
 * transfer limits, withdrawal limits, etc.
 */
public class TransactionLimitException extends RuntimeException {
    
    private final String accountNumber;
    private final String transactionType;
    private final double requestedAmount;
    private final double limitAmount;
    
    /**
     * Constructs a TransactionLimitException with transaction details
     *
     * @param accountNumber The account number where the transaction was attempted
     * @param transactionType The type of transaction (withdrawal, transfer, etc.)
     * @param requestedAmount The amount that was requested for the transaction
     * @param limitAmount The maximum allowed amount for this type of transaction
     */
    public TransactionLimitException(String accountNumber, String transactionType, 
                                    double requestedAmount, double limitAmount) {
        super(String.format("Transaction limit exceeded for %s on account %s. Requested: %.2f, Limit: %.2f", 
              transactionType, accountNumber, requestedAmount, limitAmount));
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.requestedAmount = requestedAmount;
        this.limitAmount = limitAmount;
    }
    
    /**
     * @return The account number where the limit was exceeded
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * @return The type of transaction that exceeded the limit
     */
    public String getTransactionType() {
        return transactionType;
    }
    
    /**
     * @return The amount that was requested for the transaction
     */
    public double getRequestedAmount() {
        return requestedAmount;
    }
    
    /**
     * @return The maximum allowed amount for this type of transaction
     */
    public double getLimitAmount() {
        return limitAmount;
    }
}
