public class FilmDetails {
    // The title of the film
    private String title;

    // Duration of the film (e.g., "2h 15m")
    private String duration;

    // The venue where the film is being shown
    private String venue;

    // Scheduling status of the film (e.g., "Scheduled", "Postponed")
    private String schedulingStatus;

    // Name of the film's director
    private String director;

    // Genre of the film (e.g., "Drama", "Comedy")
    private String genre;

    // Release date of the film in string format (e.g., "2023-10-01")
    private String releaseDate;

    // Content rating of the film (e.g., "PG-13", "R")
    private String rating;

    /**
     * Default constructor for FilmDetails.
     */
    public FilmDetails() {
    }

    /**
     * Parameterized constructor to create a FilmDetails object with all attributes.
     * 
     * @param title Title of the film
     * @param duration Duration of the film
     * @param venue Venue where the film is shown
     * @param schedulingStatus Status of the film scheduling
     * @param director Director of the film
     * @param genre Genre of the film
     * @param releaseDate Release date of the film
     * @param rating Film rating (e.g., age or content)
     */
    public FilmDetails(String title, String duration, String venue, String schedulingStatus,
                       String director, String genre, String releaseDate, String rating) {
        this.title = title;
        this.duration = duration;
        this.venue = venue;
        this.schedulingStatus = schedulingStatus;
        this.director = director;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    // Getter and Setter methods for all attributes

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSchedulingStatus() {
        return schedulingStatus;
    }

    public void setSchedulingStatus(String schedulingStatus) {
        this.schedulingStatus = schedulingStatus;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Returns a string representation of the FilmDetails object.
     * 
     * @return Human-readable string with all film details.
     */
    @Override
    public String toString() {
        return "FilmDetails{" +
                "title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", venue='" + venue + '\'' +
                ", schedulingStatus='" + schedulingStatus + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
