package ua.edu.zsmy.biology.zsmu.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 17.05.2017.
 */

@RunWith(AndroidJUnit4.class)
public class LocalFileNormalPollenConcentrationDataProviderInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        LocalFileNormalPollenConcentrationDataProvider localFileNormalPollenConcentrationDataProvider = new LocalFileNormalPollenConcentrationDataProvider();
        String content = localFileNormalPollenConcentrationDataProvider.getJSONFileContent(context);
        Log.i("contents", content);
        assertTrue(!content.isEmpty());
    }
}
