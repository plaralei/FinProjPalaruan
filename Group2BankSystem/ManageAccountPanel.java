package Group2BankSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageAccountPanel extends JPanel {
    private Group2BankSystem.MainFrame frame;
    private JTable accountTable;
    private DefaultTableModel tableModel;

    public ManageAccountPanel(Group2BankSystem.MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Accounts", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columnNames = {"Account Number", "Account Holder", "Balance", "Account Type", "Status", "Creation Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        accountTable = new JTable(tableModel);
        loadAccounts();

        JScrollPane scrollPane = new JScrollPane(accountTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editBtn = new JButton("Edit Account");
        JButton deleteBtn = new JButton("Delete Account");
        JButton backBtn = new JButton("Back");

        editBtn.addActionListener(new EditAccountAction());
        deleteBtn.addActionListener(new DeleteAccountAction());
        backBtn.addActionListener(e -> frame.show(Group2BankSystem.MainFrame.MENU));

        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadAccounts() {
        tableModel.setRowCount(0); // Clear existing rows
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

    private class EditAccountAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ManageAccountPanel.this, "Please select an account to edit.");
                return;
            }

            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            BankAccount account = AccountManager.getAccountByNumber(accountNumber);
            if (account != null) {

                EditAccountDIalog dialog = new EditAccountDIalog(account);
                dialog.setVisible(true);
                if (dialog.isUpdated()) {
                    loadAccounts();
                }
            }
        }
    }

    private class DeleteAccountAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ManageAccountPanel.this, "Please select an account to delete.");
                return;
            }

            String accountNumber = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(ManageAccountPanel.this,
                    "Are you sure you want to delete this account?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (AccountManager.deleteAccount(accountNumber)) {
                    JOptionPane.showMessageDialog(ManageAccountPanel.this, "Account deleted successfully.");
                    loadAccounts();
                } else {
                    JOptionPane.showMessageDialog(ManageAccountPanel.this, "Failed to delete account.");
                }
            }
        }
    }
}