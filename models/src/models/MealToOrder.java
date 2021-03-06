package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class MealToOrder extends Model {
    public Order order = null;
    public Meal meal = null;

    public MealToOrder() {}
    @Override
    public MealToOrder parse(ResultSet from, Connection connection) {
        try {
            order = new List<>(get("SELECT * FROM `orders` WHERE `id` = " +
                    from.getInt("order") + ";", connection), Order.class, connection).get(0);
            meal = new List<>(get("SELECT * FROM `meals` WHERE `id` = " +
                    from.getInt("meal") + ";", connection), Meal.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
