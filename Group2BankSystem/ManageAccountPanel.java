package Group2BankSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageAccountPanel extends JPanel {
    private Group2BankSystem.MainFrame frame;

    public ManageAccountPanel(Group2BankSystem.MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Accounts", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // 2x2 Grid of buttons
        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton bankButton = new JButton("Bank");
        JButton checkingButton = new JButton("Checking");
        JButton creditButton = new JButton("Credit");
        JButton investmentButton = new JButton("Investment");

        buttonGrid.add(bankButton);
        buttonGrid.add(checkingButton);
        buttonGrid.add(creditButton);
        buttonGrid.add(investmentButton);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonGrid);
        add(centerWrapper, BorderLayout.CENTER);

        bankButton.addActionListener(e -> {
            List<BankAccount> bankAccounts = AccountManager.getAccounts(); // or filter only "Bank" type if needed
            BankAccountPanel panel = new BankAccountPanel(bankAccounts);
            panel.setVisible(true);
        });

    }
}
