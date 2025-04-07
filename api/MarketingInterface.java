package api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MarketingInterface {

    // Marketing Campaign Performance Data
    Map<String, Integer> getMarketingEfforts(String eventId, LocalDate startDate, LocalDate endDate);
    Map<String, Integer> getTicketPurchaseStatistics(String campaignId);
    Map<String, Double> getPromotionalChannelEffectiveness(String eventId);

    // Audience Demographics & Engagement Data
    Map<String, String> getAudienceDemographics(String eventInstanceId);
    Map<String, Integer> getPastVisitInformation(String eventId);

    // Ticket Demand Forecasting Data
    Map<String, Integer> getTicketSalesTrends(String eventId, String forecastWindow);
    List<LocalDate> getHighTrafficDates(String eventId);
    Map<String, Integer> getPreSaleTrends(String eventId, String ticketType);

    // Event Promotion & Discount Strategy Data
    Map<String, String> getPromotionalDiscounts(String eventId, String discountType);
    Map<String, String> getTicketCategories(String eventId);
    Map<String, String> getPriceAdjustments(String eventId, String timeframe);

    // Customer Feedback & Sentiment Analysis Data
    Map<String, String> getPostEventFeedback(String eventInstanceId, String sentimentFilter);
    double getCustomerSatisfaction(String eventInstanceId, String aspect);
    List<String> getCommonComplaints(String eventInstanceId, String complaintCategory);

    // Partnership & Sponsorship Data
    Map<String, String> getPartnershipDetails(String eventId, String partnerType);
    Map<String, String> getBrandingObligations(String eventId, String venueSection);
    Map<String, String> getSponsorAreaRequirements(String eventId, String sponsorId);

    // Group Bookings & Company Data
    Map<String, String> getGroupBookingDetails(String eventName, String groupId);
    List<String> getCompaniesBookedForEvent(String eventName);

    // Advertising & Promotion Data from Box Office
    Map<String, String> getAdvertisingCampaigns();
    List<String> getEventsNeedingPromotion();
    Map<String, String> generatePromotionImpactReport(String eventName);

    // Notification Placeholders
    void notifyGroupBookingConfirmation(String eventName, String groupId, int groupSize);
    void notifyGroupBookingCancellation(String eventName, String groupId, int groupSize);
}