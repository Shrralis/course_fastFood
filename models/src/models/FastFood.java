package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class FastFood extends Owner {
    public Integer creation_year = null;
    public Company company = null;

    public FastFood() {}
    @SuppressWarnings("unused")
    public FastFood(ResultSet from) {
        parse(from);
    }
    @Override
    public FastFood parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            creation_year = from.getInt("creation_year");
            company = ParseUtils.parseViaReflection(new Company(), get("SELECT * FROM `companies` WHERE `id` = " +
                    from.getInt("company") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
