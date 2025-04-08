package api;

import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the MarketingInterface providing data access and business logic
 * for marketing-related operations in the cinema management system.
 *
 * <p>This class handles all database interactions for:
 * <ul>
 *   <li>Client and booking management
 *   <li>Show and film scheduling
 *   <li>Ticket sales and pricing
 *   <li>Room usage and meetings
 *   <li>Marketing analytics and reporting
 * </ul>
 *
 * <p>Key features include:
 * <ul>
 *   <li>JDBC-based data access with prepared statements
 *   <li>JSON-formatted reports for web integration
 *   <li>Comprehensive date-based querying
 *   <li>Support for both individual and group bookings
 * </ul>
 */
public class MarketingData implements MarketingInterface {

    /**
     * Constructs a new MarketingData instance.
     */
    public MarketingData() {}

    // Existing methods (unchanged)
    /**
     * Retrieves past visit information for a specific show.
     * @param showId the unique identifier of the show
     * @return Map containing repeat attendee count with key "repeat_attendees"
     */
    @Override
    public Map<String, Integer> getPastVisitInformation(String showId) {
        Map<String, Integer> visits = new HashMap<>();
        String query = "SELECT COUNT(DISTINCT fo.ClientID) as repeat_attendees " +
                "FROM FilmOrder fo " +
                "JOIN Invoice i ON fo.FilmOrderID = i.FilmOrderID " +
                "WHERE fo.ClientID IN (SELECT ClientID FROM FilmOrder WHERE FilmOrderID != fo.FilmOrderID)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                visits.put("repeat_attendees", rs.getInt("repeat_attendees"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return visits;
    }

    /**
     * Gets ticket sales trends for a specific event as JSON data.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return JSON array string containing sales data by date
     */
    @Override
    public String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT i.Date as sale_date, COUNT(*) as sales " +
                "FROM Invoice i " +
                "JOIN FilmOrder fo ON i.FilmOrderID = fo.FilmOrderID " +
                "JOIN TicketSales ts ON i.FilmOrderID = ts.FilmOrderID " +
                "JOIN Shows s ON s.Date = i.Date " +
                "WHERE s.Name = ? AND s.Date = ? AND s.StartTime = ? " +
                "GROUP BY i.Date";

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

