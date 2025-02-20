import java.util.List;
import java.util.Map;

public interface MarketingBoxOfficeInterface {

    /**
     * Retrieves a list of upcoming films with their dates and times.
     *
     * @return A list of upcoming films.
     */
    List<String> getUpcomingFilms();

    /**
     * Retrieves details for a specific film, such as duration and venue.
     *
     * @param filmName The name of the film for which details are requested.
     * @return A map containing film details (e.g., duration, venue).
     */
    Map<String, String> getFilmDetails(String filmName);

    /**
     * Retrieves the best seats reserved for priority bookings for a specific event.
     *
     * @param eventName The name of the event for which priority seats are requested.
     * @return A list of priority seats.
     */
    List<String> getPrioritySeats(String eventName);

    /**
     * Retrieves details for group bookings for a specific event.
     *
     * @param eventName The name of the event for which group booking details are requested.
     * @return A map containing group booking details (e.g., number of seats, preferences).
     */
    Map<String, String> getGroupBookingDetails(String eventName);

    /**
     * Notifies the Marketing team when a group booking is confirmed.
     *
     * @param eventName The name of the event for which the group booking is confirmed.
     * @param groupSize The size of the group booking.
     */
    void notifyGroupBookingConfirmation(String eventName, int groupSize);

    /**
     * Retrieves discount policies and eligibility criteria.
     *
     * @return A map containing discount policies and eligibility criteria.
     */
    Map<String, String> getDiscountPolicies();

    /**
     * Retrieves the number of discounted tickets available for a specific event.
     *
     * @param eventName The name of the event for which discounted tickets are requested.
     * @return The number of discounted tickets available.
     */
    int getAvailableDiscountedTickets(String eventName);

    /**
     * Retrieves seats reserved for Lancaster's Friends for a specific event.
     *
     * @param eventName The name of the event for which priority seats are requested.
     * @return A list of seats reserved for Lancaster�s Friends.
     */
    List<String> getFriendsPrioritySeats(String eventName);

    /**
     * Retrieves trends in priority bookings for Lancaster's Friends over the years.
     *
     * @return A map containing trends in priority bookings.
     */
    Map<String, String> getPriorityBookingTrends();

    /**
     * Retrieves details of active advertising campaigns.
     *
     * @return A map containing details of active advertising campaigns.
     */
    Map<String, String> getAdvertisingCampaigns();

    /**
     * Retrieves events with low ticket sales that need promotion.
     *
     * @return A list of events needing promotion.
     */
    List<String> getEventsNeedingPromotion();
}