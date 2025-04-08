package api;

import java.time.LocalDate;
import java.util.List;

/**
 * A test class for verifying the functionality of the {@link api.MarketingInterface} API.
 *
 * <p>This class contains a main method that executes a series of test cases against
 * the marketing data API, demonstrating various operations including:
 * <ul>
 *   <li>Retrieving client details
 *   <li>Fetching show ticket prices and discounts
 *   <li>Getting film information
 *   <li>Querying film screening schedules
 *   <li>Checking room usage details
 * </ul>
 *
 * <p>Each test case prints its results to standard output and handles any exceptions
 * that may occur during execution. The database connection is properly closed in the
 * finally block.
 */
public class APITest {
    /**
     * Main entry point for executing the API test cases.
     *
     * <p>This method performs the following test sequence:
     * <ol>
     *   <li>Retrieves client details for ClientID 1
     *   <li>Gets ticket price and discount for ShowID 15
     *   <li>Fetches film details for "The Space Odyssey"
     *   <li>Queries film screenings between April 1-30, 2025
     *   <li>Retrieves room usage details for the same period
     * </ol>
     *
     * <p>All database operations use the MarketingData implementation
     * of the MarketingInterface. The database connection is automatically
     * closed when tests complete or if an exception occurs.
     *
     * @param args command line arguments (not used)
     *
     * @throws Exception if any database operation fails during testing
     */
    public static void main(String[] args) {
        MarketingInterface marketing = new MarketingData();

        try {
            // Test 1: Fetch Client Details for ClientID = 1
            System.out.println("Fetching client details for ClientID 1...");
            ClientDTO client = marketing.getClientDetails("1");
            System.out.println("Client: ID=" + client.getClientId() +
                    ", Name=" + client.getContactName() +
                    ", Email=" + client.getContactEmail() +
                    ", Address=" + client.getStreetAddress());

            // Test 2: Fetch Show Ticket Price and Discount for ShowID = 15
            System.out.println("\nFetching ticket price and discount for ShowID 15...");
            double price = marketing.getShowTicketPrice("15");
            double discount = marketing.getShowDiscount("15");
            System.out.println("ShowID 15: Price=" + price + ", Discount=" + discount);

            // Test 3: Fetch Film Details for "The Space Odyssey"
            System.out.println("\nFetching film details for 'The Space Odyssey'...");
            FilmDetails film = marketing.getFilmDetails("The Space Odyssey");
            System.out.println("Film: Name=" + film.getFilmName() +
                    ", Certificate=" + film.getGenre());

            // Test 4: Fetch Film Screening Schedule between 2025-04-01 and 2025-04-30
            System.out.println("\nFetching film screenings from 2025-04-01 to 2025-04-30...");
            List<FilmScreeningDTO> screenings = marketing.getFilmScreeningSchedule(
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
            List<RoomUsageDTO> roomUsages = marketing.getRoomUsageDetails(
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                JDBC.closeConnection();
                System.out.println("\nDatabase connection closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}