    /**
     * Identifies high traffic dates for a specific show.
     * @param showId the unique identifier of the show
     * @return List of dates with attendance exceeding 20 tickets
     */
    @Override
    public List<LocalDate> getHighTrafficDates(String showId) {
        List<LocalDate> dates = new ArrayList<>();
        String query = "SELECT s.Date " +
                "FROM Shows s " +
                "JOIN HeldSeats hs ON s.ShowID = hs.ShowID " +
                "JOIN TicketSales ts ON ts.InvoiceID IN (SELECT InvoiceID FROM Invoice WHERE Date = s.Date) " +
                "WHERE s.ShowID = ? " +
                "GROUP BY s.Date " +
                "HAVING SUM(ts.Quantity) > 20";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, showId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("Date").toLocalDate());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * Identifies high traffic dates for a show within a specific timeframe.
     * @param showId the unique identifier of the show
     * @param timeframe the number of days to look back (defaults to 30 if invalid)
     * @return List of dates with attendance exceeding 20 tickets
     */
    @Override
    public List<LocalDate> getHighTrafficDates(String showId, String timeframe) {
        List<LocalDate> dates = new ArrayList<>();
        String query = "SELECT s.Date " +
                "FROM Shows s " +
                "JOIN HeldSeats hs ON s.ShowID = hs.ShowID " +
                "JOIN TicketSales ts ON ts.InvoiceID IN (SELECT InvoiceID FROM Invoice WHERE Date >= DATE_SUB(s.Date, INTERVAL ? DAY)) " +
                "WHERE s.ShowID = ? " +
                "GROUP BY s.Date " +
                "HAVING SUM(ts.Quantity) > 20";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            int days = timeframe.matches("\\d+") ? Integer.parseInt(timeframe) : 30;
            stmt.setInt(1, days);
            stmt.setString(2, showId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("Date").toLocalDate());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * Analyzes pre-sale trends for a specific show and ticket type.
     * @param showId the unique identifier of the show
     * @param ticketType the type of ticket being analyzed
     * @return Map containing pre-sale count with key "pre_sale_count"
     */
    @Override
    public Map<String, Integer> getPreSaleTrends(String showId, String ticketType) {
        Map<String, Integer> trends = new HashMap<>();
        String query = "SELECT COUNT(*) as pre_sale_count " +
                "FROM Invoice i " +
                "JOIN Shows s ON i.Date < s.Date " +
                "WHERE s.ShowID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, showId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                trends.put("pre_sale_count", rs.getInt("pre_sale_count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return trends;
    }

    /**
     * Gets ticket category information including price and discount details.
     * @param showId the unique identifier of the show
     * @return Map where keys are ticket types and values contain count, price and discount
     */
    @Override
    public Map<String, String> getTicketCategories(String showId) {
        Map<String, String> categories = new HashMap<>();
        // Adjusted to include price and discount
        String query = "SELECT 'Standard' as ticket_type, SUM(ts.Quantity) as count, s.Price, s.Discount " +
                "FROM TicketSales ts " +
                "JOIN Invoice i ON ts.InvoiceID = i.InvoiceID " +
                "JOIN Shows s ON i.Date = s.Date " +
                "WHERE s.ShowID = ? " +
                "GROUP BY ticket_type, s.Price, s.Discount";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, showId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String details = "Count: " + rs.getInt("count") +
                        ", Price: " + rs.getDouble("Price") +
                        ", Discount: " + rs.getDouble("Discount");
                categories.put(rs.getString("ticket_type"), details);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Retrieves details for a specific group booking.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @param groupId the unique identifier of the group
     * @return GroupBookingDTO containing group size and company contact information
     */
    @Override
    public GroupBookingDTO getGroupBookingDetails(String eventName, LocalDate eventDate, String eventTime, String groupId) {
        GroupBookingDTO dto = new GroupBookingDTO();
        String query = "SELECT gs.SeatsQuantity, c.CompanyName, c.ContactName, c.ContactEmail " +
                "FROM GroupSale gs " +
                "JOIN Company c ON gs.CompanyID = c.CompanyID " +
                "JOIN Shows s ON gs.ShowID = s.ShowID " +
                "WHERE s.Name = ? AND s.Date = ? AND s.StartTime = ? AND gs.GroupID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            stmt.setString(4, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dto.setGroupSize(rs.getInt("SeatsQuantity"));
                dto.setCompanyName(rs.getString("CompanyName"));
                dto.setContactName(rs.getString("ContactName"));
                dto.setContactEmail(rs.getString("ContactEmail"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dto;
    }

    /**
     * Gets a list of companies that have booked for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return List of company names
     */
    @Override
    public List<String> getCompaniesBookedForEvent(String eventName, LocalDate eventDate, String eventTime) {
        List<String> companies = new ArrayList<>();
        String query = "SELECT DISTINCT c.CompanyName " +
                "FROM GroupSale gs " +
                "JOIN Company c ON gs.CompanyID = c.CompanyID " +
                "JOIN Shows s ON gs.ShowID = s.ShowID " +
                "WHERE s.Name = ? AND s.Date = ? AND s.StartTime = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                companies.add(rs.getString("CompanyName"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return companies;
    }


    /**
     * Identifies events needing promotion based on low ticket sales.
     * @return List of event names with fewer than 10 tickets sold
     */
    @Override
    public List<String> getEventsNeedingPromotion() {
        List<String> events = new ArrayList<>();
        String query = "SELECT s.Name " +
                "FROM Shows s " +
                "LEFT JOIN HeldSeats hs ON s.ShowID = hs.ShowID " +
                "LEFT JOIN TicketSales ts ON ts.InvoiceID IN (SELECT InvoiceID FROM Invoice WHERE Date = s.Date) " +
                "GROUP BY s.ShowID, s.Name " +
                "HAVING COALESCE(SUM(ts.Quantity), 0) < 10";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(rs.getString("Name"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Generates a report on promotion impact for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return JSON string containing sales attributed to promotions
     */
    @Override
    public String generatePromotionImpactReport(String eventName, LocalDate eventDate, String eventTime) {
        JSONObject json = new JSONObject();
        String query = "SELECT SUM(gs.SeatsQuantity) as sales_from_promotion " +
                "FROM GroupSale gs " +
                "JOIN Shows s ON gs.ShowID = s.ShowID " +
                "WHERE s.Name = ? AND s.Date = ? AND s.StartTime = ? AND gs.Discount > 0";

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

    /**
     * Gets all confirmed bookings for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return List of BookingDTO objects
     */
    @Override
    public List<BookingDTO> getConfirmedBookings(String eventName, LocalDate eventDate, String eventTime) {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT fo.FilmOrderID, fo.ClientID, fo.TotalCost " +
                "FROM FilmOrder fo " +
                "JOIN Invoice i ON fo.FilmOrderID = i.FilmOrderID " +
                "JOIN Shows s ON i.Date = s.Date " +
                "WHERE s.Name = ? AND s.Date = ? AND s.StartTime = ? AND fo.Status = 'Confirmed'";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, eventName);
            stmt.setDate(2, java.sql.Date.valueOf(eventDate));
            stmt.setString(3, eventTime);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDTO booking = new BookingDTO();
                booking.setBookingId(rs.getInt("FilmOrderID"));
                booking.setPatronId(rs.getInt("ClientID"));
                booking.setTotalCost(rs.getDouble("TotalCost"));
                bookings.add(booking);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Counts discounted tickets used for a specific event.
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param eventTime the start time of the event
     * @return number of discounted tickets used
     */
    @Override
    public int getUsedDiscountedTickets(String eventName, LocalDate eventDate, String eventTime) {
        String query = "SELECT SUM(gs.SeatsQuantity) as used " +
                "FROM GroupSale gs " +
                "JOIN Shows s ON gs.ShowID = s.ShowID " +
                "WHERE s.Name = ? AND s.Date = ? AND s.StartTime = ? AND gs.Discount > 0";

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

    /**
     * Confirms a group booking.
     * @param eventName the name of the event
     * @param groupId the unique identifier of the group
     * @param groupSize the number of people in the group
     * @return true if confirmation was successful, false otherwise
     */
    @Override
    public boolean confirmGroupBooking(String eventName, String groupId, int groupSize) {
        String query = "UPDATE GroupSale SET Confirmed = 'Yes' " +
                "WHERE GroupID = ? AND ShowID IN (SELECT ShowID FROM Shows WHERE Name = ?)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, groupId);
            stmt.setString(2, eventName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cancels a group booking.
     * @param eventName the name of the event
     * @param groupId the unique identifier of the group
     * @param groupSize the number of people in the group
     * @return true if cancellation was successful, false otherwise
     */
    @Override
    public boolean cancelGroupBooking(String eventName, String groupId, int groupSize) {
        String query = "UPDATE GroupSale SET Confirmed = 'No' " +
                "WHERE GroupID = ? AND ShowID IN (SELECT ShowID FROM Shows WHERE Name = ?)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, groupId);
            stmt.setString(2, eventName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets alerts for shows with low ticket sales.
     * @return List of event names with fewer than 10 tickets sold
     */
    @Override
    public List<String> getLowSalesAlerts() {
        List<String> alerts = new ArrayList<>();
        String query = "SELECT s.Name " +
                "FROM Shows s " +
                "LEFT JOIN HeldSeats hs ON s.ShowID = hs.ShowID " +
                "LEFT JOIN TicketSales ts ON ts.InvoiceID IN (SELECT InvoiceID FROM Invoice WHERE Date = s.Date) " +
                "GROUP BY s.ShowID, s.Name " +
                "HAVING COALESCE(SUM(ts.Quantity), 0) < 10";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alerts.add(rs.getString("Name"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return alerts;
    }

    /**
     * Retrieves details for a specific film.
     * @param filmName the name of the film
     * @return FilmDetails object containing film information
     */
    @Override
    public FilmDetails getFilmDetails(String filmName) {
        FilmDetails details = new FilmDetails();
        String query = "SELECT Name, Certificate FROM Film WHERE Name = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, filmName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details.setFilmName(rs.getString("Name"));
                details.setGenre(rs.getString("Certificate"));
                details.setDurationMinutes(0);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return details;
    }

    /**
     * Gets client details by ID.
     * @param clientId the unique identifier of the client
     * @return ClientDTO containing all client information
     */
    @Override
    public ClientDTO getClientDetails(String clientId) {
        ClientDTO client = new ClientDTO();
        String query = "SELECT ClientID, ContactEmail, ContactName, StreetAddress, IsFriendOfLancaster " +
                "FROM Client WHERE ClientID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client.setClientId(rs.getString("ClientID"));
                client.setContactEmail(rs.getString("ContactEmail"));
                client.setContactName(rs.getString("ContactName"));
                client.setStreetAddress(rs.getString("StreetAddress"));
                client.setFriendOfLancaster(rs.getBoolean("IsFriendOfLancaster"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * Updates client contact information.
     * @param clientId the unique identifier of the client
     * @param contactEmail the new email address
     * @param contactName the new contact name
     * @param streetAddress the new street address
     * @return true if update was successful, false otherwise
     */
    @Override
    public boolean updateClientDetails(String clientId, String contactEmail, String contactName, String streetAddress) {
        String query = "UPDATE Client SET ContactEmail = ?, ContactName = ?, StreetAddress = ? WHERE ClientID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, contactEmail);
            stmt.setString(2, contactName);
            stmt.setString(3, streetAddress);
            stmt.setString(4, clientId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Books a room for a client meeting.
     * @param clientId the unique identifier of the client
     * @param date the date of the meeting
     * @param startTime the start time of the meeting
     * @param title the title/description of the meeting
     * @param location the room location
     * @return true if booking was successful, false otherwise
     */
    @Override
    public boolean bookRoomForClient(String clientId, LocalDate date, LocalTime startTime, String title, String location) {
        String query = "INSERT INTO Meeting (ClientID, Date, Time, Title, Location, Type) VALUES (?, ?, ?, ?, ?, 'Meeting')";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, clientId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setTime(3, java.sql.Time.valueOf(startTime));
            stmt.setString(4, title);
            stmt.setString(5, location);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generates an invoice for room usage.
     * @param clientId the unique identifier of the client
     * @param date the date of the invoice
     * @param cost the total cost
     * @return true if invoice generation was successful, false otherwise
     */
    @Override
    public boolean generateInvoiceForRoomUsage(String clientId, LocalDate date, double cost) {
        // First, create a FilmOrder (required for Invoice)
        String insertOrderQuery = "INSERT INTO FilmOrder (ClientID, TotalCost, Status) VALUES (?, ?, 'Confirmed')";
        String insertInvoiceQuery = "INSERT INTO Invoice (FilmOrderID, Date, Costs, Total, ClientID) " +
                "SELECT FilmOrderID, ?, ?, ?, ? FROM FilmOrder WHERE ClientID = ? ORDER BY FilmOrderID DESC LIMIT 1";

        try (PreparedStatement orderStmt = JDBC.getConnection().prepareStatement(insertOrderQuery);
             PreparedStatement invoiceStmt = JDBC.getConnection().prepareStatement(insertInvoiceQuery)) {
            // Insert FilmOrder
            orderStmt.setString(1, clientId);
            orderStmt.setDouble(2, cost);
            orderStmt.executeUpdate();

            // Insert Invoice
            invoiceStmt.setDate(1, java.sql.Date.valueOf(date));
            invoiceStmt.setDouble(2, cost);
            invoiceStmt.setDouble(3, cost);
            invoiceStmt.setString(4, clientId);
            invoiceStmt.setString(5, clientId);
            return invoiceStmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets the ticket price for a show.
     * @param showId the unique identifier of the show
     * @param price the new ticket price
     * @return true if update was successful, false otherwise
     */
    @Override
    public boolean setShowTicketPrice(String showId, double price) {
        String query = "UPDATE Shows SET Price = ? WHERE ShowID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setDouble(1, price);
            stmt.setString(2, showId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the current ticket price for a show.
     * @param showId the unique identifier of the show
     * @return the current ticket price
     */
    @Override
    public double getShowTicketPrice(String showId) {
        String query = "SELECT Price FROM Shows WHERE ShowID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, showId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Price");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Sets the discount rate for a show.
     * @param showId the unique identifier of the show
     * @param discount the new discount rate (0-1)
     * @return true if update was successful, false otherwise
     */
    @Override
    public boolean setShowDiscount(String showId, double discount) {
        String query = "UPDATE Shows SET Discount = ? WHERE ShowID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setDouble(1, discount);
            stmt.setString(2, showId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the current discount rate for a show.
     * @param showId the unique identifier of the show
     * @return the current discount rate (0-1)
     */
    @Override
    public double getShowDiscount(String showId) {
        String query = "SELECT Discount FROM Shows WHERE ShowID = ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, showId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Discount");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Schedules a new film screening.
     * @param filmId the unique identifier of the film
     * @param date the screening date
     * @param startTime the screening start time
     * @param price the ticket price
     * @return true if scheduling was successful, false otherwise
     */
    @Override
    public boolean scheduleFilmScreening(String filmId, LocalDate date, LocalTime startTime, double price) {
        String query = "INSERT INTO Screening (FilmID, Date, StartTime, Price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, filmId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setTime(3, java.sql.Time.valueOf(startTime));
            stmt.setDouble(4, price);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Arranges a venue tour for a client.
     * @param clientId the unique identifier of the client
     * @param date the tour date
     * @param startTime the tour start time
     * @param location the tour starting location
     * @return true if arrangement was successful, false otherwise
     */
    @Override
    public boolean arrangeTour(String clientId, LocalDate date, LocalTime startTime, String location) {
        String query = "INSERT INTO Meeting (ClientID, Date, Time, Title, Location, Type) VALUES (?, ?, ?, ?, ?, 'Tour')";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setString(1, clientId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setTime(3, java.sql.Time.valueOf(startTime));
            stmt.setString(4, "Tour for Client " + clientId);
            stmt.setString(5, location);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets room usage details for a date range.
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return List of RoomUsageDTO objects
     */
    @Override
    public List<RoomUsageDTO> getRoomUsageDetails(LocalDate startDate, LocalDate endDate) {
        List<RoomUsageDTO> roomUsages = new ArrayList<>();
        String query = "SELECT ClientID, Date, Time, Title, Location " +
                "FROM Meeting " +
                "WHERE Type = 'Meeting' AND Date BETWEEN ? AND ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoomUsageDTO usage = new RoomUsageDTO();
                usage.setClientId(rs.getString("ClientID"));
                usage.setDate(rs.getDate("Date").toLocalDate());
                usage.setStartTime(rs.getTime("Time").toLocalTime());
                usage.setTitle(rs.getString("Title"));
                usage.setLocation(rs.getString("Location"));
                roomUsages.add(usage);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return roomUsages;
    }

    /**
     * Gets film screening schedule for a date range
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return List of FilmScreeningDTO objects
     */
    @Override
    public List<FilmScreeningDTO> getFilmScreeningSchedule(LocalDate startDate, LocalDate endDate) {
        List<FilmScreeningDTO> screenings = new ArrayList<>();
        String query = "SELECT FilmID, Date, StartTime, Price " +
                "FROM Screening " +
                "WHERE Date BETWEEN ? AND ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FilmScreeningDTO screening = new FilmScreeningDTO();
                screening.setFilmId(rs.getString("FilmID"));
                screening.setDate(rs.getDate("Date").toLocalDate());
                screening.setStartTime(rs.getTime("StartTime").toLocalTime());
                screening.setPrice(rs.getDouble("Price"));
                screenings.add(screening);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    /**
     * Gets tour schedule for a date range.
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return List of TourDTO objects
     */
    @Override
    public List<TourDTO> getTourSchedule(LocalDate startDate, LocalDate endDate) {
        List<TourDTO> tours = new ArrayList<>();
        String query = "SELECT ClientID, Date, Time, Location " +
                "FROM Meeting " +
                "WHERE Type = 'Tour' AND Date BETWEEN ? AND ?";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TourDTO tour = new TourDTO();
                tour.setClientId(rs.getString("ClientID"));
                tour.setDate(rs.getDate("Date").toLocalDate());
                tour.setStartTime(rs.getTime("Time").toLocalTime());
                tour.setLocation(rs.getString("Location"));
                tours.add(tour);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tours;
    }

    /**
     * Gets all clients enrolled in the Friends of Lancaster program.
     * @return List of ClientDTO objects
     */
    @Override
    public List<ClientDTO> getFriendsOfLancasterClients() {
        List<ClientDTO> clients = new ArrayList<>();
        String query = "SELECT ClientID, ContactEmail, ContactName, StreetAddress, IsFriendOfLancaster " +
                "FROM Client WHERE IsFriendOfLancaster = TRUE";

        try (PreparedStatement stmt = JDBC.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClientDTO client = new ClientDTO();
                client.setClientId(rs.getString("ClientID"));
                client.setContactEmail(rs.getString("ContactEmail"));
                client.setContactName(rs.getString("ContactName"));
                client.setStreetAddress(rs.getString("StreetAddress"));
                client.setFriendOfLancaster(rs.getBoolean("IsFriendOfLancaster"));
                clients.add(client);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clients;
    }
}