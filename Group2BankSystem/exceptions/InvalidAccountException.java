package Group2BankSystem.exceptions;

/**
 * Exception thrown when operations are attempted on an invalid or non-existent account.
 * This exception is used when account lookup fails or an invalid account number is provided.
 */
public class InvalidAccountException extends RuntimeException {

    private final String accountNumber;
    
    /**
     * Constructs an InvalidAccountException with a specific account number
     *
     * @param accountNumber The account number that was found to be invalid
     */
    public InvalidAccountException(String accountNumber) {
        super("Invalid or non-existent account: " + accountNumber);
        this.accountNumber = accountNumber;
    }
    
    /**
     * Constructs an InvalidAccountException with a specific account number and custom message
     *
     * @param accountNumber The account number that was found to be invalid
     * @param message Additional details about why the account is invalid
     */
    public InvalidAccountException(String accountNumber, String message) {
        super(message);
        this.accountNumber = accountNumber;
    }
    
    /**
     * @return The account number that was found to be invalid
     */
    public String getAccountNumber() {
        return accountNumber;
    }
}
