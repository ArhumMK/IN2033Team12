package api;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) representing a film screening in the cinema management system.
 *
 * <p>This class encapsulates all essential information about a film screening including:
 * <ul>
 *   <li>The film being shown
 *   <li>Screening date and time
 *   <li>Ticket price information
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances are used to transfer screening data between different
 * layers of the application and for API responses.
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * FilmScreeningDTO screening = new FilmScreeningDTO();
 * screening.setFilmId("FLM-001");
 * screening.setDate(LocalDate.of(2023, 12, 15));
 * screening.setStartTime(LocalTime.of(18, 30));
 * screening.setPrice(12.50);
 * </pre>
 *
 * @see LocalDate
 * @see LocalTime
 */
public class FilmScreeningDTO {
    /** Unique identifier for the film being screened.*/
    private String filmId;

    /** Date when the screening occurs.*/
    private LocalDate date;

    /** Scheduled start time of the screening.*/
    private LocalTime startTime;

    /** Base ticket price for the screening.*/
    private double price;

    /**
     * Gets the unique identifier of the film being screened.
     * @return the film ID as a String
     */
    public String getFilmId() { return filmId; }

    /**
     * Sets the unique identifier of the film being screened.
     * @param filmId the new film ID
     */
    public void setFilmId(String filmId) { this.filmId = filmId; }

    /**
     * Gets the date of the screening.
     * @return the screening date as LocalDate
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets the date of the screening.
     * @param date the new screening data
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Gets the scheduled start time of the screening.
     * @return the start time as LocalTime
     */
    public LocalTime getStartTime() { return startTime; }

    /**
     * Sets the scheduled start time of the screening.
     * @param startTime the new start time
     */
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    /**
     * Gets the base ticket price for the screening.
     * @return the ticket price as a positive double
     */
    public double getPrice() { return price; }

    /**
     * Sets the base ticket price for the screening.
     * @param price the new ticket price
     */
    public void setPrice(double price) { this.price = price; }
}