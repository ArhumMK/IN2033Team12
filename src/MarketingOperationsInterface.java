public interface MarketingOperationsInterface {

    /**
     * Retrieves the performance metrics for a specific marketing campaign.
     *
     * @param eventName The name of the event for which campaign performance is requested.
     * @return A map containing engagement metrics (e.g., clicks, impressions) for the campaign.
     */
    Map<String, Integer> getCampaignPerformance(String eventName);

    /**
     * Retrieves the most effective promotional channels used for marketing campaigns.
     *
     * @return A list of the most effective promotional channels (e.g., social media, email).
     */
    List<String> getEffectivePromotionalChannels();

    /**
     * Retrieves demographic information about the audience for a specific event.
     *
     * @param eventName The name of the event for which audience demographics are requested.
     * @return A map containing demographic data (e.g., age, location) for the event.
     */
    Map<String, String> getAudienceDemographics(String eventName);

    /**
     * Retrieves the number of repeat attendees for a specific event.
     *
     * @param eventName The name of the event for which repeat attendees are requested.
     * @return The number of repeat attendees for the event.
     */
    int getRepeatAttendees(String eventName);

    /**
     * Retrieves ticket sales trends for a specific event.
     *
     * @param eventName The name of the event for which ticket sales trends are requested.
     * @return A map containing ticket sales trends (e.g., daily sales, peak sales periods).
     */
    Map<String, Integer> getTicketSalesTrends(String eventName);

    /**
     * Retrieves dates with expected high demand for ticket sales.
     *
     * @return A list of dates with expected high demand.
     */
    List<String> getHighDemandDates();

    /**
     * Retrieves discount details for a specific event.
     *
     * @param eventName The name of the event for which discount details are requested.
     * @return A map containing discount details (e.g., discount type, eligibility criteria).
     */
    Map<String, String> getDiscountDetails(String eventName);

    /**
     * Retrieves promotional ticket categories for a specific event.
     *
     * @param eventName The name of the event for which promotional ticket categories are requested.
     * @return A list of promotional ticket categories (e.g., VIP, student discounts).
     */
    List<String> getPromotionalTicketCategories(String eventName);

    /**
     * Retrieves customer feedback and sentiment analysis for a specific event.
     *
     * @param eventName The name of the event for which feedback is requested.
     * @return A map containing feedback and sentiment analysis (e.g., positive/negative reviews).
     */
    Map<String, String> getCustomerFeedback(String eventName);

    /**
     * Retrieves common complaints from customers for a specific event.
     *
     * @param eventName The name of the event for which complaints are requested.
     * @return A list of common complaints (e.g., long queues, unclear signage).
     */
    List<String> getCommonComplaints(String eventName);

    /**
     * Retrieves sponsor details and obligations for a specific event.
     *
     * @param eventName The name of the event for which sponsor details are requested.
     * @return A map containing sponsor details (e.g., branding requirements, VIP areas).
     */
    Map<String, String> getSponsorDetails(String eventName);
}