package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

/**
 * Created by shrralis on 3/15/17.
 */
public class Order extends Owner {
    public Date date = null;
    public Time time = null;
    public Filiation filiation = null;

    public Order() {}
    @SuppressWarnings("unused")
    public Order(ResultSet from) {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            date = DateWorker.convertToDate(from.getString("date"));
            time = DateWorker.convertToTime(from.getString("time"));
            filiation = new List<>(get("SELECT * FROM `filiations` WHERE `id` = " +
                    from.getInt("filiation") + ";", connection), Filiation.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return DateWorker.convertDateToString(date) + ", " + time + " — " + filiation;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Filiation getFiliation() {
        return filiation;
    }

    public void setFiliation(Filiation filiation) {
        this.filiation = filiation;
    }
}
