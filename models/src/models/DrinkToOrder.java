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
            order = new List<>(get("SELECT * FROM `orders` WHERE `id` = " +
                    from.getInt("order") + ";", connection), Order.class, connection).get(0);
            drink = new List<>(get("SELECT * FROM `drinks` WHERE `id` = " +
                    from.getInt("drink") + ";", connection), Drink.class, connection).get(0);
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

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }
}
