package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.edu.zsmu.mfi.biology.pollen.util.LocalFileDataProvider;

import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 17.05.2017.
 */

@RunWith(AndroidJUnit4.class)
public class LocalFileDataProviderInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String content = localFileDataProvider.getJSONNormConcentrationFileContent(context);
        Log.i("contents", content);
        assertTrue(!content.isEmpty());
    }
}
