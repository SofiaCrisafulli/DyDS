package fees;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ComputeFee {

  //This method calculates the final price, using the the fee data in the list
  public float price(int minutes) {

    // sort descending
    feesSet.sort(new Comparator<Fee>() {
      @Override public int compare(Fee o1, Fee o2) {
        return o2.getTimeFraction() - o1.getTimeFraction();
      }
    });

    float price = 0;

    int minutesToPrice = minutes;

    // apply best fee in order. For example, 2hs 15min will apply 2 x 1hr fee + 1 x 15min fee
    for (Fee fee : feesSet) {
      if (minutesToPrice / fee.getTimeFraction() > 0) {
        int units = minutesToPrice / fee.getTimeFraction();
        price += units * fee.getFractionPrice();
        minutesToPrice -= units * fee.getTimeFraction();
      }
    }

    // Leftover minutes. For example, 1hs 3min, minutesToPrice = 3.
    // We need to charge an extra minimum fee (last one in the list)
    if (minutesToPrice > 0) {
      price += feesSet.get(feesSet.size() - 1).getFractionPrice();
    }


    // fix overprice. For example, 11hs = $660, but 12hs = $600

    // sort ascending
    feesSet.sort(new Comparator<Fee>() {
      @Override public int compare(Fee o1, Fee o2) {
        return o1.getTimeFraction() - o2.getTimeFraction();
      }
    });

    //This part was made by Estefanio, we dont get it but it totally works!!!
    for (Fee fee : feesSet) {

      // check price for full time fee. Example, 2hs 10min, check 3hs price
      int t = (int) Math.ceil((float)minutes / (float) fee.getTimeFraction());
      float p = fee.getFractionPrice() * t;

      if (t > 0 && p < price) price = p;
    }

    return price;
  }

  private static ComputeFee instance;
  
  private ComputeFee() { }

  public static ComputeFee getInstance() {
    if (instance == null) {
      instance =  new ComputeFee();
    }
    return instance;
  }

  private List<Fee> feesSet = new ArrayList<>();

  Calendar c = Calendar.getInstance();

  public void setDate(Date d){
    c.setTime(d);
  }


  public void TypeFeesAdd(int minutes, float price) {
    int d = c.get(Calendar.DAY_OF_WEEK);

    //Checks whether the day is not Monday to add the normal fee, else in the weekend use the discounted rate
    if (!(d == Calendar.MONDAY)) {
      feesSet.add(new Fee(minutes, price));
    } else {
      feesSet.add(new OtherFee(minutes, price));
    }
  }


  public String feeString() {
    // sort ascending
    feesSet.sort(new Comparator<Fee>() {
      @Override public int compare(Fee o1, Fee o2) {
        return o1.getTimeFraction() - o2.getTimeFraction();
      }
    });

    StringBuilder rs = new StringBuilder();
    for (Fee fee : feesSet) {

      rs
          .append(minutesToHsMin(fee.getTimeFraction()))
          .append(" - $")
          .append(fee.getFractionPrice())
          .append("\n");

    }
    return rs.toString();
  }

  private String minutesToHsMin(Integer minutes) {

    StringBuilder timeString = new StringBuilder();

    int hs = minutes / 60;

    if (hs > 0) {
      timeString.append(hs).append("hs ");
    }

    //Lets get those leftover minutes!
    int min = minutes % 60;

    if (min > 0) {
      timeString.append(min).append("min");
    }

    return timeString.toString();
  }


  public void resetSingleton() {
    feesSet.clear();
    c = Calendar.getInstance();
  }
}
