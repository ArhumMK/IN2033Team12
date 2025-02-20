import java.util.List;
import java.util.Map;

public abstract class BoxOfficeBase implements BoxOfficeInterface {

    @Override
    public abstract List<String> getUpcomingFilms();

    @Override
    public abstract Map<String, String> getFilmDetails(String filmName);

    @Override
    public abstract List<String> getFilmScheduleHistory(String filmName);

    @Override
    public abstract Map<String, String> getGroupBookingDetails(String eventName, String groupId);

    @Override
    public abstract void notifyGroupBookingConfirmation(String eventName, String groupId, int groupSize);

    @Override
    public abstract void notifyGroupBookingCancellation(String eventName, String groupId, int groupSize);

    @Override
    public abstract List<String> getHeldRowsForGroupBookings(String eventName, String groupId);

    @Override
    public abstract Map<String, String> getDiscountPolicies(String eligibleCategory);

    @Override
    public abstract int getAvailableDiscountedTickets(String eventName);

    @Override
    public abstract Map<String, String> getDiscountAllocation(String eventName);

    @Override
    public abstract List<String> getTimeLimitedOffers();

    @Override
    public abstract List<String> getFriendsPrioritySeats(String eventName);

    @Override
    public abstract Map<String, String> getPriorityBookingTrends();

    @Override
    public abstract String getFriendsBookingPeriod(String eventName);

    @Override
    public abstract Map<String, String> getAdvertisingCampaigns();

    @Override
    public abstract List<String> getEventsNeedingPromotion();

    @Override
    public abstract Map<String, String> generatePromotionImpactReport(String eventName);
}
