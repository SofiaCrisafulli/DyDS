package fees;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ComputeFee{
    private static ComputeFee instance;
    private List<Fee> feeList = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private float price;
    private int minutesToPrice;


    ComputeFee() {
    }

    public float finalPrice(int minutes) {
        Ordering.sortDescending(feeList);
        price = 0;
        minutesToPrice = minutes;
        feeInOrder();
        if (thereAreMinutesLeft())
            price += feeList.get(feeList.size() - 1).getFractionPrice();
        Ordering.sortAscending(feeList);
        for (Fee fee : feeList) {
            int checkPriceForFullTime = (int) Math.ceil((float) minutes / (float) fee.getTimeFraction());
            float salary = fee.getFractionPrice() * checkPriceForFullTime;
            if (checkPriceForFullTime > 0 && salary < price)
                price = salary;
        }
        return price;
    }

    public void addFees(int minutes, float price) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Fee fee = new Fee(minutes, price);
        Discount discount = new DiscountImpl();
        if (isMonday(day))
           fee.addDiscount(discount);
        feeList.add(fee);
    }

    private int feeInOrder() {
        for (Fee fee : feeList) {
            if (minutesToPrice / fee.getTimeFraction() > 0) {
                int normalTime = minutesToPrice / fee.getTimeFraction();
                price += normalTime * fee.getFractionPrice();
                minutesToPrice -= normalTime * fee.getTimeFraction();
            }
        }
        return minutesToPrice;
    }

    private boolean thereAreMinutesLeft() {
        return minutesToPrice > 0;
    }

    public static ComputeFee getInstance() {
        if (instance == null) {
            instance = new ComputeFee();
        }
        return instance;
    }

    public void setDate(Date date) {
        calendar.setTime(date);
    }

    private boolean isMonday(int day) {
        return day == Calendar.MONDAY;
    }

    public void resetSingleton() {
        feeList.clear();
        calendar = Calendar.getInstance();
    }

    public List<Fee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }

    public String callFeeString(){
        return ShowAtributes.feeString(feeList);
    }
}
