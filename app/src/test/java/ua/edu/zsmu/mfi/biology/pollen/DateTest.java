package ua.edu.zsmu.mfi.biology.pollen;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andrey on 17.05.2017.
 */

public class DateTest {

    private static long timeStamp = 1495022400;

    @Test
    public void getDayTest() {
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        Date date = new Date(timeStamp*1000L);
        System.out.println(date);
        System.out.println(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear);
    }
}
