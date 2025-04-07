package api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// DTO Classes
class FilmDetails {
    private String filmName;
    private int durationMinutes;
    private String genre;

    // Getters and Setters
    public String getFilmName() { return filmName; }
    public void setFilmName(String filmName) { this.filmName = filmName; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}

class BookingDTO {
    private int bookingId;
    private int patronId;
    private double totalCost;

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getPatronId() { return patronId; }
    public void setPatronId(int patronId) { this.patronId = patronId; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}

class GroupBookingDTO {
    private int groupSize;
    private String companyName;
    private String contactName;
    private String contactEmail;

    // Getters and Setters
    public int getGroupSize() { return groupSize; }
    public void setGroupSize(int groupSize) { this.groupSize = groupSize; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}

class DiscountPolicyDTO {
    private String discountId;
    private double discountPercentage;

    // Getters and Setters
    public String getDiscountId() { return discountId; }
    public void setDiscountId(String discountId) { this.discountId = discountId; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }
}

// Main MarketingData Class
public class MarketingData implements MarketingInterface {

    @Override
    public String getMarketingEfforts(String eventName, LocalDate eventDate, String eventTime, LocalDate startDate, LocalDate endDate) {
        return "";
    }

    @Override
    public String getTicketPurchaseStatistics(String campaignId) {
        return "";
    }

    @Override
    public String getPromotionalChannelEffectiveness(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public String getAudienceDemographics(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public Map<String, Integer> getPastVisitInformation(String eventId) {
        return Map.of();
    }

    @Override
    public String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public List<LocalDate> getHighTrafficDates(String eventId) {
        return List.of();
    }

    @Override
    public List<LocalDate> getHighTrafficDates(String eventId, String timeframe) {
        return List.of();
    }

    @Override
    public Map<String, Integer> getPreSaleTrends(String eventId, String ticketType) {
        return Map.of();
    }

    @Override
    public List<DiscountPolicyDTO> getPromotionalDiscounts(String eventId, String discountType) {
        return List.of();
    }

    @Override
    public Map<String, String> getTicketCategories(String eventId) {
        return Map.of();
    }

    @Override
    public String getPriceAdjustments(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public String getPostEventFeedback(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public double getCustomerSatisfaction(String eventInstanceId, String aspect) {
        return 0;
    }

    @Override
    public List<String> getCommonComplaints(String eventInstanceId, String complaintCategory) {
        return List.of();
    }

    @Override
    public String getPartnershipDetails(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public String getBrandingObligations(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public String getSponsorAreaRequirements(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
    public GroupBookingDTO getGroupBookingDetails(String eventName, LocalDate eventDate, String eventTime, String groupId) {
        return null;
    }

    @Override
    public List<String> getCompaniesBookedForEvent(String eventName, LocalDate eventDate, String eventTime) {
        return List.of();
    }

    @Override
    public String getAdvertisingCampaigns() {
        return "";
    }

    @Override
    public List<String> getEventsNeedingPromotion() {
        return List.of();
    }

    @Override
    public String generatePromotionImpactReport(String eventName, LocalDate eventDate, String eventTime) {
        return "";
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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