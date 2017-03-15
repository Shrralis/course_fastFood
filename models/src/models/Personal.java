package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Personal extends Owner {
    public enum Work {
        адміністратор,
        кухар,
        касир,
        прибиральник,
        охоронець
    }

    public String surname = null;
    public Work work = null;
    public Fillation fillation = null;

    public Personal() {}
    @SuppressWarnings("unused")
    public Personal(ResultSet from) {
        parse(from);
    }

    @Override
    public Personal parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            surname = from.getString("surname");
            work = parseWork(from.getString("work"));
            fillation = ParseUtils.parseViaReflection(new Fillation(), get("SELECT * FROM `fillations` WHERE `id = " +
                    from.getInt("fillation") + ";", connection));
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    private Work parseWork(String src) {
        for (Work value : Work.values()) {
            if (src.equalsIgnoreCase(value.toString())) {
                return value;
            }
        }
        return null;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Fillation getFillation() {
        return fillation;
    }

    public void setFillation(Fillation fillation) {
        this.fillation = fillation;
    }
}
