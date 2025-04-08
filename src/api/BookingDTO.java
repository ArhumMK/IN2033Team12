package api;

/**
 * Data Transfer Object (DTO) representing a booking in the cinema management system.
 *
 * <p>This class encapsulates the essential booking information including:
 * <ul>
 *   <li>A unique booking identifier
 *   <li>The patron who made the booking
 *   <li>The total cost of the booking
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances of this class are typically used to transfer booking
 * data between different layers of the application.
 */
public class BookingDTO {
    /** The unique identifier for this booking */
    private int bookingId;

    /** The identifier of the patron who made this booking */
    private int patronId;

    /** The total cost of the booking in local currency */
    private double totalCost;

    // Getter and Setter for bookingId
    /**
     * Gets the unique identifier for this booking.
     * @return the booking ID
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets the unique identifier for this booking.
     * @param bookingId the new booking ID
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    // Getter and Setter for patronId
    /**
     * Gets the identifier of the patron who made this booking.
     * @return the patron ID
     */
    public int getPatronId() {
        return patronId;
    }

    /**
     * Sets the identifier of the patron who made this booking.
     * @param patronId the new patron ID
     */
    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    // Getter and Setter for totalCost
    /**
     * Gets the total cost of the booking.
     * @return the total cost in local currency
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost of the booking.
     * @param totalCost the new total cost in local currency
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
