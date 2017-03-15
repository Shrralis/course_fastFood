package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class MealToFillation extends Model {
    public Fillation fillation = null;
    public Meal meal = null;

    public MealToFillation() {}
    @Override
    public MealToFillation parse(ResultSet from, Connection connection) {
        try {
            fillation = ParseUtils.parseViaReflection(new Fillation(), get("SELECT * FROM `fillations` WHERE `id` = " +
                    from.getInt("fillation") + ";", connection));
            meal = ParseUtils.parseViaReflection(new Meal(), get("SELECT * FROM `meals` WHERE `id` = " +
                    from.getInt("meal") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Fillation getFillation() {
        return fillation;
    }

    public void setFillation(Fillation fillation) {
        this.fillation = fillation;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
