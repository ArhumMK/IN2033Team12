package api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MarketingInterface {

    // Marketing Campaign Performance Data
    String getMarketingEfforts(String eventName, LocalDate eventDate, String eventTime, LocalDate startDate, LocalDate endDate);
    String getTicketPurchaseStatistics(String campaignId);
    String getPromotionalChannelEffectiveness(String eventName, LocalDate eventDate, String eventTime);

    // Audience Demographics & Engagement Data
    String getAudienceDemographics(String eventName, LocalDate eventDate, String eventTime);
    Map<String, Integer> getPastVisitInformation(String eventId);

    // Ticket Demand Forecasting Data
    String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime);
    List<LocalDate> getHighTrafficDates(String eventId);
    List<LocalDate> getHighTrafficDates(String eventId, String timeframe); // Overloaded method
    Map<String, Integer> getPreSaleTrends(String eventId, String ticketType);

    // Event Promotion & Discount Strategy Data
    List<DiscountPolicyDTO> getPromotionalDiscounts(String eventId, String discountType);
    Map<String, String> getTicketCategories(String eventId);
    String getPriceAdjustments(String eventName, LocalDate eventDate, String eventTime);

    // Customer Feedback & Sentiment Analysis Data
    String getPostEventFeedback(String eventName, LocalDate eventDate, String eventTime);
    double getCustomerSatisfaction(String eventInstanceId, String aspect);
    List<String> getCommonComplaints(String eventInstanceId, String complaintCategory);

    // Partnership & Sponsorship Data
    String getPartnershipDetails(String eventName, LocalDate eventDate, String eventTime);
    String getBrandingObligations(String eventName, LocalDate eventDate, String eventTime);
    String getSponsorAreaRequirements(String eventName, LocalDate eventDate, String eventTime);

    // Group Bookings & Company Data
    GroupBookingDTO getGroupBookingDetails(String eventName, LocalDate eventDate, String eventTime, String groupId);
    List<String> getCompaniesBookedForEvent(String eventName, LocalDate eventDate, String eventTime);

    // Advertising & Promotion Data
    String getAdvertisingCampaigns();
    List<String> getEventsNeedingPromotion();
    String generatePromotionImpactReport(String eventName, LocalDate eventDate, String eventTime);

    // VIP and Priority Bookings
    List<String> getBestSeatsForVIP(String eventName, LocalDate eventDate, String eventTime, String vipType);
    Map<String, Object> getPriorityBookingTrends(String year, String season);

    // Confirmed Bookings
    List<BookingDTO> getConfirmedBookings(String eventName, LocalDate eventDate, String eventTime);

    // Discount Tracking
    int getUsedDiscountedTickets(String eventName, LocalDate eventDate, String eventTime);

    // Group Booking Management
    boolean confirmGroupBooking(String eventName, String groupId, int groupSize);
    boolean cancelGroupBooking(String eventName, String groupId, int groupSize);

    // Real-Time Notifications
    List<String> getLowSalesAlerts();

    // Film Details
    FilmDetails getFilmDetails(String filmName);
}