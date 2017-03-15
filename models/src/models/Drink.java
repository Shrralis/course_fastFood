package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Drink extends Owner {
    public Integer volume = null;
    public Double calorie = null;

    public Drink() {}
    @SuppressWarnings("unused")
    public Drink(ResultSet from) {
        parse(from);
    }
    @Override
    public Drink parse(ResultSet from) {
        super.parse(from);

        try {
            volume = from.getInt("volume");
            calorie = from.getDouble("calorie");
        } catch (SQLException ignored) {}
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }
}
