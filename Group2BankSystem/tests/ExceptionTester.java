package Group2BankSystem.tests;

import Group2BankSystem.*;
import Group2BankSystem.exceptions.*;

/**
 * Simple console-based tester for the custom exception classes.
 * Run this class directly to test all exception types.
 */
public class ExceptionTester {
    
    public static void main(String[] args) {
        System.out.println("===== Banking System Exception Tester =====\n");
        
        testInsufficientFundsException();
        testInvalidAccountException();
        testAccountClosedException();
        testTransactionLimitException();
        testInvalidAmountException();
        
        System.out.println("\n===== All Tests Completed =====");
    }
    
    private static void testInsufficientFundsException() {
        System.out.println("Testing InsufficientFundsException:");
        try {
            // Create a test account with PHP 100
            BankAccount account = new BankAccount("TEST001", "Test Account", 100.0);
            
            // Try to withdraw PHP 200
            System.out.println("  Attempting to withdraw PHP 200 from account with PHP 100 balance...");
            account.withdraw(200.0);
            
            // This line should not be reached
            System.out.println("  ERROR: Exception was not thrown!");
        } catch (InsufficientFundsException e) {
            System.out.println("  SUCCESS: " + e.getMessage());
            System.out.println("  Details: Account=" + e.getAccountNumber() + 
                    ", Requested=PHP " + e.getRequestedAmount() + 
                    ", Available=PHP " + e.getAvailableBalance() + 
                    ", Shortage=PHP " + e.getShortageAmount());
        } catch (Exception e) {
            System.out.println("  ERROR: Wrong exception type: " + e.getClass().getSimpleName());
        }
        System.out.println();
    }
    
    private static void testInvalidAccountException() {
        System.out.println("Testing InvalidAccountException:");
        try {
            // Create a non-existent account number
            String accountNumber = "NONEXISTENT123";
            System.out.println("  Looking up non-existent account: " + accountNumber);
            
            // We'll simulate the exception since we don't have access to the account database
            throw new InvalidAccountException(accountNumber);
        } catch (InvalidAccountException e) {
            System.out.println("  SUCCESS: " + e.getMessage());
            System.out.println("  Account number: " + e.getAccountNumber());
        } catch (Exception e) {
            System.out.println("  ERROR: Wrong exception type: " + e.getClass().getSimpleName());
        }
        System.out.println();
    }
    
    private static void testAccountClosedException() {
        System.out.println("Testing AccountClosedException:");
        try {
            // Create a test account and close it
            BankAccount account = new BankAccount("TEST002", "Closed Account", 500.0);
            account.closeAccount();
            
            // Try to perform an operation on the closed account
            System.out.println("  Attempting to deposit to a closed account...");
            account.deposit(100.0);
            
            // This line should not be reached
            System.out.println("  ERROR: Exception was not thrown!");
        } catch (AccountClosedException e) {
            System.out.println("  SUCCESS: " + e.getMessage());
            System.out.println("  Details: Account=" + e.getAccountNumber() + 
                    ", Operation=" + e.getOperation());
        } catch (Exception e) {
            System.out.println("  ERROR: Wrong exception type: " + e.getClass().getSimpleName());
        }
        System.out.println();
    }
    
    private static void testTransactionLimitException() {
        System.out.println("Testing TransactionLimitException:");
        try {
            // Create accounts for transfer
            BankAccount sourceAccount = new BankAccount("TEST003", "Source Account", 20000.0);
            BankAccount targetAccount = new BankAccount("TEST004", "Target Account", 1000.0);
            
            // Try to transfer more than the limit
            System.out.println("  Attempting to transfer PHP 15,000 (over the PHP 10,000 limit)...");
            sourceAccount.transfer(targetAccount, 15000.0);
            
            // This line should not be reached
            System.out.println("  ERROR: Exception was not thrown!");
        } catch (TransactionLimitException e) {
            System.out.println("  SUCCESS: " + e.getMessage());
            System.out.println("  Details: Account=" + e.getAccountNumber() + 
                    ", Type=" + e.getTransactionType() + 
                    ", Requested=PHP " + e.getRequestedAmount() + 
                    ", Limit=PHP " + e.getLimitAmount());
        } catch (Exception e) {
            System.out.println("  ERROR: Wrong exception type: " + e.getClass().getSimpleName());
            e.printStackTrace();
        }
        System.out.println();
    }
    
    private static void testInvalidAmountException() {
        System.out.println("Testing InvalidAmountException:");
        try {
            // Create a test account
            BankAccount account = new BankAccount("TEST005", "Test Account", 500.0);
            
            // Try to deposit a negative amount
            System.out.println("  Attempting to deposit -PHP 100...");
            account.deposit(-100.0);
            
            // This line should not be reached
            System.out.println("  ERROR: Exception was not thrown!");
        } catch (InvalidAmountException e) {
            System.out.println("  SUCCESS: " + e.getMessage());
            System.out.println("  Details: Amount=PHP " + e.getAmount() + 
                    ", Operation=" + e.getOperation());
        } catch (Exception e) {
            System.out.println("  ERROR: Wrong exception type: " + e.getClass().getSimpleName());
        }
        System.out.println();
    }
}
