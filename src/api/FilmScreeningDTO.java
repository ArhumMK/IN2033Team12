package api;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilmScreeningDTO {
    private String filmId;
    private LocalDate date;
    private LocalTime startTime;
    private double price;

    // Getters and setters
    public String getFilmId() { return filmId; }
    public void setFilmId(String filmId) { this.filmId = filmId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}