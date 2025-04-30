package Group2BankSystem;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout layout;
    private JPanel cards;

    public static final String MENU = "Menu";
    public static final String CREATE = "Create";
    public static final String MANAGE = "Manage";
    public static final String SEARCH = "Search";
    public static final String GENERATE = "Generate";

    public MainFrame() {
        setTitle("Palaruan Banking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);

        cards.add(new CreateAccountPanel(this), CREATE);
        cards.add(new ManageAccountPanel(this), MANAGE); // Use a JPanel instead of JDialog
        cards.add(new SearchAccountPanel(this), SEARCH);
        cards.add(new GenerateReportPanel(this), GENERATE);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1)); // Vertical layout
        buttonPanel.setBackground(Color.BLUE);

        JButton createBtn = new JButton("Create Account");
        createBtn.addActionListener(e -> {
            layout.show(cards, CREATE);
        });
        buttonPanel.add(createBtn);

        JButton manageBtn = new JButton("Manage Account");
        manageBtn.addActionListener(e -> {
            layout.show(cards, MANAGE);
        });
        buttonPanel.add(manageBtn);

        JButton searchAccBtn = new JButton("Search Account");
        searchAccBtn.addActionListener(e -> {
            layout.show(cards, SEARCH);
        });
        buttonPanel.add(searchAccBtn);

        JButton generateAccBtn = new JButton("Generate Reports");
        generateAccBtn.addActionListener(e -> {
            layout.show(cards, GENERATE);
        });
        buttonPanel.add(generateAccBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        // Set layout for the main frame
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.WEST); // Add button panel to the left
        add(cards, BorderLayout.CENTER); // Add cards panel to the center

        setVisible(true);
    }

    public void show(String create) {
    }

    public void showCard(String menu) {
    }
}

