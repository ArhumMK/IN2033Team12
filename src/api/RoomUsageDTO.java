package api;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) representing room usage in the cinema management system.
 *
 * <p>This class encapsulates all essential information about room bookings including:
 * <ul>
 *   <li>Client identification
 *   <li>Booking date and time
 *   <li>Event title and location
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances are used to transfer room usage data between
 * different layers of the application.
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * RoomUsageDTO usage = new RoomUsageDTO();
 * usage.setClientId("CLIENT-001");
 * usage.setDate(LocalDate.of(2023, 12, 15));
 * usage.setStartTime(LocalTime.of(14, 30));
 * usage.setTitle("Quarterly Planning Meeting");
 * usage.setLocation("Conference Room A");
 * </pre>
 *
 * @see LocalDate
 * @see LocalTime
 */
public class RoomUsageDTO {
    /** Unique identifier of the client who booked the room.*/
    private String clientId;

    /** Date when the room is booked.*/
    private LocalDate date;

    /** Scheduled start time of the room booking.*/
    private LocalTime startTime;

    /** Title or description of the booking.*/
    private String title;

    /** Physical location of the booked room.*/
    private String location;

    /**
     * Gets the client identifier who booked the room.
     * @return the client ID as a String
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the client identifier who booked the room.
     * @param clientId the new client ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the date of the room booking.
     * @return the booking date as LocalDate
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the room booking.
     * @param date the new booking date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the start time of the room booking.
     * @return the start time as LocalTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the room booking.
     * @param startTime the new start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the title/description of the booking.
     * @return the booking title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title/description of the booking.
     * @param title the new booking title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the physical location of the booking.
     * @return the room location as a String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the physical location of the booking.
     * @param location the new room location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}