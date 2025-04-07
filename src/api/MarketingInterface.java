package api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface MarketingInterface {

    Map<String, Integer> getPastVisitInformation(String showId);
    String getTicketSalesTrends(String eventName, LocalDate eventDate, String eventTime);
    List<LocalDate> getHighTrafficDates(String showId);
    List<LocalDate> getHighTrafficDates(String showId, String timeframe);
    Map<String, Integer> getPreSaleTrends(String showId, String ticketType);
    Map<String, String> getTicketCategories(String showId);
    GroupBookingDTO getGroupBookingDetails(String eventName, LocalDate eventDate, String eventTime, String groupId);
    List<String> getCompaniesBookedForEvent(String eventName, LocalDate eventDate, String eventTime);
    List<String> getEventsNeedingPromotion();
    String generatePromotionImpactReport(String eventName, LocalDate eventDate, String eventTime);
    List<BookingDTO> getConfirmedBookings(String eventName, LocalDate eventDate, String eventTime);
    int getUsedDiscountedTickets(String eventName, LocalDate eventDate, String eventTime);
    boolean confirmGroupBooking(String eventName, String groupId, int groupSize);
    boolean cancelGroupBooking(String eventName, String groupId, int groupSize);
    List<String> getLowSalesAlerts();
    FilmDetails getFilmDetails(String filmName);

    // Manage client details
    ClientDTO getClientDetails(String clientId);
    boolean updateClientDetails(String clientId, String contactEmail, String contactName, String streetAddress);

    // Book rooms for clients and bill for usage
    boolean bookRoomForClient(String clientId, LocalDate date, LocalTime startTime, String title, String location);
    boolean generateInvoiceForRoomUsage(String clientId, LocalDate date, double cost);

    // Confirm the agreed price for a show’s tickets and set seat prices
    boolean setShowTicketPrice(String showId, double price);
    double getShowTicketPrice(String showId);

    // Confirm maximum discount for a show and set discount rates
    boolean setShowDiscount(String showId, double discount);
    double getShowDiscount(String showId);

    // Communicate seat prices and discounts
    // Adjust getTicketCategories to include price and discount
    // Already exists: Map<String, String> getTicketCategories(String showId);

    // Schedule film screenings
    boolean scheduleFilmScreening(String filmId, LocalDate date, LocalTime startTime, double price);

    // Arrange Tours (using Meeting table with Type = 'Tour')
    boolean arrangeTour(String clientId, LocalDate date, LocalTime startTime, String location);

    // Communicate room usage and needs to other teams
    List<RoomUsageDTO> getRoomUsageDetails(LocalDate startDate, LocalDate endDate);

    // Communicate Film and Tour schedule to other teams
    List<FilmScreeningDTO> getFilmScreeningSchedule(LocalDate startDate, LocalDate endDate);
    List<TourDTO> getTourSchedule(LocalDate startDate, LocalDate endDate);

    // Update Box Office about customers who are Friends of Lancaster’s
    List<ClientDTO> getFriendsOfLancasterClients();
}