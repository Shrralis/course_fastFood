package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by shrralis on 3/15/17.
 */
public class Order extends Owner {
    public Date datetime = null;
    public Fillation fillation = null;

    public Order() {}
    @SuppressWarnings("unused")
    public Order(ResultSet from) {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            datetime = DateWorker.convertToDateTime(from.getString("datetime"));
            fillation = ParseUtils.parseViaReflection(new Fillation(), get("SELECT * FROM `fillations` WHERE `id` = " +
                    from.getInt("fillation") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Fillation getFillation() {
        return fillation;
    }

    public void setFillation(Fillation fillation) {
        this.fillation = fillation;
    }
}
