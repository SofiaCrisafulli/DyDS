package fees;

import java.util.Comparator;
import java.util.List;

public class Ordering {

    public Ordering() {
    }

    protected static void sortAscending(List<Fee> feesSet) {
        feesSet.sort(new Comparator<Fee>() {
            @Override
            public int compare(Fee fee1, Fee fee2) {
                return fee1.getTimeFraction() - fee2.getTimeFraction();
            }
        });
    }

    protected static void sortDescending(List<Fee> feesSet) {
        feesSet.sort(new Comparator<Fee>() {
            @Override
            public int compare(Fee fee1, Fee fee2) {
                return fee2.getTimeFraction() - fee1.getTimeFraction();
            }
        });
    }
}
