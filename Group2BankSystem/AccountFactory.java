package Group2BankSystem;

public class AccountFactory {
    public static BankAccount createAccount(String accountNumber, String accountHolderName,
                                            double initialDeposit, String accountType) {
        switch (accountType) {
            case "Checking Account":
                return new CheckingAccount(accountNumber, accountHolderName, initialDeposit);
            case "Investment Account":
                return new InvestmentAccount(accountNumber, accountHolderName, initialDeposit);
            case "Credit Card Account":
                return new CreditCardAccount(accountNumber, accountHolderName, initialDeposit);
            default:
                return new BankAccount(accountNumber, accountHolderName, initialDeposit);
        }
    }

    public static BankAccount convertAccount(BankAccount oldAccount, String newType) {
        BankAccount newAccount;

        switch (newType) {
            case "Checking Account":
                newAccount = new CheckingAccount(oldAccount.getAccountNumber(),
                        oldAccount.getAccountHolderName(),
                        oldAccount.getBalance());
                break;
            case "Investment Account":
                newAccount = new InvestmentAccount(oldAccount.getAccountNumber(),
                        oldAccount.getAccountHolderName(),
                        oldAccount.getBalance());
                break;
            case "Credit Card Account":
                newAccount = new CreditCardAccount(oldAccount.getAccountNumber(),
                        oldAccount.getAccountHolderName(),
                        oldAccount.getBalance());
                break;
            default:
                newAccount = new BankAccount(oldAccount.getAccountNumber(),
                        oldAccount.getAccountHolderName(),
                        oldAccount.getBalance());
                break;
        }

        newAccount.setActive(oldAccount.isActive());
        return newAccount;
    }
}