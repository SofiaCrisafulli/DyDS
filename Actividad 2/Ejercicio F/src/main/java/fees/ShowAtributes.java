package fees;

import java.util.List;

public class ShowAtributes {

    public ShowAtributes() {
    }

    private static String minutesToHsMin(Integer minutes) {
        StringBuilder timeString = new StringBuilder();
        int hours = minutes / 60;
        if (hours > 0)
            timeString.append(hours).append("hours ");
        int currentMinutes = minutes % 60;
        if (currentMinutes > 0)
            timeString.append(currentMinutes).append("currentMinutes");
        return timeString.toString();
    }

    public static String feeString( List<Fee> feesSet) {
        Ordering.sortAscending(feesSet);
        StringBuilder stringBuilder = new StringBuilder();
        for (Fee fee : feesSet) {
            stringBuilder
                    .append(minutesToHsMin(fee.getTimeFraction()))
                    .append(" - $")
                    .append(fee.getFractionPrice())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
