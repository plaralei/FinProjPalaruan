package Group2BankSystem.exceptions;

/**
 * Exception thrown when an invalid amount is provided for a banking operation.
 * This exception is used when negative amounts, zero amounts, or otherwise 
 * invalid monetary values are provided for operations like deposits or withdrawals.
 */
public class InvalidAmountException extends RuntimeException {
    
    private final double amount;
    private final String operation;
    
    /**
     * Constructs an InvalidAmountException with operation details
     *
     * @param amount The invalid amount that was provided
     * @param operation The operation for which the amount was provided (deposit, withdrawal, etc.)
     */
    public InvalidAmountException(double amount, String operation) {
        super(String.format("Invalid amount %.2f for %s operation", amount, operation));
        this.amount = amount;
        this.operation = operation;
    }
    
    /**
     * Constructs an InvalidAmountException with a custom message
     *
     * @param amount The invalid amount that was provided
     * @param operation The operation for which the amount was provided
     * @param message Custom message explaining why the amount is invalid
     */
    public InvalidAmountException(double amount, String operation, String message) {
        super(message);
        this.amount = amount;
        this.operation = operation;
    }
    
    /**
     * @return The invalid amount that was provided
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * @return The operation for which the invalid amount was provided
     */
    public String getOperation() {
        return operation;
    }
}
