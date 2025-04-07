package api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketingData implements MarketingInterface {

    // Marketing Campaign Performance Data
    @Override
    public Map<String, Integer> getMarketingEfforts(String eventId, LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> efforts = new HashMap<>();
        String query = "SELECT campaign_type, SUM(clicks) as total_clicks " +
                "FROM MarketingCampaign " +
                "WHERE event_id = ? AND start_date >= ? AND end_date <= ? " +
                "GROUP BY campaign_type";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                efforts.put(rs.getString("campaign_type"), rs.getInt("total_clicks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return efforts;
    }

    @Override
    public Map<String, Integer> getTicketPurchaseStatistics(String campaignId) {
        Map<String, Integer> stats = new HashMap<>();
        String query = "SELECT 'TicketsSold', COUNT(*) as count " +
                "FROM Booking WHERE campaign_id = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, campaignId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("TicketsSold", rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stats;
    }

    @Override
    public Map<String, Double> getPromotionalChannelEffectiveness(String eventId) {
        Map<String, Double> effectiveness = new HashMap<>();
        String query = "SELECT campaign_type, (SUM(clicks) / SUM(impressions)) * 100 as effectiveness " +
                "FROM MarketingCampaign WHERE event_id = ? GROUP BY campaign_type";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                effectiveness.put(rs.getString("campaign_type"), rs.getDouble("effectiveness"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return effectiveness;
    }

    // Audience Demographics & Engagement Data
    @Override
    public Map<String, String> getAudienceDemographics(String eventInstanceId) {
        Map<String, String> demographics = new HashMap<>();
        String query = "SELECT age_group, COUNT(*) as count " +
                "FROM Attendee WHERE event_instance_id = ? GROUP BY age_group";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventInstanceId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                demographics.put(rs.getString("age_group"), String.valueOf(rs.getInt("count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return demographics;
    }

    @Override
    public Map<String, Integer> getPastVisitInformation(String eventId) {
        Map<String, Integer> visits = new HashMap<>();
        String query = "SELECT 'RepeatAttendees', COUNT(DISTINCT patron_id) as count " +
                "FROM Booking WHERE event_id = ? AND patron_id IN " +
                "(SELECT patron_id FROM Booking WHERE event_id != ? GROUP BY patron_id HAVING COUNT(*) > 1)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                visits.put("RepeatAttendees", rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return visits;
    }

    // Ticket Demand Forecasting Data
    @Override
    public Map<String, Integer> getTicketSalesTrends(String eventId, String forecastWindow) {
        Map<String, Integer> trends = new HashMap<>();
        String query = "SELECT DATE(booking_date) as sale_date, COUNT(*) as sales " +
                "FROM Booking WHERE event_id = ? AND booking_date >= DATE_SUB(CURDATE(), INTERVAL ? DAY) " +
                "GROUP BY sale_date";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setInt(2, Integer.parseInt(forecastWindow.replace(" days", "")));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trends.put(rs.getString("sale_date"), rs.getInt("sales"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return trends;
    }

    @Override
    public List<LocalDate> getHighTrafficDates(String eventId) {
        List<LocalDate> dates = new ArrayList<>();
        String query = "SELECT DATE(booking_date) as high_date " +
                "FROM Booking WHERE event_id = ? GROUP BY high_date " +
                "HAVING COUNT(*) > (SELECT AVG(daily_count) FROM (SELECT COUNT(*) as daily_count " +
                "FROM Booking WHERE event_id = ? GROUP BY DATE(booking_date)) as avg)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("high_date").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return dates;
    }

    @Override
    public Map<String, Integer> getPreSaleTrends(String eventId, String ticketType) {
        Map<String, Integer> trends = new HashMap<>();
        String query = "SELECT 'PreSaleCount', COUNT(*) as count " +
                "FROM Booking WHERE event_id = ? AND ticket_type = ? AND booking_date < (SELECT event_date FROM Shows WHERE show_id = ?)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, ticketType);
            stmt.setString(3, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                trends.put("PreSaleCount", rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return trends;
    }

    // Event Promotion & Discount Strategy Data
    @Override
    public Map<String, String> getPromotionalDiscounts(String eventId, String discountType) {
        Map<String, String> discounts = new HashMap<>();
        String query = "SELECT discount_id, discount_percentage " +
                "FROM Discount WHERE event_id = ? AND discount_type = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, discountType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                discounts.put("DiscountID", rs.getString("discount_id"));
                discounts.put("Percentage", rs.getString("discount_percentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return discounts;
    }

    @Override
    public Map<String, String> getTicketCategories(String eventId) {
        Map<String, String> categories = new HashMap<>();
        String query = "SELECT ticket_type, COUNT(*) as count " +
                "FROM Booking WHERE event_id = ? GROUP BY ticket_type";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.put(rs.getString("ticket_type"), String.valueOf(rs.getInt("count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public Map<String, String> getPriceAdjustments(String eventId, String timeframe) {
        Map<String, String> adjustments = new HashMap<>();
        String query = "SELECT adjustment_date, new_price " +
                "FROM PriceAdjustment WHERE event_id = ? AND adjustment_date >= DATE_SUB(CURDATE(), INTERVAL ? DAY)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setInt(2, Integer.parseInt(timeframe.replace(" days", "")));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                adjustments.put(rs.getString("adjustment_date"), rs.getString("new_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return adjustments;
    }

    // Customer Feedback & Sentiment Analysis Data
    @Override
    public Map<String, String> getPostEventFeedback(String eventInstanceId, String sentimentFilter) {
        Map<String, String> feedback = new HashMap<>();
        String query = "SELECT sentiment, COUNT(*) as count " +
                "FROM Feedback WHERE event_instance_id = ? AND (? IS NULL OR sentiment = ?) GROUP BY sentiment";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventInstanceId);
            stmt.setString(2, sentimentFilter);
            stmt.setString(3, sentimentFilter);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                feedback.put(rs.getString("sentiment"), String.valueOf(rs.getInt("count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return feedback;
    }

    @Override
    public double getCustomerSatisfaction(String eventInstanceId, String aspect) {
        String query = "SELECT AVG(satisfaction_score) as score " +
                "FROM Feedback WHERE event_instance_id = ? AND aspect = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventInstanceId);
            stmt.setString(2, aspect);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    @Override
    public List<String> getCommonComplaints(String eventInstanceId, String complaintCategory) {
        List<String> complaints = new ArrayList<>();
        String query = "SELECT comment FROM Feedback WHERE event_instance_id = ? AND aspect = ? AND sentiment = 'Negative'";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventInstanceId);
            stmt.setString(2, complaintCategory);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                complaints.add(rs.getString("comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return complaints;
    }

    // Partnership & Sponsorship Data
    @Override
    public Map<String, String> getPartnershipDetails(String eventId, String partnerType) {
        Map<String, String> details = new HashMap<>();
        String query = "SELECT partner_name, branding_requirements " +
                "FROM Partnership WHERE event_id = ? AND partner_type = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, partnerType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details.put("PartnerName", rs.getString("partner_name"));
                details.put("BrandingRequirements", rs.getString("branding_requirements"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return details;
    }

    @Override
    public Map<String, String> getBrandingObligations(String eventId, String venueSection) {
        Map<String, String> obligations = new HashMap<>();
        String query = "SELECT partner_name, branding_requirements " +
                "FROM Partnership WHERE event_id = ? AND venue_section = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, venueSection);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                obligations.put(rs.getString("partner_name"), rs.getString("branding_requirements"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obligations;
    }

    @Override
    public Map<String, String> getSponsorAreaRequirements(String eventId, String sponsorId) {
        Map<String, String> requirements = new HashMap<>();
        String query = "SELECT requirement_type, requirement_value " +
                "FROM SponsorRequirements WHERE event_id = ? AND sponsor_id = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, sponsorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requirements.put(rs.getString("requirement_type"), rs.getString("requirement_value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return requirements;
    }

    // Group Bookings & Company Data
    @Override
    public Map<String, String> getGroupBookingDetails(String eventName, String groupId) {
        Map<String, String> details = new HashMap<>();
        String query = "SELECT gb.group_size, c.company_name, c.contact_name, c.contact_email " +
                "FROM GroupBooking gb " +
                "JOIN Booking b ON gb.booking_id = b.booking_id " +
                "JOIN Company c ON gb.company_id = c.company_id " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND gb.group_id = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setString(2, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details.put("GroupSize", String.valueOf(rs.getInt("group_size")));
                details.put("CompanyName", rs.getString("company_name"));
                details.put("ContactName", rs.getString("contact_name"));
                details.put("ContactEmail", rs.getString("contact_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return details;
    }

    @Override
    public List<String> getCompaniesBookedForEvent(String eventName) {
        List<String> companies = new ArrayList<>();
        String query = "SELECT DISTINCT c.company_name " +
                "FROM GroupBooking gb " +
                "JOIN Booking b ON gb.booking_id = b.booking_id " +
                "JOIN Company c ON gb.company_id = c.company_id " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                companies.add(rs.getString("company_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return companies;
    }

    // Advertising & Promotion Data
    @Override
    public Map<String, String> getAdvertisingCampaigns() {
        Map<String, String> campaigns = new HashMap<>();
        String query = "SELECT campaign_id, event_id FROM MarketingCampaign WHERE active = 1";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                campaigns.put(rs.getString("campaign_id"), rs.getString("event_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return campaigns;
    }

    @Override
    public List<String> getEventsNeedingPromotion() {
        List<String> events = new ArrayList<>();
        String query = "SELECT s.show_title " +
                "FROM Shows s LEFT JOIN Booking b ON s.show_id = b.show_id " +
                "GROUP BY s.show_id, s.show_title " +
                "HAVING COUNT(b.booking_id) < (SELECT AVG(booking_count) FROM (SELECT COUNT(*) as booking_count " +
                "FROM Booking GROUP BY show_id) as avg)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(rs.getString("show_title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public Map<String, String> generatePromotionImpactReport(String eventName) {
        Map<String, String> report = new HashMap<>();
        String query = "SELECT 'TicketSales', COUNT(*) as sales " +
                "FROM Booking b JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND b.campaign_id IS NOT NULL";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                report.put("TicketSalesFromPromotion", String.valueOf(rs.getInt("sales")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return report;
    }

    // Notification Placeholders
    @Override
    public void notifyGroupBookingConfirmation(String eventName, String groupId, int groupSize) {
        System.out.println("Notifying group booking confirmation for event '" + eventName +
                "' by group '" + groupId + "' with " + groupSize + " seats.");
        // Placeholder for future notification system integration
    }

    @Override
    public void notifyGroupBookingCancellation(String eventName, String groupId, int groupSize) {
        System.out.println("Notifying group booking cancellation for event '" + eventName +
                "' by group '" + groupId + "' with " + groupSize + " seats.");
        // Placeholder for future notification system integration
    }
}