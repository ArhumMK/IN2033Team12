package api;

public class ClientDTO {
    private String clientId;
    private String contactEmail;
    private String contactName;
    private String streetAddress;
    private boolean isFriendOfLancaster;

    // Getters and setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public boolean isFriendOfLancaster() { return isFriendOfLancaster; }
    public void setFriendOfLancaster(boolean isFriendOfLancaster) { this.isFriendOfLancaster = isFriendOfLancaster; }
}
