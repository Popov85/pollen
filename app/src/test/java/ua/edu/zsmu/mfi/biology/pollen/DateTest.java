package ua.edu.zsmu.mfi.biology.pollen;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrey on 17.05.2017.
 */

public class DateTest {

    private static long timeStamp = 1495022400;

/*    @Test
    public void getDayTest() {
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        Date date = new Date(timeStamp*1000L);
        System.out.println(date);
        System.out.println(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear);
    }*/

    @Test
    public void getDayTest2() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Date d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2017-05-22 00:00:00");
        System.out.println("d= " + d);
        calendar.setTime(d);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        assertEquals(22, dayOfMonth);
        //System.out.println(dayOfMonth);
    }
}
