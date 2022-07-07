package fees;

import java.util.ArrayList;
import java.util.List;

public class Fee {

    private int timeFraction;
    private float fractionPrice;
    private List<Discount> discountList;

    public Fee(int timeFraction, float fractionPrice) {
        this.timeFraction = timeFraction;
        this.fractionPrice = fractionPrice;
        discountList = new ArrayList<>();
    }

    public int getTimeFraction() {
        return timeFraction;
    }

    public float getFractionPrice() {
        float resultFinal = fractionPrice;
        for (Discount discount : discountList) {
            resultFinal = discount.applyDiscount(resultFinal);
        }
        return resultFinal;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public void addDiscount(Discount discount){
        discountList.add(discount);
    }
}
