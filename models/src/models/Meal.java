package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Meal extends Owner {
    public Double calorie = null;
    public Double weight = null;

    public Meal() {}
    @SuppressWarnings("unused")
    public Meal(ResultSet from) {
        parse(from);
    }
    @Override
    public Meal parse(ResultSet from) {
        super.parse(from);

        try {
            calorie = from.getDouble("calorie");
            weight = from.getDouble("weight");
        } catch (SQLException ignored) {}
        return this;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
