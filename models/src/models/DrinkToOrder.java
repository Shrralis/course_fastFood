package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class DrinkToOrder extends Model {
    public Order order = null;
    public Drink drink = null;

    public DrinkToOrder() {}
    @Override
    public DrinkToOrder parse(ResultSet from, Connection connection) {
        try {
            order = ParseUtils.parseViaReflection(new Order(), get("SELECT * FROM `orders` WHERE `id` = " +
                    from.getInt("order") + ";", connection));
            drink = ParseUtils.parseViaReflection(new Drink(), get("SELECT * FROM `drinks` WHERE `id` = " +
                    from.getInt("drink") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
