package api;

/**
 * Data Transfer Object (DTO) representing a client in the cinema management system.
 *
 * <p>This class encapsulates all essential client information including:
 * <ul>
 *   <li>Client identification details
 *   <li>Contact information
 *   <li>Membership status in the Friends of Lancaster program
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances of this class are typically used to transfer client
 * data between different layers of the application.
 */
public class ClientDTO {
    /** Unique identifier for the client */
    private String clientId;

    /** Email address for contacting the client */
    private String contactEmail;

    /** Full name of the client's primary contact */
    private String contactName;

    /** Physical street address of the client */
    private String streetAddress;

    /** Flag indicating membership in Friends of Lancaster program */
    private boolean isFriendOfLancaster;

    // Getters and setters
    /**
     * Gets the unique identifier for this client.
     * @return the client ID as a String
     */
    public String getClientId() { return clientId; }

    /**
     * Sets the unique identifier for this client.
     * @param clientId the new client ID
     */
    public void setClientId(String clientId) { this.clientId = clientId; }

    /**
     * Gets the email address for contacting this client.
     * @return the client's email address
     */
    public String getContactEmail() { return contactEmail; }

    /**
     * Sets the email address for contacting this client.
     * @param contactEmail the new email address
     */
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    /**
     * Gets the full name of the client's primary contact.
     * @return the contact name
     */
    public String getContactName() { return contactName; }


    /**
     * Sets the full name of the client's primary contact.
     * @param contactName the new contact name
     */
    public void setContactName(String contactName) { this.contactName = contactName; }

    /**
     * Gets the physical street address of the client.
     * @return the street address
     */
    public String getStreetAddress() { return streetAddress; }

    /**
     * Sets the physical street address of the client.
     * @param streetAddress the new street address
     */
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    /**
     * Checks if the client is a member of the Friends of Lancaster program.
     * @return true if the client is a member, false otherwise
     */
    public boolean isFriendOfLancaster() { return isFriendOfLancaster; }

    /**
     * Sets the membership status for the Friends of Lancaster program.
     * @param isFriendOfLancaster true to mark as member, false otherwise
     */
    public void setFriendOfLancaster(boolean isFriendOfLancaster) { this.isFriendOfLancaster = isFriendOfLancaster; }
}
