package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Filiation extends Owner {
    public String country = null;
    public String city = null;
    public String address = null;
    public Boolean general = null;
    public Fast_Food fast_food = null;

    public Filiation() {}
    @SuppressWarnings("unused")
    public Filiation(ResultSet from) {
        parse(from);
    }
    @Override
    public Filiation parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            country = from.getString("country");
            city = from.getString("city");
            address = from.getString("address");
            general = from.getBoolean("general");
            fast_food = new List<>(get("SELECT * FROM `fast_foods` WHERE `id` = " +
                    from.getInt("fast_food") + ";", connection), Fast_Food.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return fast_food.toString() + " - " + country + ", " + city + " " + address;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGeneral() {
        return general;
    }

    public void setGeneral(Boolean general) {
        this.general = general;
    }

    public Fast_Food getFast_food() {
        return fast_food;
    }

    public void setFast_food(Fast_Food fast_food) {
        this.fast_food = fast_food;
    }
}
