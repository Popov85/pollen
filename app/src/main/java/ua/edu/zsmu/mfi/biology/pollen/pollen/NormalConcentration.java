package ua.edu.zsmu.mfi.biology.pollen.pollen;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 17.05.2017.
 */

public final class NormalConcentration {

    private Date updated;

    //Key - day of the year, value - normal pollen concentration of this day
    private Map<Integer, Double> storage = new HashMap<>();

    public Date getUpdated() {
        return updated;
    }

    public Map<Integer, Double> getStorage() {
        return storage;
    }

    public NormalConcentration(Date updated, Map<Integer, Double> storage) {
        this.updated = updated;
        this.storage = storage;
    }

    public  double getNormalConcentration(int day) {
        return storage.get(day);
    }


    @Override
    public String toString() {
        return "NormalConcentration{" +
                "updated=" + updated +
                ", storage=" + storage +
                '}';
    }
}
