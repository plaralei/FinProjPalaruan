package Group2BankSystem;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GenerateReportPanel extends JPanel {
    private Group2BankSystem.MainFrame frame;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public GenerateReportPanel(Group2BankSystem.MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Generate Reports", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Create a table to display transactions
        String[] columnNames = {"Transaction ID", "Date", "Account Number", "Type", "Amount", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        loadTransactions();

        // Add a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel();
        JButton backBtn = new JButton("Back");
        JButton generateReportBtn = new JButton("Generate Report");

        backBtn.addActionListener(e -> frame.showCard(Group2BankSystem.MainFrame.MENU));
        generateReportBtn.addActionListener(new GenerateReportAction());

        buttonPanel.add(generateReportBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTransactions() {
        tableModel.setRowCount(0); // Clear existing rows
        List<Transaction> transactions = TransactionManager.getTransactions();
        for (Transaction transaction : transactions) {
            Object[] row = {
                    transaction.getTransactionId(),
                    transaction.getDate(),
                    transaction.getAccountNumber(),
                    transaction.getType(),
                    String.format("%.2f", transaction.getAmount()),
                    transaction.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    private class GenerateReportAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Logic to generate reports can be added here
            JOptionPane.showMessageDialog(GenerateReportPanel.this, "Report generated successfully!");
        }
    }

    // Transaction class to represent a transaction
    public static class Transaction implements Serializable {
        private String transactionId;
        private Date date;
        private String accountNumber;
        private String type; // e.g., Deposit, Withdrawal, Transfer
        private double amount;
        private String description;

        public Transaction(String transactionId, Date date, String accountNumber, String type, double amount, String description) {
            this.transactionId = transactionId;
            this.date = date;
            this.accountNumber = accountNumber;
            this.type = type;
            this.amount = amount;
            this.description = description;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public Date getDate() {
            return date;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }
}