package fees;

public class DiscountImpl implements Discount{

    public float applyDiscount(float fractionPrice) {
        return fractionPrice * 0.8f;
    }
}
