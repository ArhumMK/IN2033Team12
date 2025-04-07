package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class JDBC {
    private static Connection connection;
    private static MarketingData marketingData;

    // Private constructor to prevent instantiation
    private JDBC() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t12";
        String username = "in2033t12_d";
        String password = "MhftnbMWQLk";

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        marketingData = new MarketingData();
    }

    // Get the database connection
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

    // Close the database connection
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }

    // Wrapper methods for MarketingData functionality

    // Fetch client details
    public static ClientDTO getClientDetails(String clientId) {
        return marketingData.getClientDetails(clientId);
    }

    // Fetch show ticket price
    public static double getShowTicketPrice(String showId) {
        return marketingData.getShowTicketPrice(showId);
    }

    // Fetch show discount
    public static double getShowDiscount(String showId) {
        return marketingData.getShowDiscount(showId);
    }

    // Fetch film details
    public static FilmDetails getFilmDetails(String filmName) {
        return marketingData.getFilmDetails(filmName);
    }

    // Fetch film screening schedule
    public static List<FilmScreeningDTO> getFilmScreeningSchedule(LocalDate startDate, LocalDate endDate) {
        return marketingData.getFilmScreeningSchedule(startDate, endDate);
    }

    // Fetch room usage details
    public static List<RoomUsageDTO> getRoomUsageDetails(LocalDate startDate, LocalDate endDate) {
        return marketingData.getRoomUsageDetails(startDate, endDate);
    }

    // Fetch events needing promotion
    public static List<String> getEventsNeedingPromotion() {
        return marketingData.getEventsNeedingPromotion();
    }

    // Fetch past visit information
    public static Map<String, Integer> getPastVisitInformation(String showId) {
        return marketingData.getPastVisitInformation(showId);
    }

    // Fetch ticket sales trends
    public static String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime) {
        return marketingData.getTicketSalesTrends(eventName, eventDate, eventTime);
    }

    // Main method as the program entry point
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