import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OperationsInterface {

    // Marketing Campaign Performance Data
    /**
     * Retrieves a breakdown of marketing efforts (e.g., social media, email) for an event within a timeframe.
     * @param eventId Unique event identifier (e.g., "EVENT-2023-001").
     * @param startDate Start date for the analysis period. These are int for now until we get Database connectivity
     * @param endDate End date for the analysis period.
     * @return Map of marketing efforts (e.g., "SocialMediaClicks: 5000").
     */
    Map<String, Integer> getMarketingEfforts(String eventId, int startDate, int endDate);

    /**
     * Retrieves statistics linking ticket purchases to specific marketing campaigns.
     * @param campaignId Unique ID of the marketing campaign (e.g., "CAMPAIGN-123").
     * @return Map of ticket purchases (e.g., "TicketsSold: 150").
     */
    Map<String, Integer> getTicketPurchaseStatistics(String campaignId);

    /**
     * Compares effectiveness of promotional channels (e.g., paid ads vs. organic).
     * @param eventId Unique event identifier.
     * @return Map of channel effectiveness (e.g., "PaidAds: 60%, Organic: 40%").
     */
    Map<String, Double> getPromotionalChannelEffectiveness(String eventId);

    // Audience Demographics & Engagement Data
    /**
     * Retrieves demographic data for attendees of a specific event instance.
     * @param eventInstanceId Unique ID for an event occurrence (e.g., "EVENT-2023-001-NIGHT1").
     * @return Map of demographics (e.g., "Age18-25: 40%").
     */
    Map<String, String> getAudienceDemographics(String eventInstanceId);

    /**
     * Retrieves past visit history (repeat vs. new attendees) for an event.
     * @param eventId Unique event identifier.
     * @return Map of attendance history (e.g., "RepeatAttendees: 200, NewAttendees: 100").
     */
    Map<String, Integer> getPastVisitInformation(String eventId);

    // Ticket Demand Forecasting Data
    /**
     * Predicts ticket sales trends based on historical data and marketing reach.
     * @param eventId Unique event identifier.
     * @param forecastWindow Timeframe for prediction (e.g., "30 days").
     * @return Map of trends (e.g., "PeakDay: 2023-11-01, ExpectedSales: 300").
     */
    Map<String, Integer> getTicketSalesTrends(String eventId, String forecastWindow);

    /**
     * Identifies high-traffic dates for staffing/resource planning.
     * @param eventId Unique event identifier.
     * @return List of high-traffic dates (e.g., ["2023-11-01", "2023-11-02"]).
     */
    List<LocalDate> getHighTrafficDates(String eventId);

    /**
     * Analyzes pre-sale and early booking trends.
     * @param eventId Unique event identifier.
     * @param ticketType Ticket category (e.g., "VIP", "General").
     * @return Map of trends (e.g., "VIPEarlySales: 50, GeneralEarlySales: 200").
     */
    Map<String, Integer> getPreSaleTrends(String eventId, String ticketType);

    // Event Promotion & Discount Strategy Data
    /**
     * Retrieves active/pending promotional discounts for an event.
     * @param eventId Unique event identifier.
     * @param discountType Type of discount (e.g., "Student", "EarlyBird").
     * @return Map of discount details (e.g., "StudentDiscount: 15%, ValidUntil: 2023-10-30").
     */
    Map<String, String> getPromotionalDiscounts(String eventId, String discountType);

    /**
     * Retrieves marketing-driven ticket categories (e.g., VIP, influencer passes).
     * @param eventId Unique event identifier.
     * @return Map of ticket categories (e.g., "VIP: 100 seats, Influencer: 20 seats").
     */
    Map<String, String> getTicketCategories(String eventId);

    /**
     * Analyzes price adjustments driven by marketing strategies.
     * @param eventId Unique event identifier.
     * @param timeframe Analysis period (e.g., "Last 30 days").
     * @return Map of price changes (e.g., "BasePriceIncrease: 10%, VIPPriceDecrease: 5%").
     */
    Map<String, String> getPriceAdjustments(String eventId, String timeframe);

    // Customer Feedback & Analysis Data
    /**
     * Retrieves post-event feedback from surveys/social media.
     * @param eventInstanceId Unique event occurrence ID.
     * @param sentimentFilter Optional filter (e.g., "Negative", "Positive").
     * @return Map of feedback (e.g., "Negative: 20%, Positive: 75%").
     */
    Map<String, String> getPostEventFeedback(String eventInstanceId, String sentimentFilter);

    /**
     * Retrieves customer satisfaction scores for specific event aspects.
     * @param eventInstanceId Unique event occurrence ID.
     * @param aspect Category (e.g., "VenueAccessibility", "TicketingProcess").
     * @return Satisfaction score (e.g., "VenueAccessibility: 4.5/5").
     */
    double getCustomerSatisfaction(String eventInstanceId, String aspect);

    /**
     * Retrieves common complaints filtered by category (e.g., "Queues", "Signage").
     * @param eventInstanceId Unique event occurrence ID.
     * @param complaintCategory Optional filter for complaint type.
     * @return List of complaints (e.g., "Long queues at entry").
     */
    List<String> getCommonComplaints(String eventInstanceId, String complaintCategory);

    // Partnership & Sponsorship Data
    /**
     * Retrieves details of sponsors/partners associated with an event.
     * @param eventId Unique event identifier.
     * @param partnerType Optional filter (e.g., "VenueSponsor", "MediaPartner").
     * @return Map of partner details (e.g., "SponsorName: ABC Corp, BrandingRequirements: Logo placement in lobby").
     */
    Map<String, String> getPartnershipDetails(String eventId, String partnerType);

    /**
     * Retrieves venue-specific branding obligations for sponsors.
     * @param eventId Unique event identifier.
     * @param venueSection Section of the venue (e.g., "Lobby", "VIP Lounge").
     * @return Map of obligations (e.g., "Lobby: Sponsor banners, VIP Lounge: Branded seating").
     */
    Map<String, String> getBrandingObligations(String eventId, String venueSection);

    /**
     * Retrieves requirements for sponsor areas/booths.
     * @param eventId Unique event identifier.
     * @param sponsorId Unique sponsor identifier.
     * @return Map of requirements (e.g., "BoothSize: 10x10ft, PowerRequirements: 220V").
     */
    Map<String, String> getSponsorAreaRequirements(String eventId, String sponsorId);
}