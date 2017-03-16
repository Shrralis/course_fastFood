package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class DrinkToFiliation extends Model {
    public Filiation filiation = null;
    public Drink drink = null;

    public DrinkToFiliation() {}
    @Override
    public DrinkToFiliation parse(ResultSet from, Connection connection) {
        try {
            filiation = new List<>(get("SELECT * FROM `filiations` WHERE `id` = " +
                    from.getInt("filiation") + ";", connection), Filiation.class, connection).get(0);
            drink = new List<>(get("SELECT * FROM `drinks` WHERE `id` = " +
                    from.getInt("drink") + ";", connection), Drink.class, connection).get(0);
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

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }
}
