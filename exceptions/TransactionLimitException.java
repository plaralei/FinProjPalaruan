package exceptions;

public class TransactionLimitException extends Exception {
    public TransactionLimitException (String message) {
        super (message);
    }
}
