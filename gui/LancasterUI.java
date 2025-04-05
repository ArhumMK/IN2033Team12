package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LancasterUI extends JFrame {

    private JPanel sidebarPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LancasterUI() {
        // Set up the main frame
        setTitle("Cinema Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Initialize the sidebar
        setupSidebar();

        // Initialize the main content area with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setupMainPanels();

        // Add components to the frame
        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Top bar with user info, date, and icon
        setupTopBar();

        // Set the frame to be visible
        setVisible(true);
    }

    private void setupSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(152, 251, 152)); // Light gray sidebar
        sidebarPanel.setPreferredSize(new Dimension(150, getHeight()));

        sidebarPanel.add(Box.createVerticalStrut(20));

        // Sidebar buttons for each service
        String[] services = { "Show", "Screening", "Film", "Meeting", "Client", "Invoice",
                "Group Sale", "Group", "FoL", "Held/Seats", "Ticket Sales", "Film Orders" };
        for (String service : services) {
            JButton button = new JButton(service);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(Color.BLUE); // Blue button background
            button.setForeground(Color.WHITE); // White font color
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setMaximumSize(new Dimension(120, 30));
            button.addActionListener(e -> cardLayout.show(mainPanel, service));

            // Add hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(50, 50, 50)); // Darker shade on hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.BLUE); // Revert to black
                }
            });

            sidebarPanel.add(button);
            sidebarPanel.add(Box.createVerticalStrut(10));
        }
    }

    private void setupTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(245, 245, 245)); // Light gray background
        topBar.setPreferredSize(new Dimension(getWidth(), 50));

        // Icon on the top left (above the sidebar)
        JLabel iconLabel = new JLabel();
        try {
            // Placeholder: Load a small icon (replace with actual Lancaster Great Hall
            // icon)
            // For now, we'll use a default Java icon as a placeholder
            ImageIcon icon = new ImageIcon(
                    getClass().getResource(
                            "D:\\Courseworks BSc Computer Science\\IN2033Team12\\resources\\lancasters-music-hall-high-resolution-logo.png"));
            // Scale the icon to 30x30 pixels
            Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            // If the icon fails to load, display a placeholder text
            iconLabel.setText("Icon");
            iconLabel.setForeground(Color.RED);
        }
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Padding
        topBar.add(iconLabel, BorderLayout.WEST);

        // User info on the top right
        JLabel userLabel = new JLabel("Logged in as: Admin");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        topBar.add(userLabel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    private void setupMainPanels() {
        // Show Panel
        JPanel showPanel = new JPanel(new BorderLayout());
        showPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Form for Show
        JPanel showFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        showFormPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        JTextField showDateField = new JTextField(15);
        showFormPanel.add(showDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        showFormPanel.add(new JLabel("Start Time:"), gbc);
        gbc.gridx = 1;
        JTextField showTimeField = new JTextField(15);
        showFormPanel.add(showTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        showFormPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField showNameField = new JTextField(15);
        showFormPanel.add(showNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        showFormPanel.add(new JLabel("Venue:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> showVenueCombo = new JComboBox<>(new String[] { "Theater 1", "Theater 2", "Theater 3" });
        showFormPanel.add(showVenueCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        showFormPanel.add(new JLabel("Price/Discount:"), gbc);
        gbc.gridx = 1;
        JTextField showPriceField = new JTextField(15);
        showFormPanel.add(showPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        showFormPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextArea showDescArea = new JTextArea(3, 15);
        showFormPanel.add(new JScrollPane(showDescArea), gbc);

        // Buttons for Show
        JPanel showButtonPanel = new JPanel(new FlowLayout());
        JButton addShowButton = new JButton("Add Show");
        addShowButton.setBackground(new Color(0, 123, 255));
        addShowButton.setForeground(Color.WHITE);
        showButtonPanel.add(addShowButton);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        showFormPanel.add(showButtonPanel, gbc);

        showPanel.add(showFormPanel, BorderLayout.NORTH);

        // Table for Show
        String[] showColumns = { "Date", "Start Time", "Name", "Venue", "Price", "Actions" };
        DefaultTableModel showTableModel = new DefaultTableModel(showColumns, 0);
        JTable showTable = new JTable(showTableModel);
        JScrollPane showScrollPane = new JScrollPane(showTable);
        showPanel.add(showScrollPane, BorderLayout.CENTER);

        // Add action to the button
        addShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] row = { showDateField.getText(), showTimeField.getText(), showNameField.getText(),
                        (String) showVenueCombo.getSelectedItem(), showPriceField.getText(), "Edit/Delete" };
                showTableModel.addRow(row);
                // Clear fields after adding
                showDateField.setText("");
                showTimeField.setText("");
                showNameField.setText("");
                showPriceField.setText("");
                showDescArea.setText("");
            }
        });

        mainPanel.add(showPanel, "Show");

        // Client Panel
        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.setBackground(new Color(245, 245, 245));

        // Form for Client
        JPanel clientFormPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        clientFormPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField clientNameField = new JTextField(15);
        clientFormPanel.add(clientNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        clientFormPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        JTextArea clientAddressArea = new JTextArea(3, 15);
        clientFormPanel.add(new JScrollPane(clientAddressArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        clientFormPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField clientEmailField = new JTextField(15);
        clientFormPanel.add(clientEmailField, gbc);

        // Buttons for Client
        JPanel clientButtonPanel = new JPanel(new FlowLayout());
        JButton addClientButton = new JButton("Add Client");
        addClientButton.setBackground(new Color(0, 123, 255));
        addClientButton.setForeground(Color.WHITE);
        clientButtonPanel.add(addClientButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        clientFormPanel.add(clientButtonPanel, gbc);

        clientPanel.add(clientFormPanel, BorderLayout.NORTH);

        // Table for Client
        String[] clientColumns = { "Name", "Address", "Email", "Actions" };
        DefaultTableModel clientTableModel = new DefaultTableModel(clientColumns, 0);
        JTable clientTable = new JTable(clientTableModel);
        JScrollPane clientScrollPane = new JScrollPane(clientTable);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);

        // Add action to the button
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] row = { clientNameField.getText(), clientAddressArea.getText(), clientEmailField.getText(),
                        "Edit/Delete" };
                clientTableModel.addRow(row);
                // Clear fields after adding
                clientNameField.setText("");
                clientAddressArea.setText("");
                clientEmailField.setText("");
            }
        });

        mainPanel.add(clientPanel, "Client");

        // Placeholder panels for other services
        for (String service : new String[] { "Screening", "Film", "Meeting", "Invoice", "Group Sale",
                "Group", "FoL", "Held/Seats", "Ticket Sales", "Film Orders" }) {
            JPanel placeholderPanel = new JPanel();
            placeholderPanel.add(new JLabel("Panel for " + service));
            mainPanel.add(placeholderPanel, service);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LancasterUI());
    }
}