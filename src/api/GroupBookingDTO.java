package api;

/**
 * Data Transfer Object (DTO) representing a group booking in the cinema management system.
 *
 * <p>This class encapsulates all essential information about a group booking including:
 * <ul>
 *   <li>Group size (number of attendees)
 *   <li>Company/organization details
 *   <li>Contact information for the booking
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances are used to transfer group booking data between
 * different layers of the application and for API responses.
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * GroupBookingDTO booking = new GroupBookingDTO();
 * booking.setGroupSize(25);
 * booking.setCompanyName("Acme Corporation");
 * booking.setContactName("John Smith");
 * booking.setContactEmail("jsmith@acme.com");
 * </pre>
 */
public class GroupBookingDTO {
    /** The number of attendees in the group.*/
    private int groupSize;

    /** The name of the company or organization making the booking.*/
    private String companyName;

    /** The full name of the primary contact person for the booking.*/
    private String contactName;

    /** The email address of the primary contact person.*/
    private String contactEmail;

    // Getter and Setter for groupSize
    /**
     * Gets the number of attendees in the group.
     * @return the group size as a positive integer
     */
    public int getGroupSize() {
        return groupSize;
    }

    /**
     * Sets the number of attendees in the group.
     * @param groupSize the new group size
     */
    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    // Getter and Setter for companyName
    /**
     * Gets the name of the company or organization.
     * @return the company name as a String
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the company or organization.
     * @param companyName the new company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // Getter and Setter for contactName
    /**
     * Gets the full name of the primary contact person.
     * @return the contact name as a String
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the full name of the primary contact person.
     * @param contactName the new contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    // Getter and Setter for contactEmail
    /**
     * Gets the email address of the primary contact person.
     * @return the contact email as a String
     */
    public String getContactEmail() {
        return contactEmail;
    }


    /**
     * Sets the email address of the primary contact person.
     * @param contactEmail the new contact email
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}