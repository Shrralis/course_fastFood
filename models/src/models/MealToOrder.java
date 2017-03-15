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
            order = ParseUtils.parseViaReflection(new Order(), get("SELECT * FROM `orders` WHERE `id` = " +
                    from.getInt("order") + ";", connection));
            meal = ParseUtils.parseViaReflection(new Meal(), get("SELECT * FROM `meals` WHERE `id` = " +
                    from.getInt("meal") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
