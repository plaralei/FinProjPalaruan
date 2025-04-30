package Group2BankSystem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

            BankAccount newAccount = AccountFactory.createAccount(getFormattedAccountNumber(nextAccountNumber++), name, Double.parseDouble(depositStr), type);

            saveAccount(newAccount);

            JOptionPane.showMessageDialog(this,
                    "Account created!\nName: " + name + "\nType: " + type + "\nDeposit: " + depositStr + "\nAccount Number: " + newAccount.getAccountNumber());
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