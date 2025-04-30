package Group2BankSystem;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(PalaruanBankSystem.MainFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Main Menu");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        add(label, gbc);

        JButton createBtn = new JButton("Create Account");
        createBtn.addActionListener(e -> frame.show(PalaruanBankSystem.MainFrame.CREATE));
        gbc.gridy++;
        add(createBtn, gbc);

        JButton manageBtn = new JButton("Manage Account");
        manageBtn.addActionListener(e -> frame.show(PalaruanBankSystem.MainFrame.MANAGE));
        gbc.gridy++;
        add(manageBtn, gbc);

        JButton searchAccBtn = new JButton("Search Account");
        searchAccBtn.addActionListener(e -> frame.show(PalaruanBankSystem.MainFrame.SEARCH));
        gbc.gridy++;
        add(searchAccBtn, gbc);

        JButton generateAccBtn = new JButton("Generate Reports");
        generateAccBtn.addActionListener(e -> frame.show(PalaruanBankSystem.MainFrame.GENERATE));
        gbc.gridy++;
        add(generateAccBtn, gbc);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        gbc.gridy++;
        add(exitBtn, gbc);
    }
}

