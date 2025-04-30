package Group2BankSystem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAccountDialog extends JDialog {
    private BankAccount account;
    private boolean updated = false;

    public EditAccountDialog(MainFrame account) {
        this.account = (BankAccount) account;
        setTitle("Edit Account");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Account Holder Name:"));
        JTextField nameField = new JTextField(((BankAccount) account).getAccountHolderName());
        add(nameField);

        add(new JLabel("Balance:"));
        JTextField balanceField = new JTextField(String.valueOf(((BankAccount) account).getBalance()));
        add(balanceField);

        add(new JLabel("Account Type:"));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{
                "Bank Account", "Checking Account", "Investment Account", "Credit Card Account"
        });
        typeBox.setSelectedItem(((BankAccount) account).getAccountType());
        add(typeBox);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((BankAccount) account).setAccountHolderName(nameField.getText());
                ((BankAccount) account).setActive(true); // Assuming we want to reactivate the account
                ((BankAccount) account).deposit(Double.parseDouble(balanceField.getText())); // Update balance
                AccountManager.updateAccount((BankAccount) account);
                updated = true;
                dispose();
            }
        });
        add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        add(cancelBtn);
    }

    public boolean isUpdated() {
        return updated;
    }
}