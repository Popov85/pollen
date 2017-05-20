package ua.edu.zsmu.mfi.biology.pollen.demo;

import android.widget.RemoteViews;

import java.util.Random;

import ua.edu.zsmu.mfi.biology.pollen.R;

/**
 * Created by Andrey on 17.05.2017.
 */

public final class RandomDemo {

    public void randomlyUpdate(RemoteViews views) {

        Random rd = new Random();
        int level = rd.nextInt(5);
        views.setTextViewText(R.id.today_level, "Рівень " + level);
        try {
            views.setInt(R.id.today_container, "setBackgroundResource", getDrawable(level));
        } catch (Exception e) {
            e.printStackTrace();
            views.setInt(R.id.today_container, "setBackgroundResource", R.drawable.danger_1);
        }

        level = rd.nextInt(5);
        views.setTextViewText(R.id.tomorrow_level, "Рівень " + level);
        try {
            views.setInt(R.id.tomorrow_container, "setBackgroundResource", getDrawable(level));
        } catch (Exception e) {
            e.printStackTrace();
            views.setInt(R.id.tomorrow_container, "setBackgroundResource", R.drawable.danger_1);
        }

        level = rd.nextInt(5);
        views.setTextViewText(R.id.after_level, "Рівень " + level);
        try {
            views.setInt(R.id.after_container, "setBackgroundResource", getDrawable(level));
        } catch (Exception e) {
            e.printStackTrace();
            views.setInt(R.id.after_container, "setBackgroundResource", R.drawable.danger_1);
        }

    }

    private int getDrawable(int level) {
        switch (level) {
            case 1:
                return R.drawable.danger_1;
            case 2:
                return R.drawable.danger_2;
            case 3:
                return R.drawable.danger_3;
            case 4:
                return R.drawable.danger_4;
            case 5:
                return R.drawable.danger_5;
        }
        throw new RuntimeException();
    }

}
