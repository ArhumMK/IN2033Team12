import java.util.List;
import java.util.Map;

/**
 * Interface defining the methods for the Operations component.
 */
public interface OperationsInterface {

    // Marketing Campaign Performance Data
    /**
     * Retrieves a breakdown of marketing efforts for a specific event.
     * @param eventName The name of the event.
     * @return A map containing marketing effort details (e.g., social media ads, email campaigns).
     */
    Map<String, Integer> getMarketingEfforts(String eventName);

    /**
     * Retrieves statistics on ticket purchases influenced by marketing materials.
     * @param eventName The name of the event.
     * @return A map containing ticket purchase statistics.
     */
    Map<String, Integer> getTicketPurchaseStatistics(String eventName);

    /**
     * Retrieves the effectiveness of different promotional channels for a specific event.
     * @param eventName The name of the event.
     * @return A map containing promotional channel effectiveness.
     */
    Map<String, Integer> getPromotionalChannelEffectiveness(String eventName);


    // Audience Demographics & Engagement Data
    /**
     * Retrieves demographic information about the audience for a specific event.
     * @param eventName The name of the event.
     * @return A map containing demographic data (e.g., age, location).
     */
    Map<String, String> getAudienceDemographics(String eventName);

    /**
     * Retrieves past visit information for attendees of a specific event.
     * @param eventName The name of the event.
     * @return A map containing past visit data (e.g., repeat attendees, new customers).
     */
    Map<String, Integer> getPastVisitInformation(String eventName);


    // Ticket Demand Forecasting Data
    /**
     * Retrieves predictive analytics on ticket sales trends for a specific event.
     * @param eventName The name of the event.
     * @return A map containing ticket sales trends.
     */
    Map<String, Integer> getTicketSalesTrends(String eventName);

    /**
     * Retrieves expected high-traffic dates for a specific event.
     * @param eventName The name of the event.
     * @return A list of high-traffic dates.
     */
    List<String> getHighTrafficDates(String eventName);

    /**
     * Retrieves pre-sale and early booking trends for a specific event.
     * @param eventName The name of the event.
     * @return A map containing pre-sale and early booking trends.
     */
    Map<String, Integer> getPreSaleTrends(String eventName);


    // Event Promotion & Discount Strategy Data
    /**
     * Retrieves details of ongoing or upcoming promotional discounts for a specific event.
     * @param eventName The name of the event.
     * @return A map containing promotional discount details.
     */
    Map<String, String> getPromotionalDiscounts(String eventName);

    /**
     * Retrieves information on special marketing-driven ticket categories for a specific event.
     * @param eventName The name of the event.
     * @return A map containing ticket category details.
     */
    Map<String, String> getTicketCategories(String eventName);

    /**
     * Retrieves insights into marketing-driven price adjustments for a specific event.
     * @param eventName The name of the event.
     * @return A map containing price adjustment details.
     */
    Map<String, String> getPriceAdjustments(String eventName);


    // Customer Feedback & Sentiment Analysis Data
    /**
     * Retrieves post-event feedback for a specific event.
     * @param eventName The name of the event.
     * @return A map containing feedback data.
     */
    Map<String, String> getPostEventFeedback(String eventName);

    /**
     * Retrieves customer satisfaction scores for a specific event.
     * @param eventName The name of the event.
     * @return A map containing satisfaction scores.
     */
    Map<String, Integer> getCustomerSatisfaction(String eventName);

    /**
     * Retrieves common customer complaints for a specific event.
     * @param eventName The name of the event.
     * @return A list of common complaints.
     */
    List<String> getCommonComplaints(String eventName);


    // Partnership & Sponsorship Data
    /**
     * Retrieves details of brand partnerships or sponsors for a specific event.
     * @param eventName The name of the event.
     * @return A map containing partnership or sponsor details.
     */
    Map<String, String> getPartnershipDetails(String eventName);

    /**
     * Retrieves venue-specific branding or advertising obligations for a specific event.
     * @param eventName The name of the event.
     * @return A map containing branding or advertising obligations.
     */
    Map<String, String> getBrandingObligations(String eventName);

    /**
     * Retrieves requirements for special sponsor areas or booths for a specific event.
     * @param eventName The name of the event.
     * @return A map containing sponsor area requirements.
     */
    Map<String, String> getSponsorAreaRequirements(String eventName);
}