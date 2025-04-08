package api;

/**
 * Data Transfer Object (DTO) representing film details in the cinema management system.
 *
 * <p>This class encapsulates essential information about films including:
 * <ul>
 *   <li>Film title
 *   <li>Duration in minutes
 *   <li>Genre classification
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances are used to transfer film data between application layers.
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * FilmDetails details = new FilmDetails();
 * details.setFilmName("Inception");
 * details.setDurationMinutes(148);
 * details.setGenre("Sci-Fi");
 * </pre>
 */
public class FilmDetails {
    /** The title of the film.*/
    private String filmName;

    /** The duration of the film in minutes.*/
    private int durationMinutes;

    /**
     * The primary genre classification of the film.
     * Examples: "Action", "Comedy", "Drama", "Documentary".
     */
    private String genre;

    /**
     * Gets the title of the film.
     * @return the film title as a String
     */
    public String getFilmName() {
        return filmName;
    }

    /**
     * Sets the title of the film.
     * @param filmName the new film title
     */
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }


    /**
     * Gets the duration of the film in minutes.
     * @return the duration in minutes as a positive integer
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Sets the duration of the film in minutes.
     * @param durationMinutes the new duration in minutes
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * Gets the genre classification of the film.
     * @return the film genre as a String
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre classification of the film.
     * @param genre the new genre classification
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
}