package ua.edu.zsmu.mfi.biology.pollen.view;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import java.util.Map;

import layout.PollenWidget;
import ua.edu.zsmu.mfi.biology.pollen.R;
import ua.edu.zsmu.mfi.biology.pollen.pollen.Pollen;

/**
 * Created by Andrey on 25.05.2017.
 */

public final class WidgetView {

    private final Context context;
    private final RemoteViews remoteViews;

    private AppWidgetManager appWidgetManager;
    private ComponentName watchWidget;

    public WidgetView(Context context, RemoteViews remoteViews) {
        this.context = context;
        this.remoteViews = remoteViews;
        this.appWidgetManager = AppWidgetManager.getInstance(context);
        this.watchWidget = new ComponentName(context, PollenWidget.class);
    }

    public void setForecast(Map<Integer, Pollen> forecast) {
        Pollen today = forecast.get(1);
        remoteViews.setTextViewText(R.id.today_level, "Рівень " + today.getLevel());
        remoteViews.setTextViewText(R.id.today_value, String.format("%.1f", today.getValue())+" од.");
        remoteViews.setInt(R.id.today_container, "setBackgroundResource", getDrawable(today.getLevel()));

        if (forecast.containsKey(2)) {
            Pollen tomorrow = forecast.get(2);
            remoteViews.setTextViewText(R.id.tomorrow_level, "Рівень " + tomorrow.getLevel());
            remoteViews.setTextViewText(R.id.tomorrow_value, String.format("%.1f", tomorrow.getValue())+" од.");
            remoteViews.setInt(R.id.tomorrow_container, "setBackgroundResource", getDrawable(tomorrow.getLevel()));
        } else {
            remoteViews.setTextViewText(R.id.tomorrow_level, "Рівень -");
            remoteViews.setTextViewText(R.id.tomorrow_value, String.format("%.1f", "0 од."));
            remoteViews.setInt(R.id.tomorrow_container, "setBackgroundResource", getDrawable(0));
        }

        if (forecast.containsKey(3)) {
            Pollen after = forecast.get(3);
            remoteViews.setTextViewText(R.id.after_level, "Рівень " + after.getLevel());
            remoteViews.setTextViewText(R.id.after_value, String.format("%.1f", after.getValue())+" од.");
            remoteViews.setInt(R.id.after_container, "setBackgroundResource", getDrawable(after.getLevel()));
        } else {
            remoteViews.setTextViewText(R.id.after_level, "Рівень -");
            remoteViews.setTextViewText(R.id.after_value, String.format("%.1f", "0 од."));
            remoteViews.setInt(R.id.after_container, "setBackgroundResource", getDrawable(0));
        }
        update();
    }

    public void setMessage(String message) {
        remoteViews.setTextColor(R.id.message, Color.GREEN);
        remoteViews.setTextViewText(R.id.message, message);
        update();
    }

    public void setError(String message) {
        remoteViews.setTextColor(R.id.message, Color.RED);
        remoteViews.setTextViewText(R.id.message, message);
        update();
    }

    public void setUpdated(String updated) {
        remoteViews.setTextViewText(R.id.updated, updated);
        update();
    }

    /**
     * Updates the mode
     * @param mode demo - for demo, on - for real-time data
     */
    public void setMode(String mode) {
        int color;
        String text;
        if (mode.equals("off")) {
            color = Color.RED;
            text = "Demo";
        } else if (mode.equals("on")) {
            color = Color.GREEN;
            text = "Real-time";
        } else throw new RuntimeException();
        remoteViews.setTextColor(R.id.appwidget_mode, color);
        remoteViews.setTextViewText(R.id.appwidget_mode, text);
        update();
    }

    private int getDrawable(int level) {
        switch (level) {
            case 0:
                return R.drawable.danger_unknown;
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

    private void update() {
        this.appWidgetManager.updateAppWidget(this.watchWidget, this.remoteViews);
    }
}
