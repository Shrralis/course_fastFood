package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class MealToFiliation extends Model {
    public Filiation filiation = null;
    public Meal meal = null;

    public MealToFiliation() {}
    @Override
    public MealToFiliation parse(ResultSet from, Connection connection) {
        try {
            filiation = new List<>(get("SELECT * FROM `filiations` WHERE `id` = " +
                    from.getInt("filiation") + ";", connection), Filiation.class, connection).get(0);
            meal = new List<>(get("SELECT * FROM `meals` WHERE `id` = " +
                    from.getInt("meal") + ";", connection), Meal.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Filiation getFiliation() {
        return filiation;
    }

    public void setFiliation(Filiation filiation) {
        this.filiation = filiation;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
