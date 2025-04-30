package Group2BankSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchAccountPanel extends JPanel {
    private Group2BankSystem.MainFrame frame;
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel resultTableModel;

    public SearchAccountPanel(Group2BankSystem.MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Search Accounts", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new SearchAction());
        searchPanel.add(new JLabel("Enter Account Number or Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.CENTER);

        String[] columnNames = {"Account Number", "Account Holder", "Balance", "Account Type", "Status"};
        resultTableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(resultTableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = searchField.getText().trim();
            resultTableModel.setRowCount(0);

            List<BankAccount> accounts = AccountManager.getAccounts();
            for (BankAccount account : accounts) {
                if (account.getAccountNumber().equalsIgnoreCase(query) ||
                        account.getAccountHolderName().equalsIgnoreCase(query)) {
                    Object[] row = {
                            account.getAccountNumber(),
                            account.getAccountHolderName(),
                            String.format("%.2f", account.getBalance()),
                            account.getAccountType(),
                            account.isActive() ? "Active" : "Closed"
                    };
                    resultTableModel.addRow(row);
                }
            }

            if (resultTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(SearchAccountPanel.this, "No accounts found.");
            }
        }
    }
}