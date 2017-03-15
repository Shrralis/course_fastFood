package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Fillation extends Owner {
    public String country = null;
    public String city = null;
    public String address = null;
    public Boolean general = null;
    public FastFood fast_food = null;

    public Fillation() {}
    @SuppressWarnings("unused")
    public Fillation(ResultSet from) {
        parse(from);
    }
    @Override
    public Fillation parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            country = from.getString("country");
            city = from.getString("city");
            address = from.getString("address");
            general = from.getBoolean("general");
            fast_food = ParseUtils.parseViaReflection(new FastFood(), get("SELECT * FROM `fast_foods` WHERE `id = " +
                    from.getInt("fast_food") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
