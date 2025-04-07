package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

public class LancasterUI extends JFrame {

    private JPanel sidebarPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LancasterUI() {
        setTitle("Cinema Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        setupSidebar();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setupMainPanels();

        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        setupTopBar();

        setVisible(true);
    }

    private void setupSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(Color.decode("#2FCC40"));
        sidebarPanel.setPreferredSize(new Dimension(140, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] services = { "Home", "Show", "Screening", "Film", "Meeting", "Client", "Invoice",
                "Group Sale", "Group", "FoL", "Held/Seats", "Ticket Sales", "Film Orders" };

        for (String service : services) {
            // Custom button with underline effect
            UnderlineButton button = new UnderlineButton(service);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(Color.decode("#122023"));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setMaximumSize(new Dimension(120, 30));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.decode("#2FCC40"), 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)));

            Color normalColor = Color.decode("#122023");
            Color hoverColor = new Color(50, 50, 50);
            Color pressedColor = new Color(0, 123, 255);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(hoverColor);
                    button.setHovered(true);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(normalColor);
                    button.setHovered(false);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    button.setBackground(pressedColor);
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(hoverColor);
                }
            });

            button.addActionListener(e -> cardLayout.show(mainPanel, service));
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createVerticalStrut(8));
        }
    }

    private void setupTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.decode("#122023")); // Original color restored
        topBar.setPreferredSize(new Dimension(getWidth(), 50));
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon("resources/noBackground.png");
            if (icon.getIconWidth() == -1) throw new Exception("Icon not found");
            Image scaledImage = icon.getImage().getScaledInstance(110, 64, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.out.println("Failed to load logo: " + e.getMessage());
            iconLabel.setOpaque(true);
            iconLabel.setBackground(Color.RED);
            iconLabel.setText("Icon");
            iconLabel.setForeground(Color.WHITE);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        topBar.add(iconLabel, BorderLayout.WEST);

        JLabel userLabel = new JLabel("Logged in as: Admin", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        topBar.add(userLabel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    private void setupMainPanels() {
        // Home Panel with SVG-Inspired Background
        JPanel homePanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background: #122023 (dark teal)
                g2d.setColor(Color.decode("#122023"));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Scale SVG coordinates (647.68x364.32) to panel size
                float widthScale = getWidth() / 647.68f;
                float heightScale = getHeight() / 364.32f;

                // Define wavy paths (translated from SVG <path> data)
                Color[] fillColors = {
                        Color.decode("#40B14C"), Color.decode("#3ba548"), Color.decode("#369944"),
                        Color.decode("#318d40"), Color.decode("#2c813c"), Color.decode("#277538"),
                        Color.decode("#226934"), Color.decode("#1d5d30"), Color.decode("#18512c"),
                        Color.decode("#134528"), Color.decode("#0e3924"), Color.decode("#092d20")
                };
                float[] yOffsets = { -3.12f, 30.00f, 63.12f, 96.24f, 129.36f, 162.48f, 195.60f, 228.72f, 261.84f, 294.96f, 328.08f, 361.20f };

                for (int i = 0; i < fillColors.length; i++) {
                    g2d.setColor(fillColors[i]);
                    GeneralPath path = new GeneralPath();
                    path.moveTo(-211.92 * widthScale, yOffsets[i] * heightScale);

                    // Simplified Bezier curves based on SVG 'S' (smooth curve) commands
                    if (i == 0) {
                        path.curveTo(-120.00 * widthScale, -27.12 * heightScale, 0.00 * widthScale, -3.12 * heightScale, 0.00 * widthScale, -3.12 * heightScale);
                        path.curveTo(53.92 * widthScale, -30.12 * heightScale, 211.92 * widthScale, -3.12 * heightScale, 211.92 * widthScale, -3.12 * heightScale);
                        path.curveTo(226.84 * widthScale, -27.12 * heightScale, 423.84 * widthScale, -3.12 * heightScale, 423.84 * widthScale, -3.12 * heightScale);
                        path.curveTo(518.76 * widthScale, -35.12 * heightScale, 635.76 * widthScale, -3.12 * heightScale, 635.76 * widthScale, -3.12 * heightScale);
                        path.curveTo(731.72 * widthScale, -19.68 * heightScale, 847.68 * widthScale, -3.12 * heightScale, 847.68 * widthScale, -3.12 * heightScale);
                        path.curveTo(943.64 * widthScale, -33.12 * heightScale, 1059.60 * widthScale, -3.12 * heightScale, 1059.60 * widthScale, -3.12 * heightScale);
                    } else {
                        // Use a simpler approximation for subsequent paths (adjust as needed)
                        float[][] controlPoints = {
                                {-115.96f, yOffsets[i] - 30f}, {0.00f, yOffsets[i]}, {95.96f, yOffsets[i] - 17f},
                                {211.92f, yOffsets[i]}, {307.88f, yOffsets[i] - 17f}, {423.84f, yOffsets[i]},
                                {519.80f, yOffsets[i] - 20f}, {635.76f, yOffsets[i]}, {731.72f, yOffsets[i] - 17f},
                                {847.68f, yOffsets[i]}, {943.64f, yOffsets[i] - 17f}, {1059.60f, yOffsets[i]}
                        };
                        for (int j = 0; j < controlPoints.length - 1; j += 3) {
                            path.curveTo(
                                    controlPoints[j][0] * widthScale, controlPoints[j][1] * heightScale,
                                    controlPoints[j + 1][0] * widthScale, controlPoints[j + 1][1] * heightScale,
                                    controlPoints[j + 2][0] * widthScale, controlPoints[j + 2][1] * heightScale
                            );
                        }
                    }

                    // Close the path to the bottom of the panel
                    path.lineTo(1169.60 * widthScale, yOffsets[i] * heightScale);
                    path.lineTo(1169.60 * widthScale, getHeight());
                    path.lineTo(-211.92 * widthScale, getHeight());
                    path.closePath();
                    g2d.fill(path);
                }
            }
        };
        homePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Center Section: Logo and Text
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon("resources/logo.png");
            if (icon.getIconWidth() == -1) throw new Exception("Logo not found");
            Image scaledImage = icon.getImage().getScaledInstance(200, 116, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.out.println("Failed to load logo: " + e.getMessage());
            logoLabel.setIcon(new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(Color.decode("#122023"));
                    g2d.fillRect(x + 20, y + 20, 60, 40); // Stage
                    g2d.setColor(Color.decode("#2FCC40"));
                    g2d.fillOval(x + 30, y + 10, 40, 40); // Spotlight
                }
                @Override public int getIconWidth() { return 100; }
                @Override public int getIconHeight() { return 100; }
            });
        }
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        // Welcome Title
        JLabel welcomeLabel = new JLabel("Welcome to Lancaster Music Hall", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 28));
        welcomeLabel.setForeground(Color.WHITE); // White for contrast on dark background
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        centerPanel.add(welcomeLabel);

        // Intro Text
        String introText = "<html><div style='text-align: center; width: 500px;'>"
                + "Nestled in the heart of the community, Lancaster Music Hall is your go-to destination for unforgettable performances,<br>"
                + "cinematic experiences, and vibrant cultural events.<br><br>"
                + "Whether you're here to enjoy a live show, catch a film, or host a special gathering,<br>"
                + "our venue blends tradition with modern flair to bring people together through the power of the arts."
                + "</div></html>";
        JLabel introLabel = new JLabel(introText, SwingConstants.CENTER);
        introLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        introLabel.setForeground(Color.WHITE);
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        introLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        centerPanel.add(introLabel);

        // Tagline
        JLabel taglineLabel = new JLabel("Where Stories Come to Life", SwingConstants.CENTER);
        taglineLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        taglineLabel.setForeground(Color.decode("#2FCC40"));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        taglineLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        centerPanel.add(taglineLabel);

        homePanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(homePanel, "Home");

        // Other Panels
        JPanel showPanel = createFormPanel("Show", new String[] {"Date", "Start Time", "Name", "Venue", "Price", "Actions"});
        mainPanel.add(showPanel, "Show");

        JPanel clientPanel = createFormPanel("Client", new String[] {"Name", "Address", "Email", "Actions"});
        mainPanel.add(clientPanel, "Client");

        JPanel meetingPanel = createFormPanel("Meeting", new String[] {"Client", "Date/Time", "Title", "Location", "Actions"});
        mainPanel.add(meetingPanel, "Meeting");

        JPanel heldSeatsPanel = createFormPanel("Held/Seats", new String[] {"Show / Date", "Seats", "Actions"});
        mainPanel.add(heldSeatsPanel, "Held/Seats");

        JPanel screeningPanel = createFormPanel("Screening", new String[] {"Date", "Start Time", "Film", "Price", "Actions"});
        mainPanel.add(screeningPanel, "Screening");

        JPanel folPanel = createFormPanel("FoL", new String[] {"Name", "Address", "Email", "Actions"});
        mainPanel.add(folPanel, "FoL");

        JPanel filmPanel = createFormPanel("Film", new String[] {"Name", "Certificate", "Actions"});
        mainPanel.add(filmPanel, "Film");

        for (String service : new String[] {"Invoice", "Group Sale", "Group", "Ticket Sales", "Film Orders"}) {
            JPanel placeholderPanel = new JPanel(new BorderLayout());
            placeholderPanel.setBackground(new Color(245, 245, 245));
            placeholderPanel.add(new JLabel("Panel for " + service, SwingConstants.CENTER), BorderLayout.CENTER);
            mainPanel.add(placeholderPanel, service);
        }
    }

    private JPanel createFormPanel(String panelName, String[] tableColumns) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#122023"), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Component[] inputs = new Component[tableColumns.length - 1]; // Store all input components

        for (int i = 0; i < inputs.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(tableColumns[i] + ":");
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.decode("#122023"));
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            if (panelName.equals("Client") && i == 1 || panelName.equals("FoL") && i == 1) {
                JTextArea textArea = new JTextArea(3, 15);
                textArea.setFont(new Font("Arial", Font.PLAIN, 14));
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode("#2FCC40"), 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
                textArea.setBackground(new Color(245, 245, 245));
                inputs[i] = textArea;
                formPanel.add(new JScrollPane(textArea), gbc);
            } else {
                JTextField field = new JTextField(15) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(getBackground());
                        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                        super.paintComponent(g2);
                        g2.dispose();
                    }

                    @Override
                    protected void paintBorder(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.decode("#2FCC40"));
                        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                        g2.dispose();
                    }
                };
                field.setFont(new Font("Arial", Font.PLAIN, 14));
                field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                field.setBackground(new Color(245, 245, 245));
                field.setOpaque(false);
                inputs[i] = field;
                formPanel.add(field, gbc);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton addButton = new JButton("Add " + panelName) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#122023"));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                g2.dispose();
            }
        };
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setFocusPainted(false);
        addButton.setOpaque(false);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(new Color(30, 144, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(new Color(0, 123, 255));
            }
        });
        buttonPanel.add(addButton);
        gbc.gridx = 0;
        gbc.gridy = inputs.length;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(tableColumns, 0);
        JTable table = new JTable(tableModel);
        styleTable(table);
        table.getColumnModel().getColumn(tableColumns.length - 1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                actionPanel.setOpaque(false);

                JButton editButton = new JButton("Edit");
                editButton.setFont(new Font("Arial", Font.PLAIN, 12));
                editButton.setBackground(new Color(255, 193, 7));
                editButton.setForeground(Color.WHITE);
                editButton.setFocusPainted(false);
                editButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JButton deleteButton = new JButton("Delete");
                deleteButton.setFont(new Font("Arial", Font.PLAIN, 12));
                deleteButton.setBackground(new Color(220, 53, 69));
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setFocusPainted(false);
                deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                actionPanel.add(editButton);
                actionPanel.add(deleteButton);

                editButton.addActionListener(e -> {
                    for (int i = 0; i < inputs.length; i++) {
                        String cellValue = table.getValueAt(row, i).toString();
                        if (inputs[i] instanceof JTextField) {
                            ((JTextField) inputs[i]).setText(cellValue);
                        } else if (inputs[i] instanceof JTextArea) {
                            ((JTextArea) inputs[i]).setText(cellValue);
                        }
                    }
                    tableModel.removeRow(row);
                });

                deleteButton.addActionListener(e -> tableModel.removeRow(row));

                return actionPanel;
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#122023"), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String[] row = new String[tableColumns.length];
            for (int i = 0; i < inputs.length; i++) {
                if (inputs[i] instanceof JTextField) {
                    row[i] = ((JTextField) inputs[i]).getText();
                } else if (inputs[i] instanceof JTextArea) {
                    row[i] = ((JTextArea) inputs[i]).getText();
                }
                if (row[i].isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }
            }
            row[tableColumns.length - 1] = "";
            tableModel.addRow(row);
            for (Component input : inputs) {
                if (input instanceof JTextField) {
                    ((JTextField) input).setText("");
                } else if (input instanceof JTextArea) {
                    ((JTextArea) input).setText("");
                }
            }
        });

        return panel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(Color.decode("#122023"));
        table.setBackground(new Color(245, 245, 245));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setForeground(Color.WHITE);
        header.setBackground(Color.decode("#2FCC40"));
        header.setBorder(BorderFactory.createLineBorder(Color.decode("#122023"), 1));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new Font("Arial", Font.PLAIN, 12));
                if (isSelected) {
                    c.setBackground(new Color(0, 123, 255));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(230, 230, 230) : new Color(245, 245, 245));
                    c.setForeground(Color.decode("#122023"));
                }
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                ((JLabel) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#2FCC40")));
                return c;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }


    private static class UnderlineButton extends JButton {
        private boolean isHovered = false;

        public UnderlineButton(String text) {
            super(text);
            setOpaque(true);
            setBorderPainted(false);
        }

        public void setHovered(boolean hovered) {
            this.isHovered = hovered;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isHovered) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                int y = getHeight() - 5;
                int width = getWidth();
                int lineWidth = (int) (width * 0.6);
                int startX = (width - lineWidth) / 2;
                g2d.drawLine(startX, y, startX + lineWidth, y);
                g2d.dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LancasterUI());
    }
}