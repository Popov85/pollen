package ua.edu.zsmu.mfi.biology.pollen.weather;

/**
 * Created by Andrey on 23.05.2017.
 */

public final class Pollen {

    private double value;
    private int level;

    public double getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public Pollen(double value, int level) {
        this.value = value;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Pollen{" +
                "value=" + value +
                ", level=" + level +
                '}';
    }
}
