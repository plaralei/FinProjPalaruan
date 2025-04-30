package Group2BankSystem;

import java.io.Serializable;

public class Account extends BankAccount implements Serializable {
    public Account(String accountNumber, String name, double deposit, String accountType) {
        super(accountNumber, name, deposit);
        this.accountType = accountType;
    }

}