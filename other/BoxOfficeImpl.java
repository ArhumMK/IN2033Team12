import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class BoxOfficeImpl extends BoxOfficeBase {

    @Override
    public List<String> getUpcomingFilms() {
        System.out.println("Fetching upcoming films data from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    
  public FilmDetails getFilmDetails(String filmName) {
    System.out.println("Fetching film details for film '" + filmName + "' from the database...");

    // Sample hardcoded response until database integration
    FilmDetails details = new FilmDetails();
    details.setTitle(filmName);
    details.setDuration("2h 10m");
    details.setVenue("Main Hall A");
    details.setSchedulingStatus("Scheduled");
    details.setDirector("Jane Doe");
    details.setGenre("Drama");
    details.setReleaseDate("2025-04-01");
    details.setRating("PG-13");

    return details;
}


    @Override
    public List<String> getFilmScheduleHistory(String filmName) {
        System.out.println("Fetching film schedule history for film '" + filmName);
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public Map<String, String> getGroupBookingDetails(String eventName, String groupId) {
        System.out.println("Fetching group booking details for event '" + eventName + "' by " + groupId);
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    // helper function to send a notification.
    private boolean sendNotification(String subject, String message) {
        // Once DB connectivity is established, this can be expanded or another approach
        // can be implemented.
        System.out.println("Notification Sent - " + subject + ": " + message);
        return false; // false for now
    }

    @Override
    public void notifyGroupBookingConfirmation(String eventName, String groupId, int groupSize) {
        // data from database to be added later
        String subject = "Group Booking Confirmation for - " + eventName + " by " + groupId;
        String message = "A group booking has been confirmed for event '"
                + eventName + "' with " + groupSize + " seats." + " by " + groupId;
        sendNotification(subject, message);
        System.out.println("Notifying group booking confirmation for event '" + eventName
                + "' by group '" + groupId + "' with " + groupSize + " seats.");
    }

    @Override
    public void notifyGroupBookingCancellation(String eventName, String groupId, int groupSize) {
        // data from database to be added later
        String subject = "Group Booking Cancellation for - " + eventName + " by " + groupId;
        String message = "A group booking has been cancelled for event '"
                + eventName + "' that had " + groupSize + " seats booked." + " by " + groupId;
        sendNotification(subject, message);
        System.out.println("Notifying group booking cancellation for event '" + eventName
                + "' by group '" + groupId + "' with " + groupSize + " seats.");
    }

    @Override
    public List<String> getHeldRowsForGroupBookings(String eventName, String groupId) {
        System.out.println("Fetching held rows for group bookings for event '" + eventName + "' by " + groupId);
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public Map<String, String> getDiscountPolicies(String eligibleCategory) {
        System.out.println("Fetching discount policies for eligible category '" + eligibleCategory + "' from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public int getAvailableDiscountedTickets(String eventName) {
        System.out.println("Fetching available discounted tickets for event '" + eventName + "' from the database...");
        // data from database to be added later
        return 0; // Placeholder: return 0 for now.
    }

    @Override
    public Map<String, String> getDiscountAllocation(String eventName) {
        System.out.println("Fetching discount allocation details for event " + eventName);
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public List<String> getTimeLimitedOffers() {
        System.out.println("Fetching current time-limited offers from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public List<String> getFriendsPrioritySeats(String eventName) {
        System.out.println("Fetching reserved seats for Lancaster's Friends for event '" + eventName + "' from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
public List<String> getBestSeatsForVIP(String eventName) {
    System.out.println("Fetching best seats for VIP guests for event: " + eventName);
    
    // Placeholder return: Example VIP seat allocations
    return Arrays.asList("A1", "A2", "B1", "B2");
}


    @Override
    public Map<String, String> getPriorityBookingTrends(String year,String season) {
        System.out.println("Fetching historical priority booking trends from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public String getFriendsBookingPeriod(String eventName) {
        System.out.println("Fetching booking period details for Lancaster's Friends for event '" + eventName + "' from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public Map<String, String> getAdvertisingCampaigns() {
        System.out.println("Fetching advertising campaigns details from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public List<String> getEventsNeedingPromotion() {
        System.out.println("Fetching events needing promotion from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }

    @Override
    public Map<String, String> generatePromotionImpactReport(String eventName) {
        System.out.println("Fetching promotion impact report for event '" + eventName + "' from the database...");
        // data from database to be added later
        return null; // Placeholder: return null for now.
    }
    @Override
    public List<String> getConfirmedBookings(String eventName) {
        System.out.println("Fetching confirmed bookings for event: " + eventName);
        return null;
    }
    
    @Override
    public int getUsedDiscountedTickets(String eventName) {
        System.out.println("Fetching used discounted tickets for event: " + eventName);
        return 0;
    }
    
    @Override
    public boolean confirmGroupBooking(String eventName, String groupId, int groupSize) {
        System.out.println("Confirming group booking for event: " + eventName + ", group: " + groupId);
        return true;
    }
    
    @Override
    public boolean cancelGroupBooking(String eventName, String groupId, int groupSize) {
        System.out.println("Cancelling group booking for event: " + eventName + ", group: " + groupId);
        return true;
    }
    
    @Override
    public List<String> getLowSalesAlerts() {
        System.out.println("Fetching real-time low sales alerts...");
        return null;
    }
    


}
