package Group2BankSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BankAccountPanel extends JFrame {

    private JTable accountTable;
    private DefaultTableModel tableModel;

    public BankAccountPanel(List<BankAccount> accounts) {
        setTitle("Bank Accounts");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table column headers
        String[] columnNames = {
                "Account Number", "Account Holder", "Balance",
                "Account Type", "Status", "Date Created"
        };

        // Create and assign table model
        tableModel = new DefaultTableModel(columnNames, 0);
        accountTable = new JTable(tableModel);

        // Load data into the table
        for (BankAccount account : accounts) {
            Object[] row = {
                    account.getAccountNumber(),
                    account.getAccountHolderName(),
                    String.format("%.2f", account.getBalance()),
                    account.getAccountType(),
                    account.isActive() ? "Active" : "Closed",
                    account.getDateCreated()
            };
            tableModel.addRow(row);
        }

        // Add table to scroll pane and frame
        JScrollPane scrollPane = new JScrollPane(accountTable);
        add(scrollPane, BorderLayout.CENTER);

        // Mouse listener to show dropdown menu (popup menu)
        accountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int row = accountTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    accountTable.setRowSelectionInterval(row, row);

                    // Create popup menu
                    JPopupMenu popup = new JPopupMenu();
                    String[] actions = {
                            "Edit Account", "Deposit", "Withdrawal",
                            "Transfer Money", "Inquire Balance", "Close Account"
                    };

                    for (String action : actions) {
                        JMenuItem item = new JMenuItem(action);
                        item.addActionListener(e -> handleAction(action, row));
                        popup.add(item);
                    }

                    popup.show(accountTable, evt.getX(), evt.getY());
                }
            }
        });

        // Back button at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleAction(String action, int row) {
        String accountNumber = (String) tableModel.getValueAt(row, 0);
        BankAccount account = AccountManager.getAccountByNumber(accountNumber);

        if (account == null) {
            JOptionPane.showMessageDialog(this, "Account not found.");
            return;
        }

        switch (action) {
            case "Edit Account":
                EditAccountDialog editDialog = new EditAccountDialog(account);
                editDialog.setVisible(true);
                if (editDialog.isUpdated()) {
                    loadAccounts();
                }
                break;
            case "Deposit":
                JOptionPane.showMessageDialog(this, "Deposit to " + account.getAccountHolderName());
                break;
            case "Withdrawal":
                JOptionPane.showMessageDialog(this, "Withdraw from " + account.getAccountHolderName());
                break;
            case "Transfer Money":
                JOptionPane.showMessageDialog(this, "Transfer from " + account.getAccountHolderName());
                break;
            case "Inquire Balance":
                JOptionPane.showMessageDialog(this, "Balance: " + account.getBalance());
                break;
            case "Close Account":
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to close this account?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    account.setActive(false);
                    JOptionPane.showMessageDialog(this, "Account closed.");
                    loadAccounts();
                }
                break;
        }
    }

    private void loadAccounts() {
        tableModel.setRowCount(0); // Clear table
        List<BankAccount> accounts = AccountManager.getAccounts();
        for (BankAccount account : accounts) {
            Object[] row = {
                    account.getAccountNumber(),
                    account.getAccountHolderName(),
                    String.format("%.2f", account.getBalance()),
                    account.getAccountType(),
                    account.isActive() ? "Active" : "Closed",
                    account.getDateCreated()
            };
            tableModel.addRow(row);
        }
    }
}
