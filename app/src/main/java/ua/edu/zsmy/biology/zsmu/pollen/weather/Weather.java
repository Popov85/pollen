package ua.edu.zsmy.biology.zsmu.pollen.weather;

import java.util.Date;

/**
 * Created by Andrey on 18.05.2017.
 */

public class Weather {

    // Date and time for which the weather is forecasted
    private Date dateTime;

    // Atmospheric pressure in mm of mercury
    private double pressure;
    // Wind velocity, m/s
    private double wind;
    // Amount of precipitation for the last 3 hours, mm
    private double rain;
    // Humidity, %
    private double humidity;

    public Date getDateTime() {
        return dateTime;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWind() {
        return wind;
    }

    public double getRain() {
        return rain;
    }

    public double getHumidity() {
        return humidity;
    }

    public Weather(Date dateTime, double pressure, double wind, double rain, double humidity) {
        this.dateTime = dateTime;
        this.pressure = pressure;
        this.wind = wind;
        this.rain = rain;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "dateTime=" + dateTime +
                ", pressure=" + pressure +
                ", wind=" + wind +
                ", rain=" + rain +
                ", humidity=" + humidity +
                '}';
    }
}
