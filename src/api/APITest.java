package api;

import java.time.LocalDate;
import java.util.List;

public class APITest {
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