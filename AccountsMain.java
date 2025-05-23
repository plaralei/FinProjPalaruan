import exceptions.*;
import java.util.Scanner;

public class AccountsMain {
    private static BankAccounts[] bankAccounts = new BankAccounts[5];
    private static InvestmentAccount[] investmentAccounts = new InvestmentAccount[5];
    private static CheckingAccount[] checkingAccounts = new CheckingAccount[5];
    private static CreditCardAccount[] creditCardAccounts = new CreditCardAccount[5];
    private static int bankAccountCount = 0;
    private static int investmentAccountCount = 0;
    private static int checkingAccountCount = 0;
    private static int creditCardAccountCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        // Testingg
        do {
            System.out.println("\n--- Bank Account Management System ---");
            System.out.println("1. Create Bank Account");
            System.out.println("2. Create Investment Account");
            System.out.println("3. Create Checking Account");
            System.out.println("4. Create Credit Card Account");
            System.out.println("5. Balance Inquiry");
            System.out.println("6. Deposit Transaction");
            System.out.println("7. Withdrawal Transaction");
            System.out.println("8. Transfer Money");
            System.out.println("9. Display Account Information");
            System.out.println("10. Close Account");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createBankAccount(scanner);
                    break;
                case 2:
                    createInvestmentAccount(scanner);
                    break;
                case 3:
                    createCheckingAccount(scanner);
                    break;
                case 4:
                    createCreditCardAccount(scanner);
                    break;
                case 5:
                    balanceInquiry(scanner);
                    break;
                case 6:
                    depositTransaction(scanner);
                    break;
                case 7:
                    withdrawalTransaction(scanner);
                    break;
                case 8:
                    transferMoney(scanner);
                    break;
                case 9:
                    displayAccountInformation(scanner);
                    break;
                case 10:
                    closeAccount(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the system. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void createBankAccount(Scanner scanner) {
        if (bankAccountCount >= bankAccounts.length) {
            System.out.println("Bank account limit reached.");
            return;
        }
        System.out.print("Enter Account Number (9 digits): ");
        int accountNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Account Name: ");
        String accountName = scanner.nextLine();
        bankAccounts[bankAccountCount++] = new BankAccounts(accountNo, accountName);
        System.out.println("Bank Account created successfully.");
    }

    private static void createInvestmentAccount(Scanner scanner) {
        if (investmentAccountCount >= investmentAccounts.length) {
            System.out.println("Investment account limit reached.");
            return;
        }
        System.out.print("Enter Account Number (9 digits): ");
        int accountNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Account Name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter Minimum Balance: ");
        double minimumBalance = scanner.nextDouble();
        System.out.print("Enter Interest Rate (e.g., 0.10 for 10%): ");
        double interest = scanner.nextDouble();
        investmentAccounts[investmentAccountCount++] = new InvestmentAccount(accountNo, accountName, minimumBalance, interest);
        System.out.println("Investment Account created successfully.");
    }

    private static void createCheckingAccount(Scanner scanner) {
        if (checkingAccountCount >= checkingAccounts.length) {
            System.out.println("Checking account limit reached.");
            return;
        }
        System.out.print("Enter Account Number (9 digits): ");
        int accountNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Account Name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter Minimum Balance: ");
        double minimumBalance = scanner.nextDouble();
        checkingAccounts[checkingAccountCount++] = new CheckingAccount(accountNo, accountName, minimumBalance);
        System.out.println("Checking Account created successfully.");
    }

    private static void createCreditCardAccount(Scanner scanner) {
        if (creditCardAccountCount >= creditCardAccounts.length) {
            System.out.println("Credit card account limit reached.");
            return;
        }
        System.out.print("Enter Account Number (9 digits): ");
        int accountNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Account Name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter Credit Limit: ");
        double creditLimit = scanner.nextDouble();
        creditCardAccounts[creditCardAccountCount++] = new CreditCardAccount(accountNo, accountName, creditLimit);
        System.out.println("Credit Card Account created successfully.");
    }

    private static void balanceInquiry(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        int accountNo = scanner.nextInt();
        BankAccounts account = findAccount(accountNo);
        if (account != null) {
            System.out.println("Current Balance: " + account.inquireBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void depositTransaction(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        int accountNo = scanner.nextInt();
        BankAccounts account = findAccount(accountNo);
        if (account != null) {
            System.out.print("Enter Deposit Amount: ");
            double amount = scanner.nextDouble();
            try {
                account.deposit(amount);
                System.out.println("Deposit successful. New Balance: " + account.inquireBalance());
            } catch (AccountClosedException | InvalidAmountException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawalTransaction(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        int accountNo = scanner.nextInt();
        BankAccounts account = findAccount(accountNo);
        if (account != null) {
            System.out.print("Enter Withdrawal Amount: ");
            double amount = scanner.nextDouble();
            try {
                account.withdraw(amount);
                System.out.println("Withdrawal successful. New Balance: " + account.inquireBalance());
            } catch (InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Shortage amount: PHP " + e.getShortageAmount());
            } catch (AccountClosedException | InvalidAmountException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transferMoney(Scanner scanner) {
        System.out.print("Enter Your Account Number: ");
        int fromAccountNo = scanner.nextInt();
        BankAccounts fromAccount = findAccount(fromAccountNo);
        if (fromAccount != null) {
            System.out.print("Enter Target Account Number: ");
            int toAccountNo = scanner.nextInt();
            BankAccounts toAccount = findAccount(toAccountNo);
            if (toAccount != null) {
                System.out.print("Enter Transfer Amount: ");
                double amount = scanner.nextDouble();
                try {
                    fromAccount.transferMoney(toAccountNo, amount);
                    System.out.println("Transfer successful. New Balance: " + fromAccount.inquireBalance());
                } catch (InsufficientFundsException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Shortage amount: PHP " + e.getShortageAmount());
                } catch (TransactionLimitException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Maximum allowed transfer: PHP " + e.getLimitAmount());
                } catch (AccountClosedException | InvalidAmountException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                }
            } else {
                System.out.println("Target account not found.");
            }
        } else {
            System.out.println("Your account not found.");
        }
    }

    private static void displayAccountInformation(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        int accountNo = scanner.nextInt();
        BankAccounts account = findAccount(accountNo);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void closeAccount(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        int accountNo = scanner.nextInt();
        BankAccounts account = findAccount(accountNo);
        if (account != null) {
            try {
                account.closeAccount();
                System.out.println("Account closed successfully.");
            } catch (AccountClosedException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error closing account: " + e.getMessage());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static BankAccounts findAccount(int accountNo) {
        for (int i = 0; i < bankAccountCount; i++) {
            if (bankAccounts[i] != null && bankAccounts[i].getAccountNo() == accountNo) {
                return bankAccounts[i];
            }
        }
        for (int i = 0; i < investmentAccountCount; i++) {
            if (investmentAccounts[i] != null && investmentAccounts[i].getAccountNo() == accountNo) {
                return investmentAccounts[i];
            }
        }
        for (int i = 0; i < checkingAccountCount; i++) {
            if (checkingAccounts[i] != null && checkingAccounts[i].getAccountNo() == accountNo) {
                return checkingAccounts[i];
            }
        }
        for (int i = 0; i < creditCardAccountCount; i++) {
            if (creditCardAccounts[i] != null && creditCardAccounts[i].getAccountNo() == accountNo) {
                return creditCardAccounts[i];
            }
        }
        return null; // Account not found
    }
}
