package api;

/**
 * Data Transfer Object (DTO) representing a discount policy in the cinema management system.
 *
 * <p>This class encapsulates the essential information about a discount policy including:
 * <ul>
 *   <li>A unique discount identifier
 *   <li>The discount percentage to be applied
 * </ul>
 *
 * <p>The class follows standard JavaBean conventions with getter and setter methods
 * for all properties. Instances of this class are typically used to transfer discount
 * policy information between different layers of the application.
 */
public class DiscountPolicyDTO {
    /** The unique identifier for this discount policy.*/
    private String discountId;

    /** The discount percentage represented as a decimal value between 0 and 1.*/
    private double discountPercentage;

    // Getter and Setter for discountId
    /**
     * Gets the unique identifier for this discount policy.
     * @return the discount policy ID as a String
     */
    public String getDiscountId() {
        return discountId;
    }

    /**
     * Sets the unique identifier for this discount policy.
     * @param discountId the new discount policy ID
     */
    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    // Getter and Setter for discountPercentage
    /**
     * Gets the discount percentage as a decimal value.
     * @return the discount percentage (0 to 1)
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Sets the discount percentage.
     * @param discountPercentage the new discount percentage (0 to 1)
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
