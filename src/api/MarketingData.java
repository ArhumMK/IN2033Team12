package api;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketingData implements MarketingInterface {

    // Constructor
    public MarketingData() {}

    // Marketing Campaign Performance Data

    public String getMarketingEfforts(String eventName, LocalDate eventDate, String eventTime, LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> efforts = new HashMap<>();
        String query = "SELECT mc.campaign_type, SUM(mc.clicks) as total_clicks " +
                "FROM MarketingCampaign mc " +
                "JOIN Shows s ON mc.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? " +
                "AND mc.start_date >= ? AND mc.end_date <= ? " +
                "GROUP BY mc.campaign_type";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            stmt.setDate(4, java.sql.Date.valueOf(startDate));
            stmt.setDate(5, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                efforts.put(rs.getString("campaign_type"), rs.getInt("total_clicks"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(efforts);
        return json.toString();
    }

    public String getTicketPurchaseStatistics(String campaignId) {
        String query = "SELECT COUNT(*) as tickets_sold, SUM(total_cost) as total_revenue " +
                "FROM Booking " +
                "WHERE campaign_id = ?";
        JSONObject json = new JSONObject();

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, campaignId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                json.put("tickets_sold", rs.getInt("tickets_sold"));
                json.put("total_revenue", rs.getDouble("total_revenue"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public String getPromotionalChannelEffectiveness(String eventName, LocalDate eventDate, String eventTime) {
        Map<String, Integer> effectiveness = new HashMap<>();
        String query = "SELECT pc.channel, SUM(pc.clicks) as total_clicks " +
                "FROM PromotionalChannel pc " +
                "JOIN Shows s ON pc.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? " +
                "GROUP BY pc.channel";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                effectiveness.put(rs.getString("channel"), rs.getInt("total_clicks"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(effectiveness);
        return json.toString();
    }

    // Audience Demographics & Engagement Data

    public String getAudienceDemographics(String eventName, LocalDate eventDate, String eventTime) {
        Map<String, Integer> demographics = new HashMap<>();
        String query = "SELECT p.age_group, COUNT(*) as count " +
                "FROM Booking b " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "JOIN Patron p ON b.patron_id = p.patron_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? " +
                "GROUP BY p.age_group";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                demographics.put(rs.getString("age_group"), rs.getInt("count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(demographics);
        return json.toString();
    }

    public Map<String, Integer> getPastVisitInformation(String eventId) {
        Map<String, Integer> visits = new HashMap<>();
        String query = "SELECT COUNT(DISTINCT b.patron_id) as repeat_attendees " +
                "FROM Booking b " +
                "WHERE b.show_id = ? " +
                "AND b.patron_id IN (SELECT patron_id FROM Booking WHERE show_id != ?)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                visits.put("repeat_attendees", rs.getInt("repeat_attendees"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return visits;
    }

    // Ticket Demand Forecasting Data

    public String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT DATE(b.booking_date) as sale_date, COUNT(*) as sales " +
                "FROM Booking b " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? " +
                "GROUP BY sale_date";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("date", rs.getString("sale_date"));
                obj.put("sales", rs.getInt("sales"));
                jsonArray.put(obj);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    public List<LocalDate> getHighTrafficDates(String eventId) {
        List<LocalDate> dates = new ArrayList<>();
        String query = "SELECT s.event_date " +
                "FROM Shows s " +
                "JOIN Booking b ON s.show_id = b.show_id " +
                "WHERE s.show_id = ? " +
                "GROUP BY s.event_date " +
                "HAVING COUNT(b.booking_id) > 100";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("event_date").toLocalDate());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dates;
    }

    public List<LocalDate> getHighTrafficDates(String eventId, String timeframe) {
        List<LocalDate> dates = new ArrayList<>();
        String query = "SELECT s.event_date " +
                "FROM Shows s " +
                "JOIN Booking b ON s.show_id = b.show_id " +
                "WHERE s.show_id = ? AND b.booking_date >= DATE_SUB(s.event_date, INTERVAL ? DAY) " +
                "GROUP BY s.event_date " +
                "HAVING COUNT(b.booking_id) > 100";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            // Assuming timeframe is in days, e.g., "30" for 30 days
            int days = timeframe.matches("\\d+") ? Integer.parseInt(timeframe) : 30;
            stmt.setInt(2, days);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("event_date").toLocalDate());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dates;
    }

    public Map<String, Integer> getPreSaleTrends(String eventId, String ticketType) {
        Map<String, Integer> trends = new HashMap<>();
        String query = "SELECT COUNT(*) as pre_sale_count " +
                "FROM Booking b " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_id = ? AND b.ticket_type = ? AND b.booking_date < s.event_date";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, ticketType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                trends.put("pre_sale_count", rs.getInt("pre_sale_count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return trends;
    }

    // Event Promotion & Discount Strategy Data

    public List<DiscountPolicyDTO> getPromotionalDiscounts(String eventId, String discountType) {
        List<DiscountPolicyDTO> discounts = new ArrayList<>();
        String query = "SELECT discount_id, discount_percentage " +
                "FROM Discount " +
                "WHERE show_id = ? AND discount_type = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            stmt.setString(2, discountType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DiscountPolicyDTO dto = new DiscountPolicyDTO();
                dto.setDiscountId(rs.getString("discount_id"));
                dto.setDiscountPercentage(rs.getDouble("discount_percentage"));
                discounts.add(dto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return discounts;
    }

    public Map<String, String> getTicketCategories(String eventId) {
        Map<String, String> categories = new HashMap<>();
        String query = "SELECT ticket_type, COUNT(*) as count " +
                "FROM Booking b " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_id = ? " +
                "GROUP BY ticket_type";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.put(rs.getString("ticket_type"), String.valueOf(rs.getInt("count")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public String getPriceAdjustments(String eventName, LocalDate eventDate, String eventTime) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT adjustment_date, new_price " +
                "FROM PriceAdjustment pa " +
                "JOIN Shows s ON pa.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("adjustment_date", rs.getString("adjustment_date"));
                obj.put("new_price", rs.getDouble("new_price"));
                jsonArray.put(obj);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    // Customer Feedback & Sentiment Analysis Data

    public String getPostEventFeedback(String eventName, LocalDate eventDate, String eventTime) {
        Map<String, Integer> feedback = new HashMap<>();
        String query = "SELECT sentiment, COUNT(*) as count " +
                "FROM Feedback f " +
                "JOIN Shows s ON f.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? " +
                "GROUP BY sentiment";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                feedback.put(rs.getString("sentiment"), rs.getInt("count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(feedback);
        return json.toString();
    }

    public double getCustomerSatisfaction(String eventInstanceId, String aspect) {
        String query = "SELECT AVG(satisfaction_score) as score " +
                "FROM Feedback " +
                "WHERE show_id = ? AND aspect = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventInstanceId);
            stmt.setString(2, aspect);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("score");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public List<String> getCommonComplaints(String eventInstanceId, String complaintCategory) {
        List<String> complaints = new ArrayList<>();
        String query = "SELECT comment " +
                "FROM Feedback " +
                "WHERE show_id = ? AND complaint_category = ? AND sentiment = 'Negative'";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventInstanceId);
            stmt.setString(2, complaintCategory);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                complaints.add(rs.getString("comment"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return complaints;
    }

    // Partnership & Sponsorship Data

    public String getPartnershipDetails(String eventName, LocalDate eventDate, String eventTime) {
        JSONObject json = new JSONObject();
        String query = "SELECT partner_name, branding_requirements " +
                "FROM Partnership p " +
                "JOIN Shows s ON p.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                json.put("partner_name", rs.getString("partner_name"));
                json.put("branding_requirements", rs.getString("branding_requirements"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public String getBrandingObligations(String eventName, LocalDate eventDate, String eventTime) {
        JSONObject json = new JSONObject();
        String query = "SELECT partner_name, branding_obligations " +
                "FROM Partnership p " +
                "JOIN Shows s ON p.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                json.put(rs.getString("partner_name"), rs.getString("branding_obligations"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public String getSponsorAreaRequirements(String eventName, LocalDate eventDate, String eventTime) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT requirement_type, requirement_value " +
                "FROM SponsorRequirements sr " +
                "JOIN Shows s ON sr.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(rs.getString("requirement_type"), rs.getString("requirement_value"));
                jsonArray.put(obj);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    // Group Bookings & Company Data

    public GroupBookingDTO getGroupBookingDetails(String eventName, LocalDate eventDate, String eventTime, String groupId) {
        GroupBookingDTO dto = new GroupBookingDTO();
        String query = "SELECT gb.group_size, c.company_name, c.contact_name, c.contact_email " +
                "FROM GroupBooking gb " +
                "JOIN Booking b ON gb.booking_id = b.booking_id " +
                "JOIN Company c ON gb.company_id = c.company_id " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? AND gb.group_id = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            stmt.setString(4, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dto.setGroupSize(rs.getInt("group_size"));
                dto.setCompanyName(rs.getString("company_name"));
                dto.setContactName(rs.getString("contact_name"));
                dto.setContactEmail(rs.getString("contact_email"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dto;
    }

    public List<String> getCompaniesBookedForEvent(String eventName, LocalDate eventDate, String eventTime) {
        List<String> companies = new ArrayList<>();
        String query = "SELECT DISTINCT c.company_name " +
                "FROM GroupBooking gb " +
                "JOIN Booking b ON gb.booking_id = b.booking_id " +
                "JOIN Company c ON gb.company_id = c.company_id " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                companies.add(rs.getString("company_name"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return companies;
    }

    // Advertising & Promotion Data

    public String getAdvertisingCampaigns() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT campaign_id, show_id " +
                "FROM MarketingCampaign " +
                "WHERE active = 1";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("campaign_id", rs.getString("campaign_id"));
                obj.put("show_id", rs.getString("show_id"));
                jsonArray.put(obj);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    public List<String> getEventsNeedingPromotion() {
        List<String> events = new ArrayList<>();
        String query = "SELECT s.show_title " +
                "FROM Shows s " +
                "LEFT JOIN Booking b ON s.show_id = b.show_id " +
                "GROUP BY s.show_id, s.show_title " +
                "HAVING COUNT(b.booking_id) < 10";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(rs.getString("show_title"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return events;
    }

    public String generatePromotionImpactReport(String eventName, LocalDate eventDate, String eventTime) {
        JSONObject json = new JSONObject();
        String query = "SELECT COUNT(*) as sales_from_promotion " +
                "FROM Booking b " +
                "JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? AND b.campaign_id IS NOT NULL";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                json.put("sales_from_promotion", rs.getInt("sales_from_promotion"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    // Existing Implemented Methods (Retained as Provided)

    public List<String> getBestSeatsForVIP(String eventName, LocalDate eventDate, String eventTime, String vipType) {
        List<String> seats = new ArrayList<>();
        String query = "SELECT seat_code FROM VIPSeats WHERE event_name = ? AND event_date = ? AND event_time = ? AND vip_type = ?";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            stmt.setString(4, vipType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                seats.add(rs.getString("seat_code"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public List<BookingDTO> getConfirmedBookings(String eventName, LocalDate eventDate, String eventTime) {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT b.booking_id, b.patron_id, b.total_cost " +
                "FROM Booking b JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? AND b.is_confirmed = 1";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDTO booking = new BookingDTO();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setPatronId(rs.getInt("patron_id"));
                booking.setTotalCost(rs.getDouble("total_cost"));
                bookings.add(booking);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public Map<String, Object> getPriorityBookingTrends(String year, String season) {
        Map<String, Object> trends = new HashMap<>();
        String query = "SELECT COUNT(*) as count, booking_type FROM Booking WHERE year(booking_date) = ? AND season = ? GROUP BY booking_type";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, year);
            stmt.setString(2, season);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trends.put(rs.getString("booking_type"), rs.getInt("count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return trends;
    }

    public int getUsedDiscountedTickets(String eventName, LocalDate eventDate, String eventTime) {
        String query = "SELECT COUNT(*) as used FROM Booking b JOIN Shows s ON b.show_id = s.show_id " +
                "WHERE s.show_title = ? AND s.event_date = ? AND s.event_time = ? AND b.is_discount_applied = 1";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("used");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean confirmGroupBooking(String eventName, String groupId, int groupSize) {
        String query = "UPDATE GroupBooking SET is_confirmed = 1 WHERE group_id = ? AND event_name = ?";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, groupId);
            stmt.setString(2, eventName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelGroupBooking(String eventName, String groupId, int groupSize) {
        String query = "UPDATE GroupBooking SET is_cancelled = 1 WHERE group_id = ? AND event_name = ?";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, groupId);
            stmt.setString(2, eventName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getLowSalesAlerts() {
        List<String> alerts = new ArrayList<>();
        String query = "SELECT s.show_title FROM Shows s LEFT JOIN Booking b ON s.show_id = b.show_id " +
                "GROUP BY s.show_id, s.show_title HAVING COUNT(b.booking_id) < 10";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alerts.add(rs.getString("show_title"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return alerts;
    }

    public FilmDetails getFilmDetails(String filmName) {
        FilmDetails details = new FilmDetails();
        String query = "SELECT film_name, duration_minutes, genre FROM Films WHERE film_name = ?";
        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, filmName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details.setFilmName(rs.getString("film_name"));
                details.setDurationMinutes(rs.getInt("duration_minutes"));
                details.setGenre(rs.getString("genre"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return details;
    }
}