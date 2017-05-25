package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

import ua.edu.zsmu.mfi.biology.pollen.pollen.NormalConcentration;
import ua.edu.zsmu.mfi.biology.pollen.pollen.NormalPollenConcentrationDataProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Andrey on 18.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class NormalPollenConcentrationDataProviderInstrumentedTest {

    private static final String LAST_UPDATED = "2017-05-17";

    private static final Integer TEST_DAY = 214;

    private static final Double EXPECTED_CONCENTRATION = 78.8841583827862d;

    private static final Double DELTA = 0.001d;

    @Test
    public void shouldCreateNormalConcentrationSmoothly() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        NormalPollenConcentrationDataProvider normalPollenConcentrationDataProvider = new NormalPollenConcentrationDataProvider();
        NormalConcentration normalConcentration = normalPollenConcentrationDataProvider.getDataFromLocalFile(context);
        Log.i("normalConcentration", normalConcentration.toString());
        assertNotNull(normalConcentration);
        Date updatedDataExpected = new SimpleDateFormat("yyyy-MM-dd").parse(LAST_UPDATED);
        assertEquals(updatedDataExpected, normalConcentration.getUpdated());
        assertEquals(EXPECTED_CONCENTRATION, normalConcentration.getNormalConcentration(TEST_DAY), DELTA);

    }
}
