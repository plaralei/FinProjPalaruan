package Group2BankSystem.exceptions;

/**
 * Exception thrown when operations are attempted on a closed account.
 * This exception is used when transactions like deposits, withdrawals,
 * or transfers are attempted on accounts that have been closed.
 */
public class AccountClosedException extends RuntimeException {
    
    private final String accountNumber;
    private final String operation;
    
    /**
     * Constructs an AccountClosedException with account details
     *
     * @param accountNumber The account number of the closed account
     * @param operation The operation that was attempted on the closed account
     */
    public AccountClosedException(String accountNumber, String operation) {
        super(String.format("Cannot perform %s on closed account %s", operation, accountNumber));
        this.accountNumber = accountNumber;
        this.operation = operation;
    }
    
    /**
     * @return The account number that is closed
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * @return The operation that was attempted on the closed account
     */
    public String getOperation() {
        return operation;
    }
}
