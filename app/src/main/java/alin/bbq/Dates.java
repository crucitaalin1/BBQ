package alin.bbq;

import java.util.Calendar;

/**
 * Created by alinp on 08/08/2017.
 */

public class Dates {

    String dates;

    public Dates() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                dates = "TUESDAY WEDNESDAY THURSDAY FRIDAY";
                break;
            case Calendar.TUESDAY:
                dates = "WEDNESDAY THURSDAY FRIDAY SATURDAY";
                break;
            case Calendar.WEDNESDAY:
                dates = "THURSDAY FRIDAY SATURDAY SUNDAY";
                break;
            case Calendar.THURSDAY:
                dates = "FRIDAY SATURDAY SUNDAY MONDAY";
                break;
            case Calendar.FRIDAY:
                dates = "SATURDAY SUNDAY MONDAY TUESDAY";
                break;
            case Calendar.SATURDAY:
                dates = "SUNDAY MONDAY TUESDAY WEDNESDAY";
                break;
            case Calendar.SUNDAY:
                dates = "MONDAY TUESDAY WEDNESDAY THURSDAY";
        }
    }

    public String getDates()
    {
        return dates;
    }
}
