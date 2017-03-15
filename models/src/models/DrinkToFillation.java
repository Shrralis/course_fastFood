package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class DrinkToFillation extends Model {
    public Fillation fillation = null;
    public Drink drink = null;

    public DrinkToFillation() {}
    @Override
    public DrinkToFillation parse(ResultSet from, Connection connection) {
        try {
            fillation = ParseUtils.parseViaReflection(new Fillation(), get("SELECT * FROM `fillations` WHERE `id` = " +
                    from.getInt("fillation") + ";", connection));
            drink = ParseUtils.parseViaReflection(new Drink(), get("SELECT * FROM `drinks` WHERE `id` = " +
                    from.getInt("drink") + ";", connection));
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

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }
}
