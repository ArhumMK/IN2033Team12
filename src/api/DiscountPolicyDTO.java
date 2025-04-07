package api;

public class DiscountPolicyDTO {
    private String discountId;
    private double discountPercentage;

    // Getter and Setter for discountId
    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    // Getter and Setter for discountPercentage
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
