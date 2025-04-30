package Group2BankSystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Group2BankSystem.TransactionManager;
import java.io.IOException;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateReportPanel extends JPanel {
    private Group2BankSystem.MainFrame frame;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public GenerateReportPanel(Group2BankSystem.MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Generate Reports", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Dropdown for report types
        String[] reportOptions = {
            "Daily Transactions",
            "Summary of Transactions",
            "Per Account",
            "On-Demand Reports"
        };
        JComboBox<String> reportSelector = new JComboBox<>(reportOptions);
        reportSelector.addActionListener(e -> switchPanel((String) reportSelector.getSelectedItem()));
        add(reportSelector, BorderLayout.WEST);

        // Card layout for switching views
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(createDailyTransactionPanel(), "Daily Transactions");
        contentPanel.add(createSummaryPanel(), "Summary of Transactions");
        contentPanel.add(createPerAccountPanel(), "Per Account");
        contentPanel.add(createOnDemandPanel(), "On-Demand Reports");
        add(contentPanel, BorderLayout.CENTER);

        // Show default view
        cardLayout.show(contentPanel, "Daily Transactions");
    }

    private void switchPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }

    private JPanel createDailyTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        JTextField dateField = new JTextField("2025-04-30", 10);
        JButton loadButton = new JButton("Load");
        JButton refreshButton = new JButton("Refresh");
        topPanel.add(dateField);
        topPanel.add(loadButton);
        topPanel.add(refreshButton);

        String[] columnNames = {"ID", "Date", "Account", "Type", "Amount", "Description", "Edit"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        loadButton.addActionListener(e -> {
            model.setRowCount(0);
            String dateStr = dateField.getText().trim();
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date targetDate = df.parse(dateStr);
                List<Transaction> transactions = TransactionManager.getTransactions();
                int count = 0;
                for (Transaction t : transactions) {
                    if (df.format(t.date).equals(df.format(targetDate))) {
                        model.addRow(new Object[]{t.transactionId, df.format(t.date), t.accountNumber,
                                t.type, t.amount, t.description, "Edit"});
                        count++;
                    }
                }
                if (count == 0) {
                    JOptionPane.showMessageDialog(null, "No transactions found for selected date.");
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-mm-dd");
            }
        });

        refreshButton.addActionListener(e -> loadButton.doClick());

        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), model, table));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout());
        JTextField fromDateField = new JTextField("yyyy-MM-dd", 10);
        JTextField toDateField = new JTextField("yyyy-MM-dd", 10);
        JButton generateButton = new JButton("Generate Summary");
        controlPanel.add(new JLabel("From:"));
        controlPanel.add(fromDateField);
        controlPanel.add(new JLabel("To:"));
        controlPanel.add(toDateField);
        controlPanel.add(generateButton);

        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        generateButton.addActionListener(e -> {
            resultArea.setText("");
            List<Transaction> transactions = TransactionManager.getTransactions();
            java.util.Map<String, Double> summary = new java.util.HashMap<>();
            String fromText = fromDateField.getText().trim();
            String toText = toDateField.getText().trim();

            for (Transaction t : transactions) {
                boolean include = true;
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    if (!fromText.equals("") && !fromText.equals("yyyy-MM-dd")) {
                        java.util.Date fromDate = sdf.parse(fromText);
                        if (t.date.before(fromDate)) include = false;
                    }
                    if (!toText.equals("") && !toText.equals("yyyy-MM-dd")) {
                        java.util.Date toDate = sdf.parse(toText);
                        if (t.date.after(toDate)) include = false;
                    }
                } catch (Exception ex) {
                    resultArea.setText("Invalid date format. Use yyyy-MM-dd.");
                    return;
                }

                if (include) {
                    summary.put(t.type, summary.getOrDefault(t.type, 0.0) + t.amount);
                }
            }

            for (String type : summary.keySet()) {
                resultArea.append(type + ": " + summary.get(type) + " ");
            }
            if (summary.isEmpty()) {
                resultArea.setText("No transactions match the criteria.");
            }
        });

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPerAccountPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout());
        JTextField accountField = new JTextField(10);
        JButton generateButton = new JButton("Generate Account Summary");
        controlPanel.add(new JLabel("Account Number:"));
        controlPanel.add(accountField);
        controlPanel.add(generateButton);

        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        generateButton.addActionListener(e -> {
            resultArea.setText("");
            String inputAccount = accountField.getText().trim();
            if (inputAccount.isEmpty()) {
                resultArea.setText("Please enter an account number.");
                return;
            }

            List<Transaction> transactions = TransactionManager.getTransactions();
            java.util.Map<String, Double> summary = new java.util.HashMap<>();

            for (Transaction t : transactions) {
                if (t.accountNumber.equals(inputAccount)) {
                    summary.put(t.type, summary.getOrDefault(t.type, 0.0) + t.amount);
                }
            }

            for (String type : summary.keySet()) {
                resultArea.append(type + ": " + summary.get(type) + " ");
            }
            if (summary.isEmpty()) {
                resultArea.setText("No transactions found for this account.");
            }
        });

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createOnDemandPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Keyword:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JTextArea resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        searchButton.addActionListener(e -> {
            resultArea.setText("");
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                resultArea.setText("Please enter a search keyword.");
                return;
            }

            List<Transaction> transactions = TransactionManager.getTransactions();
            int count = 0;
            for (Transaction t : transactions) {
                String combined = (t.transactionId + " " + t.date + " " + t.accountNumber + " "
                        + t.type + " " + t.amount + " " + t.description).toLowerCase();
                if (combined.contains(keyword)) {
                    resultArea.append("[" + t.transactionId + "] " + t.date + " | " + t.accountNumber +
                            " | " + t.type + " | $" + t.amount + " | " + t.description + " ");
                    count++;
                }
            }
            if (count == 0) {
                resultArea.setText("No matching transactions found.");
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}