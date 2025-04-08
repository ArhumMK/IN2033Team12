package api;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) representing a venue tour in the cinema management system.
 *
 * <p>This class encapsulates all essential information about scheduled tours including:
 * <ul>
 *   <li>Client identification
 *   <li>Tour date and time
 *   <li>Tour location details
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances are used to transfer tour data between different
 * layers of the application and for API responses.
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * TourDTO tour = new TourDTO();
 * tour.setClientId("CLIENT-123");
 * tour.setDate(LocalDate.of(2023, 12, 15));
 * tour.setStartTime(LocalTime.of(10, 0));
 * tour.setLocation("Main Lobby");
 * </pre>
 *
 * @see LocalDate
 * @see LocalTime
 */
public class TourDTO {
    /** Unique identifier of the client requesting the tour.*/
    private String clientId;

    /** Scheduled date of the tour.*/
    private LocalDate date;

    /** Scheduled start time of the tour.*/
    private LocalTime startTime;

    /** Physical starting location of the tour.*/
    private String location;

    /**
     * Gets the client identifier who requested the tour.
     * @return the client ID as a String
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the client identifier who requested the tour.
     * @param clientId the new client ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the scheduled date of the tour.
     * @return the tour date as LocalDate
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the scheduled date of the tour.
     * @param date the new tour date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the scheduled start time of the tour.
     * @return the start time as LocalTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the scheduled start time of the tour.
     * @param startTime the new start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the starting location of the tour.
     * @return the tour location as a String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the starting location of the tour
     * @param location the new tour location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
