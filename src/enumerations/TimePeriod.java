package enumerations;

import java.util.HashMap;
import java.util.Map;

public enum TimePeriod {
    ONE(1, 30),
    THREE(3, 90),
    SIX(6, 180);

    private static final Map<Integer, TimePeriod> _map = new HashMap<>();

    static {
        for (TimePeriod timePeriod : TimePeriod.values()) {
            _map.put(timePeriod.getPeriodKey(), timePeriod);
        }
    }

    private int periodKey;
    private int periodValue;

    TimePeriod(int periodKey, int periodValue) {
        this.periodKey = periodKey;
        this.periodValue = periodValue;
    }

    public static TimePeriod getTimePeriod(int value) {
        return _map.get(value);
    }

    public int getPeriodValue() {
        return periodValue;
    }

    public int getPeriodKey() {
        return periodKey;
    }


    public static void main(String[] args) {
        String one = "1";
        TimePeriod.valueOf("ONE");
    }
}
