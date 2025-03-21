import java.util.List;
import java.util.Map;
import dto.FilmDetails;


public interface BoxOfficeInterface {

    // Film and Event Scheduling
    /**
     * Retrieves a list of upcoming films with their dates, times, and venues.
     * @return A list of upcoming films.
     */
    List<String> getUpcomingFilms();

    /**
     * Retrieves details for a specific film (e.g., duration, venue, scheduling status).
     * @param filmName The name of the film.
     * @return A map containing film details.
     */
    FilmDetails getFilmDetails(String filmName);

    /**
     * Retrieves the scheduling history for a film including when it was added, removed, or rescheduled.
     * @param filmName The name of the film.
     * @return A list of scheduling history events.
     */
    List<String> getFilmScheduleHistory(String filmName);

    
    List<String> getConfirmedBookings(String eventName);


    // Group Bookings
    /**
     * Retrieves details for group bookings including event name, group size, seating preferences, and special requirements.
     * @param eventName The name of the event.
     * @param groupId The string ID of the group
     * @return A map containing group booking details.
     */
    Map<String, String> getGroupBookingDetails(String eventName, String groupId);

    /**
     * Notifies the Marketing team when a group booking is confirmed.
     * @param eventName The name of the event.
     * @param groupId The string ID of the group
     * @param groupSize The size of the group booking.
     */
    void notifyGroupBookingConfirmation(String eventName, String groupId, int groupSize);

    /**
     * Notifies the Marketing team when a group booking is cancelled.
     * @param eventName The name of the event.
     * @param groupId The string ID of the group
     * @param groupSize The size of the group booking.
     */
    void notifyGroupBookingCancellation(String eventName, String groupId, int groupSize);

    /**
     * Retrieves the held rows for group bookings for a specific event.
     * @param eventName The name of the event.
     * @param groupId The string ID of the group
     * @return A list of held rows.
     */
    List<String> getHeldRowsForGroupBookings(String eventName, String groupId);


    // Discounts and Offers
    /**
     * Retrieves discount policies based customer catergory.
     * @param eligibleCategory The category of the eligibility criteria (for e.g. NHS, military, disabled etc.)
     * @return A map containing discount policies.
     */
    Map<String, String> getDiscountPolicies(String eligibleCategory);

    /**
     * Retrieves the number of discounted tickets available for a specific event.
     * @param eventName The name of the event.
     * @return The number of discounted tickets available.
     */
    int getAvailableDiscountedTickets(String eventName);

    /**
     * Retrieves detailed discount allocation information for a specific event.
     * @param eventName The name of the event.
     * @return A map containing discount allocation details.
     */
    Map<String, String> getDiscountAllocation(String eventName);

    /**
     * Retrieves a list of time-limited offers currently available.
     * @return A list of active time-limited offers.
     */
    List<String> getTimeLimitedOffers();


    // Lancaster's Friends Priority Bookings
    /**
     * Retrieves the best seats reserved for Lancaster's Friends for a specific event.
     * @param eventName The name of the event.
     * @return A list of reserved seats.
     */
    List<String> getFriendsPrioritySeats(String eventName);

    /**
     * Retrieves trends in priority bookings for Lancaster's Friends over previous years.
     * @return A map containing historical booking trends.
     */
    Map<String, String> getPriorityBookingTrends(String year, String season);


    /**
     * Retrieves the booking period details for Lancaster's Friends (e.g., how many weeks in advance they can book).
     * @param eventName The name of the event.
     * @return A string representing the booking period.
     */
    String getFriendsBookingPeriod(String eventName);


    // Advertising & Ticket Sales Performance
    /**
     * Retrieves details of active advertising campaigns and their targeted events.
     * @return A map containing advertising campaign details.
     */
    Map<String, String> getAdvertisingCampaigns();

    /**
     * Retrieves a list of events that require additional promotion due to low ticket sales.
     * @return A list of events needing promotion.
     */
    List<String> getEventsNeedingPromotion();

    /**
     * Generates a report detailing how promotions are impacting ticket sales for a specific event.
     * @param eventName The name of the event.
     * @return A map containing promotion impact metrics.
     */
    Map<String, String> generatePromotionImpactReport(String eventName);

    int getUsedDiscountedTickets(String eventName);

    boolean confirmGroupBooking(String eventName, String groupId, int groupSize);
    boolean cancelGroupBooking(String eventName, String groupId, int groupSize);
    //Real-time Notifications for Low Sales Events
     List<String> getLowSalesAlerts();

     


}
