package api;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomUsageDTO {
    private String clientId;
    private LocalDate date;
    private LocalTime startTime;
    private String title;
    private String location;

    // Getters and setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}