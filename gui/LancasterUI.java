package gui;

import InternalAPI.BoxOfficeInterface.BoxOfficeData;

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
        sidebarPanel.setBackground(Color.decode("#2FCC40")); // Light gray sidebar
        sidebarPanel.setPreferredSize(new Dimension(100, getHeight()));

        sidebarPanel.add(Box.createVerticalStrut(20));

        // Sidebar buttons for each service
        String[] services = { "Home", "Show", "Screening", "Film", "Meeting", "Client", "Invoice",
                "Group Sale", "Group", "FoL", "Held/Seats", "Ticket Sales", "Film Orders" };
        for (String service : services) {
            final String targetPanel = service;
            JButton button = new JButton(service);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(Color.decode("#122023"));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setMaximumSize(new Dimension(120, 30));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            // Smooth hover effect
            Color normalColor = Color.decode("#122023");
            Color hoverColor = new Color(50, 50, 50);

            Timer[] hoverTimer = new Timer[1]; // allow reassignment inside inner class
            final float[] progress = { 0f };

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (hoverTimer[0] != null && hoverTimer[0].isRunning()) {
                        hoverTimer[0].stop();
                    }
                    hoverTimer[0] = new Timer(15, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (progress[0] < 1f) {
                                progress[0] += 0.1f;
                                button.setBackground(blend(normalColor, hoverColor, progress[0]));
                            } else {
                                hoverTimer[0].stop();
                            }
                        }
                    });
                    hoverTimer[0].start();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (hoverTimer[0] != null && hoverTimer[0].isRunning()) {
                        hoverTimer[0].stop();
                    }
                    hoverTimer[0] = new Timer(15, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (progress[0] > 0f) {
                                progress[0] -= 0.1f;
                                button.setBackground(blend(normalColor, hoverColor, progress[0]));
                            } else {
                                hoverTimer[0].stop();
                            }
                        }
                    });
                    hoverTimer[0].start();
                }
            });

            button.addActionListener(e -> cardLayout.show(mainPanel, targetPanel));

            sidebarPanel.add(button);
            sidebarPanel.add(Box.createVerticalStrut(10));
        }
    }

    private void setupTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.decode("#122023")); // Light gray background
        topBar.setPreferredSize(new Dimension(getWidth(), 50));

        // Icon on the top left (above the sidebar)
        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(100, 100));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, left, bottom, right padding

        // Ensure the label has a defined size
        try {
            // Load the logo from the file path
            ImageIcon icon = new ImageIcon(
                    "..\\resources\\lancasters-music-hall-high-resolution-logo.png");

            if (icon.getIconWidth() == -1) { // Check if the icon failed to load
                throw new Exception("Icon not found at file path");
            }
            // Scale the icon to 30x30 pixels
            Image scaledImage = icon.getImage().getScaledInstance(110, 64, Image.SCALE_SMOOTH);

            iconLabel.setIcon(new ImageIcon(scaledImage));
            System.out.println("Logo loaded successfully");
        } catch (Exception e) {
            // Fallback: Display a colored square and text
            System.out.println("Failed to load logo: " + e.getMessage());
            iconLabel.setOpaque(true);
            iconLabel.setBackground(Color.RED); // Red square as a placeholder
            iconLabel.setText("Icon"); // Fallback text
            iconLabel.setForeground(Color.WHITE);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        }
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Padding on the left
        topBar.add(iconLabel, BorderLayout.WEST);
        // User info on the top right
        JLabel userLabel = new JLabel("Logged in as: Admin");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        topBar.add(userLabel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    private void setupMainPanels() {

        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(245, 245, 245));
        JLabel welcomeLabel = new JLabel("Welcome to Lancaster Music Hall", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        homePanel.add(welcomeLabel, BorderLayout.CENTER);

        mainPanel.add(homePanel, "Home");
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
        // Held Seats Panel
        JPanel heldSeatsPanel = new JPanel(new BorderLayout());
        heldSeatsPanel.setBackground(new Color(245, 245, 245));

        // Form for Held Seats
        JPanel heldFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcHeld = new GridBagConstraints();
        gbcHeld.insets = new Insets(5, 5, 5, 5);
        gbcHeld.fill = GridBagConstraints.HORIZONTAL;

        gbcHeld.gridx = 0;
        gbcHeld.gridy = 0;
        heldFormPanel.add(new JLabel("Show / Date:"), gbcHeld);
        gbcHeld.gridx = 1;
        JTextField heldShowDateField = new JTextField(15);
        heldFormPanel.add(heldShowDateField, gbcHeld);

        gbcHeld.gridx = 0;
        gbcHeld.gridy = 1;
        heldFormPanel.add(new JLabel("Seats (comma-separated):"), gbcHeld);
        gbcHeld.gridx = 1;
        JTextField heldSeatsField = new JTextField(15);
        heldFormPanel.add(heldSeatsField, gbcHeld);

        // Button
        JPanel heldButtonPanel = new JPanel(new FlowLayout());
        JButton addHeldButton = new JButton("Hold Seats");
        addHeldButton.setBackground(new Color(0, 123, 255));
        addHeldButton.setForeground(Color.WHITE);
        heldButtonPanel.add(addHeldButton);

        gbcHeld.gridx = 0;
        gbcHeld.gridy = 2;
        gbcHeld.gridwidth = 2;
        heldFormPanel.add(heldButtonPanel, gbcHeld);

        heldSeatsPanel.add(heldFormPanel, BorderLayout.NORTH);

        // Table for Held Seats
        String[] heldColumns = { "Show / Date", "Seats", "Actions" };
        DefaultTableModel heldTableModel = new DefaultTableModel(heldColumns, 0);
        JTable heldTable = new JTable(heldTableModel);
        JScrollPane heldScrollPane = new JScrollPane(heldTable);
        heldSeatsPanel.add(heldScrollPane, BorderLayout.CENTER);

        // Action for holding seats
        addHeldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String showDate = heldShowDateField.getText();
                String seats = heldSeatsField.getText();

                if (showDate.isEmpty() || seats.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in both Show/Date and Seats.");
                    return;
                }

                String[] row = { showDate, seats, "Edit/Delete" };
                heldTableModel.addRow(row);

                heldShowDateField.setText("");
                heldSeatsField.setText("");
            }
        });

        // Placeholder panels for other services
        for (String service : new String[] { "Screening", "Film", "Meeting", "Invoice", "Group Sale",
                "Group", "FoL", "Held/Seats", "Ticket Sales", "Film Orders" }) {
            JPanel placeholderPanel = new JPanel();
            placeholderPanel.add(new JLabel("Panel for " + service));
            mainPanel.add(placeholderPanel, service);
        }
        JPanel screeningPanel = new JPanel(new BorderLayout());
        screeningPanel.setBackground(new Color(245, 245, 245));

        // Form for Screening
        JPanel screeningFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcScreening = new GridBagConstraints();
        gbcScreening.insets = new Insets(5, 5, 5, 5);
        gbcScreening.fill = GridBagConstraints.HORIZONTAL;

        gbcScreening.gridx = 0;
        gbcScreening.gridy = 0;
        screeningFormPanel.add(new JLabel("Date:"), gbcScreening);
        gbcScreening.gridx = 1;
        JTextField screeningDateField = new JTextField(15);
        screeningFormPanel.add(screeningDateField, gbcScreening);

        gbcScreening.gridx = 0;
        gbcScreening.gridy = 1;
        screeningFormPanel.add(new JLabel("Start Time:"), gbcScreening);
        gbcScreening.gridx = 1;
        JTextField screeningTimeField = new JTextField(15);
        screeningFormPanel.add(screeningTimeField, gbcScreening);

        gbcScreening.gridx = 0;
        gbcScreening.gridy = 2;
        screeningFormPanel.add(new JLabel("Film:"), gbcScreening);
        gbcScreening.gridx = 1;
        JTextField screeningFilmField = new JTextField(15);
        screeningFormPanel.add(screeningFilmField, gbcScreening);

        gbcScreening.gridx = 0;
        gbcScreening.gridy = 3;
        screeningFormPanel.add(new JLabel("Price:"), gbcScreening);
        gbcScreening.gridx = 1;
        JTextField screeningPriceField = new JTextField(15);
        screeningFormPanel.add(screeningPriceField, gbcScreening);

        // Buttons for Screening
        JPanel screeningButtonPanel = new JPanel(new FlowLayout());
        JButton addScreeningButton = new JButton("Add Screening");
        addScreeningButton.setBackground(new Color(0, 123, 255));
        addScreeningButton.setForeground(Color.WHITE);
        screeningButtonPanel.add(addScreeningButton);

        gbcScreening.gridx = 0;
        gbcScreening.gridy = 4;
        gbcScreening.gridwidth = 2;
        screeningFormPanel.add(screeningButtonPanel, gbcScreening);

        screeningPanel.add(screeningFormPanel, BorderLayout.NORTH);

        // Table for Screening
        String[] screeningColumns = { "Date", "Start Time", "Film", "Price", "Actions" };
        DefaultTableModel screeningTableModel = new DefaultTableModel(screeningColumns, 0);
        JTable screeningTable = new JTable(screeningTableModel);
        JScrollPane screeningScrollPane = new JScrollPane(screeningTable);
        screeningPanel.add(screeningScrollPane, BorderLayout.CENTER);

        // Add action to the button
        addScreeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = screeningDateField.getText();
                String time = screeningTimeField.getText();
                String film = screeningFilmField.getText();
                String price = screeningPriceField.getText();

                // Basic validation (optional)
                if (film.isEmpty() || price.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in both film name and price.");
                    return;
                }

                String[] row = {
                        date,
                        time,
                        film,
                        price,
                        "Edit/Delete"
                };
                screeningTableModel.addRow(row);

                // Clear form
                screeningDateField.setText("");
                screeningTimeField.setText("");
                screeningFilmField.setText("");
                screeningPriceField.setText("");
            }
        });

        mainPanel.add(screeningPanel, "Screening");
        JPanel folPanel = new JPanel(new BorderLayout());
        folPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Form for FoL
        JPanel folFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcFoL = new GridBagConstraints();
        gbcFoL.insets = new Insets(5, 5, 5, 5);
        gbcFoL.fill = GridBagConstraints.HORIZONTAL;

        gbcFoL.gridx = 0;
        gbcFoL.gridy = 0;
        folFormPanel.add(new JLabel("Name:"), gbcFoL);
        gbcFoL.gridx = 1;
        JTextField folNameField = new JTextField(15);
        folFormPanel.add(folNameField, gbcFoL);

        gbcFoL.gridx = 0;
        gbcFoL.gridy = 1;
        folFormPanel.add(new JLabel("Address:"), gbcFoL);
        gbcFoL.gridx = 1;
        JTextArea folAddressArea = new JTextArea(3, 15);
        folFormPanel.add(new JScrollPane(folAddressArea), gbcFoL);

        gbcFoL.gridx = 0;
        gbcFoL.gridy = 2;
        folFormPanel.add(new JLabel("Email:"), gbcFoL);
        gbcFoL.gridx = 1;
        JTextField folEmailField = new JTextField(15);
        folFormPanel.add(folEmailField, gbcFoL);

        // Button
        JPanel folButtonPanel = new JPanel(new FlowLayout());
        JButton addFoLButton = new JButton("Add Friend");
        addFoLButton.setBackground(new Color(0, 123, 255));
        addFoLButton.setForeground(Color.WHITE);
        folButtonPanel.add(addFoLButton);

        gbcFoL.gridx = 0;
        gbcFoL.gridy = 3;
        gbcFoL.gridwidth = 2;
        folFormPanel.add(folButtonPanel, gbcFoL);

        folPanel.add(folFormPanel, BorderLayout.NORTH);

        // Table for FoL
        String[] folColumns = { "Name", "Address", "Email", "Actions" };
        DefaultTableModel folTableModel = new DefaultTableModel(folColumns, 0);
        JTable folTable = new JTable(folTableModel);
        JScrollPane folScrollPane = new JScrollPane(folTable);
        folPanel.add(folScrollPane, BorderLayout.CENTER);

        // Action listener
        addFoLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = folNameField.getText();
                String address = folAddressArea.getText();
                String email = folEmailField.getText();

                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in both Name and Email.");
                    return;
                }

                String[] row = { name, address, email, "Edit/Delete" };
                folTableModel.addRow(row);

                // Clear fields
                folNameField.setText("");
                folAddressArea.setText("");
                folEmailField.setText("");
            }
        });

        mainPanel.add(folPanel, "FoL");
        // Film Panel
        JPanel filmPanel = new JPanel(new BorderLayout());
        filmPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Form for Film
        JPanel filmFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcFilm = new GridBagConstraints();
        gbcFilm.insets = new Insets(5, 5, 5, 5);
        gbcFilm.fill = GridBagConstraints.HORIZONTAL;

        gbcFilm.gridx = 0;
        gbcFilm.gridy = 0;
        filmFormPanel.add(new JLabel("Film Name:"), gbcFilm);
        gbcFilm.gridx = 1;
        JTextField filmNameField = new JTextField(15);
        filmFormPanel.add(filmNameField, gbcFilm);

        gbcFilm.gridx = 0;
        gbcFilm.gridy = 1;
        filmFormPanel.add(new JLabel("Certificate:"), gbcFilm);
        gbcFilm.gridx = 1;
        JTextField filmCertField = new JTextField(15);
        filmFormPanel.add(filmCertField, gbcFilm);

        // Buttons for Film
        JPanel filmButtonPanel = new JPanel(new FlowLayout());
        JButton addFilmButton = new JButton("Add Film");
        addFilmButton.setBackground(new Color(0, 123, 255));
        addFilmButton.setForeground(Color.WHITE);
        filmButtonPanel.add(addFilmButton);

        gbcFilm.gridx = 0;
        gbcFilm.gridy = 2;
        gbcFilm.gridwidth = 2;
        filmFormPanel.add(filmButtonPanel, gbcFilm);

        filmPanel.add(filmFormPanel, BorderLayout.NORTH);

        // Table for Film
        String[] filmColumns = { "Name", "Certificate", "Actions" };
        DefaultTableModel filmTableModel = new DefaultTableModel(filmColumns, 0);
        JTable filmTable = new JTable(filmTableModel);
        JScrollPane filmScrollPane = new JScrollPane(filmTable);
        filmPanel.add(filmScrollPane, BorderLayout.CENTER);

        // Add action to the button
        addFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = filmNameField.getText();
                String cert = filmCertField.getText();

                if (name.isEmpty() || cert.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in both fields.");
                    return;
                }

                String[] row = { name, cert, "Edit/Delete" };
                filmTableModel.addRow(row);

                // Clear fields
                filmNameField.setText("");
                filmCertField.setText("");
            }
        });

        mainPanel.add(filmPanel, "Film");

        mainPanel.add(heldSeatsPanel, "Held/Seats");
        // helper class

    }

    private Color blend(Color c1, Color c2, float ratio) {
        float r = Math.max(0f, Math.min(1f, ratio));
        int red = (int) (c1.getRed() * (1 - r) + c2.getRed() * r);
        int green = (int) (c1.getGreen() * (1 - r) + c2.getGreen() * r);
        int blue = (int) (c1.getBlue() * (1 - r) + c2.getBlue() * r);
        return new Color(red, green, blue);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LancasterUI());
    }
}