package Group2BankSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAccountDialog extends JDialog {
    private BankAccount account;
    private boolean updated = false;

    public EditAccountDialog(BankAccount account) {
        this.account = account; // No casting needed
        setTitle("Edit Account");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Account Holder Name:"));
        JTextField nameField = new JTextField(account.getAccountHolderName());
        add(nameField);

        add(new JLabel("Balance:"));
        JTextField balanceField = new JTextField(String.valueOf(account.getBalance()));
        add(balanceField);

        add(new JLabel("Account Type:"));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{
                "Bank Account", "Checking Account", "Investment Account", "Credit Card Account"
        });
        typeBox.setSelectedItem(account.getAccountType());
        add(typeBox);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account.setAccountHolderName(nameField.getText());
                account.setActive(true); // Assuming we want to reactivate the account
                account.deposit(Double.parseDouble(balanceField.getText())); // Update balance
                AccountManager.updateAccount(account);
                updated = true;
                dispose();
            }
        });
        add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        add(cancelBtn);
    }

    public EditAccountDialog(MainFrame mainFrame) {
    }

    public boolean isUpdated() {
        return updated;
    }
}
