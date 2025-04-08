package api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Defines the contract for marketing operations in the cinema management system.
 *
 * <p>This interface provides methods for:
 * <ul>
 *   <li>Marketing analytics and reporting
 *   <li>Client and booking management
 *   <li>Show and film scheduling
 *   <li>Ticket pricing and discounts
 *   <li>Room usage and meetings
 *   <li>Tour arrangements
 * </ul>
 *
 * <p>Implementations should handle all database operations and business logic
 * related to these marketing functions.
 */
public interface MarketingInterface {

    /**
     * Gets past visit information for a specific show.
     * @param showId the unique identifier of the show
     * @return Map containing repeat attendee statistics with key "repeat_attendees"
     */
    Map<String, Integer> getPastVisitInformation(String showId);

    /**
     * Gets ticket sales trends for an event as JSON data.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return JSON array string containing sales data by date
     */
    String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime);

    /**
     * Identifies high traffic dates for a specific show.
     * @param showId the unique identifier of the show
     * @return List of dates with attendance exceeding 20 tickets
     */
    List<LocalDate> getHighTrafficDates(String showId);

    /**
     * Identifies high traffic dates for a show within a timeframe.
     * @param showId the unique identifier of the show
     * @param timeframe the number of days to look back (defaults to 30 if invalid)
     * @return List of dates with attendance exceeding 20 tickets
     */
    List<LocalDate> getHighTrafficDates(String showId, String timeframe);

    /**
     * Analyzes pre-sale trends for a show and ticket type.
     * @param showId the unique identifier of the show
     * @param ticketType the type of ticket being analyzed
     * @return Map containing pre-sale count with key "pre_sale_count"
     */
    Map<String, Integer> getPreSaleTrends(String showId, String ticketType);

    /**
     * Gets ticket category information including price and discount.
     * @param showId the unique identifier of the show
     * @return Map where keys are ticket types and values contain count, price and discount
     */
    Map<String, String> getTicketCategories(String showId);

    /**
     * Gets details for a specific group booking.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @param groupId the unique identifier of the group
     * @return GroupBookingDTO containing group size and company contact information
     */
    GroupBookingDTO getGroupBookingDetails(String eventName, LocalDate eventDate, String eventTime, String groupId);
    /**
     * Gets companies that have booked for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return List of company names
     */
    List<String> getCompaniesBookedForEvent(String eventName, LocalDate eventDate, String eventTime);

    /**
     * Identifies events needing promotion based on low sales.
     * @return List of event names with fewer than 10 tickets sold
     */
    List<String> getEventsNeedingPromotion();

    /**
     * Generates a report on promotion impact for an event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return JSON string containing sales attributed to promotions
     */
    String generatePromotionImpactReport(String eventName, LocalDate eventDate, String eventTime);

    /**
     * Gets confirmed bookings for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return List of BookingDTO objects
     */
    List<BookingDTO> getConfirmedBookings(String eventName, LocalDate eventDate, String eventTime);

    /**
     * Counts discounted tickets used for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return number of discounted tickets used
     */
    int getUsedDiscountedTickets(String eventName, LocalDate eventDate, String eventTime);

    /**
     * Confirms a group booking.
     * @param eventName the name of the event
     * @param groupId the unique identifier of the group
     * @param groupSize the number of people in the group
     * @return true if confirmation succeeded, false otherwise
     */
    boolean confirmGroupBooking(String eventName, String groupId, int groupSize);

    /**
     * Cancels a group booking.
     * @param eventName the name of the event
     * @param groupId the unique identifier of the group
     * @param groupSize the number of people in the group
     * @return true if cancellation succeeded, false otherwise
     */
    boolean cancelGroupBooking(String eventName, String groupId, int groupSize);

    /**
     * Gets alerts for shows with low ticket sales.
     * @return List of event names with fewer than 10 tickets sold
     */
    List<String> getLowSalesAlerts();

    /**
     * Gets details for a specific film.
     * @param filmName the name of the film
     * @return FilmDetails object containing film information
     */
    FilmDetails getFilmDetails(String filmName);

    // Client management methods

    /**
     * Gets client details by ID.
     * @param clientId the unique identifier of the client
     * @return ClientDTO containing all client information
     */
    ClientDTO getClientDetails(String clientId);

    /**
     * Updates client contact information.
     * @param clientId the unique identifier of the client
     * @param contactEmail the new email address
     * @param contactName the new contact name
     * @param streetAddress the new street address
     * @return true if update succeeded, false otherwise
     */
    boolean updateClientDetails(String clientId, String contactEmail, String contactName, String streetAddress);

    // Room booking and billing methods

    /**
     * Books a room for a client meeting.
     * @param clientId the unique identifier of the client
     * @param date the date of the meeting
     * @param startTime the start time of the meeting
     * @param title the title/description of the meeting
     * @param location the room location
     * @return true if booking succeeded, false otherwise
     */
    boolean bookRoomForClient(String clientId, LocalDate date, LocalTime startTime, String title, String location);

    /**
     * Generates an invoice for room usage.
     * @param clientId the unique identifier of the client
     * @param date the date of the invoice
     * @param cost the total cost
     * @return true if invoice generation succeeded, false otherwise
     */
    boolean generateInvoiceForRoomUsage(String clientId, LocalDate date, double cost);

    // Show pricing methods

    /**
     * Sets the ticket price for a show.
     * @param showId the unique identifier of the show
     * @param price the new ticket price
     * @return true if update succeeded, false otherwise
     */
    boolean setShowTicketPrice(String showId, double price);

    /**
     * Gets the current ticket price for a show.
     * @param showId the unique identifier of the show
     * @return the current ticket price
     */
    double getShowTicketPrice(String showId);

    // Discount management methods

    /**
     * Sets the discount rate for a show.
     * @param showId the unique identifier of the show
     * @param discount the new discount rate (0-1)
     * @return true if update succeeded, false otherwise
     */
    boolean setShowDiscount(String showId, double discount);

    /**
     * Gets the current discount rate for a show.
     * @param showId the unique identifier of the show
     * @return the current discount rate (0-1)
     */
    double getShowDiscount(String showId);

    // Scheduling methods

    /**
     * Schedules a new film screening.
     * @param filmId the unique identifier of the film
     * @param date the screening date
     * @param startTime the screening start time
     * @param price the ticket price
     * @return true if scheduling succeeded, false otherwise
     */
    boolean scheduleFilmScreening(String filmId, LocalDate date, LocalTime startTime, double price);

    /**
     * Arranges a venue tour for a client.
     * @param clientId the unique identifier of the client
     * @param date the tour date
     * @param startTime the tour start time
     * @param location the tour starting location
     * @return true if arrangement succeeded, false otherwise
     */
    boolean arrangeTour(String clientId, LocalDate date, LocalTime startTime, String location);

    // Reporting methods

    /**
     * Gets room usage details for a date range.
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return List of RoomUsageDTO objects
     */
    List<RoomUsageDTO> getRoomUsageDetails(LocalDate startDate, LocalDate endDate);

    /**
     * Gets film screening schedule for a date range.
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return List of FilmScreeningDTO objects
     */
    List<FilmScreeningDTO> getFilmScreeningSchedule(LocalDate startDate, LocalDate endDate);

    /**
     * Gets tour schedule for a date range.
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return List of TourDTO objects
     */
    List<TourDTO> getTourSchedule(LocalDate startDate, LocalDate endDate);

    /**
     * Gets all clients enrolled in the Friends of Lancaster program.
     * @return List of ClientDTO objects
     */
    List<ClientDTO> getFriendsOfLancasterClients();
}