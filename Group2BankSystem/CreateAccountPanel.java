package Group2BankSystem;

import Group2BankSystem.exceptions.*;
import java.awt.*;
import javax.swing.*;

public class CreateAccountPanel extends JPanel {
    protected static final String DATA_FILE = "accounts.dat";
    private static int nextAccountNumber = 1;

    public CreateAccountPanel(Group2BankSystem.MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Create Account", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField depositField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{
                "Bank Account", "Checking Account", "Investment Account", "Credit Card Account"
        });

        form.add(new JLabel("Account Holder Name:"));
        form.add(nameField);
        form.add(new JLabel("Initial Deposit:"));
        form.add(depositField);
        form.add(new JLabel("Account Type:"));
        form.add(typeBox);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton backBtn = new JButton("Back");
        JButton createBtn = new JButton("Create");

        backBtn.addActionListener(e -> frame.showCard(Group2BankSystem.MainFrame.MENU));
        createBtn.addActionListener(e -> {
            String name = nameField.getText();
            String depositStr = depositField.getText();
            String type = (String) typeBox.getSelectedItem();

            try {
                // Validate input
                if (name == null || name.trim().isEmpty()) {
                    throw new IllegalArgumentException("Account holder name is required");
                }

                double depositAmount;
                try {
                    depositAmount = Double.parseDouble(depositStr);
                } catch (NumberFormatException ex) {
                    throw new InvalidAmountException(0, "initial deposit", "Invalid deposit amount format");
                }

                if (depositAmount <= 0) {
                    throw new InvalidAmountException(depositAmount, "initial deposit", 
                        "Initial deposit amount must be greater than zero");
                }

                // Create the account using our factory
                BankAccount newAccount = AccountFactory.createAccount(
                    getFormattedAccountNumber(nextAccountNumber++), name, depositAmount, type);

                saveAccount(newAccount);

                JOptionPane.showMessageDialog(this,
                        "Account created!\nName: " + name + "\nType: " + type + 
                        "\nDeposit: " + depositStr + "\nAccount Number: " + newAccount.getAccountNumber());
                
                // Clear the form
                nameField.setText("");
                depositField.setText("");
                
            } catch (InvalidAmountException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Invalid Amount Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating account: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttons.add(backBtn);
        buttons.add(createBtn);
        add(buttons, BorderLayout.SOUTH);
    }

    private String getFormattedAccountNumber(int accountNumber) {
        return String.format("2250%04d", accountNumber);
    }

    private void saveAccount(BankAccount account) {
        AccountManager.addAccount(account);
    }
}