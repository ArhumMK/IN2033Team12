package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Provides database connectivity and serves as a facade for marketing data operations.
 *
 * <p>This class handles all database connection management and delegates marketing-related
 * operations to the MarketingData class. It implements the Singleton pattern
 * to ensure a single database connection is maintained throughout the application.
 *
 * <p><b>Key Responsibilities:</b>
 * <ul>
 *   <li>Manages MySQL database connection lifecycle
 *   <li>Provides static access to marketing data operations
 *   <li>Handles connection pooling and reconnection
 *   <li>Includes test methods for verifying database operations
 * </ul>
 *
 * <p><b>Connection Details:</b>
 * <ul>
 *   <li>URL: jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t12
 *   <li>Driver: com.mysql.cj.jdbc.Driver
 * </ul>
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * try {
 *     // Get client details
 *     ClientDTO client = JDBC.getClientDetails("1");
 *
 *     // Get show pricing
 *     double price = JDBC.getShowTicketPrice("15");
 *
 * } finally {
 *     JDBC.closeConnection();
 * }
 * </pre>
 *
 * @see Connection
 */
public class JDBC {
    private static Connection connection;
    private static MarketingData marketingData;

    // Private constructor to prevent instantiation
    /**
     * Private constructor to enforce Singleton pattern.
     * @throws SQLException if database connection fails
     * @throws ClassNotFoundException if JDBC driver not found
     */
    private JDBC() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t12";
        String username = "in2033t12_d";
        String password = "MhftnbMWQLk";

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        marketingData = new MarketingData();
    }

    /**
     * Gets the active database connection, establishing a new one if necessary.
     *
     * @return active database Connection object
     * @throws SQLException if connection cannot be established
     * @throws ClassNotFoundException if JDBC driver not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t12";
            String username = "in2033t12_d";
            String password = "MhftnbMWQLk";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }
        // Initialize marketingData if not already done
        if (marketingData == null) {
            marketingData = new MarketingData();
        }
        return connection;
    }

    /**
     * Closes the active database connection if it exists and is open.
     * @throws SQLException if connection cannot be closed
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Gets client details by ID.
     * @param clientId the client identifier
     * @return ClientDTO containing client information
     */
    public static ClientDTO getClientDetails(String clientId) {
        return marketingData.getClientDetails(clientId);
    }

    /**
     * Gets the ticket price for a specific show.
     * @param showId the show identifier
     * @return ticket price as double
     */
    public static double getShowTicketPrice(String showId) {
        return marketingData.getShowTicketPrice(showId);
    }

    /**
     * Gets the discount rate for a specific show.
     * @param showId the show identifier
     * @return discount rate as double (0-1)
     */
    public static double getShowDiscount(String showId) {
        return marketingData.getShowDiscount(showId);
    }

    /**
     * Gets film details by name.
     * @param filmName the film title
     * @return FilmDetails object containing film information
     */
    public static FilmDetails getFilmDetails(String filmName) {
        return marketingData.getFilmDetails(filmName);
    }

    /**
     * Gets film screenings within a date range.
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return List of FilmScreeningDTO objects
     */
    public static List<FilmScreeningDTO> getFilmScreeningSchedule(LocalDate startDate, LocalDate endDate) {
        return marketingData.getFilmScreeningSchedule(startDate, endDate);
    }

    /**
     * Gets room usage details within a date range.
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return List of RoomUsageDTO objects
     */
    public static List<RoomUsageDTO> getRoomUsageDetails(LocalDate startDate, LocalDate endDate) {
        return marketingData.getRoomUsageDetails(startDate, endDate);
    }

    /**
     * Gets list of events needing promotion.
     * @return List of event names with low ticket sales
     */
    public static List<String> getEventsNeedingPromotion() {
        return marketingData.getEventsNeedingPromotion();
    }

    /**
     * Gets past visit information for a show.
     * @param showId the show identifier
     * @return Map containing repeat attendee statistics
     */
    public static Map<String, Integer> getPastVisitInformation(String showId) {
        return marketingData.getPastVisitInformation(showId);
    }

    /**
     * Gets ticket sales trends for an event as JSON.
     * @param eventName the event name
     * @param eventDate the event date
     * @param eventTime the event start time
     * @return JSON string containing sales data
     */
    public static String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime) {
        return marketingData.getTicketSalesTrends(eventName, eventDate, eventTime);
    }

    /**
     * Main method for testing database operations.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Ensure the connection and marketingData are initialized
            getConnection();

            // Test 1: Fetch Client Details for ClientID = 1
            System.out.println("Fetching client details for ClientID 1...");
            ClientDTO client = getClientDetails("1");
            System.out.println("Client: ID=" + client.getClientId() +
                    ", Name=" + client.getContactName() +
                    ", Email=" + client.getContactEmail() +
                    ", Address=" + client.getStreetAddress());

            // Test 2: Fetch Show Ticket Price and Discount for ShowID = 15
            System.out.println("\nFetching ticket price and discount for ShowID 15...");
            double price = getShowTicketPrice("15");
            double discount = getShowDiscount("15");
            System.out.println("ShowID 15: Price=" + price + ", Discount=" + discount);

            // Test 3: Fetch Film Details for "The Space Odyssey"
            System.out.println("\nFetching film details for 'The Space Odyssey'...");
            FilmDetails film = getFilmDetails("The Space Odyssey");
            System.out.println("Film: Name=" + film.getFilmName() +
                    ", Certificate=" + film.getGenre());

            // Test 4: Fetch Film Screening Schedule between 2025-04-01 and 2025-04-30
            System.out.println("\nFetching film screenings from 2025-04-01 to 2025-04-30...");
            List<FilmScreeningDTO> screenings = getFilmScreeningSchedule(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 30)
            );
            for (FilmScreeningDTO screening : screenings) {
                System.out.println("Screening: FilmID=" + screening.getFilmId() +
                        ", Date=" + screening.getDate() +
                        ", StartTime=" + screening.getStartTime() +
                        ", Price=" + screening.getPrice());
            }

            // Test 5: Fetch Room Usage Details between 2025-04-01 and 2025-04-30
            System.out.println("\nFetching room usage details from 2025-04-01 to 2025-04-30...");
            List<RoomUsageDTO> roomUsages = getRoomUsageDetails(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 30)
            );
            for (RoomUsageDTO usage : roomUsages) {
                System.out.println("Room Usage: ClientID=" + usage.getClientId() +
                        ", Date=" + usage.getDate() +
                        ", StartTime=" + usage.getStartTime() +
                        ", Title=" + usage.getTitle() +
                        ", Location=" + usage.getLocation());
            }

            // Test 6: Fetch Events Needing Promotion
            System.out.println("\nFetching events needing promotion...");
            List<String> events = getEventsNeedingPromotion();
            System.out.println("Events Needing Promotion: " + events);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection();
                System.out.println("\nDatabase connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}