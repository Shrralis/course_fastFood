package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Fast_Food extends Owner {
    public Integer creation_year = null;
    public Company company = null;

    public Fast_Food() {}
    @SuppressWarnings("unused")
    public Fast_Food(ResultSet from) {
        parse(from);
    }
    @Override
    public Fast_Food parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            creation_year = from.getInt("creation_year");
            company = new List<>(get("SELECT * FROM `companies` WHERE `id` = " +
                    from.getInt("company") + ";", connection), Company.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return name + " (" + company.toString() + ")";
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Integer getCreation_year() {
        return creation_year;
    }

    public void setCreation_year(Integer creation_year) {
        this.creation_year = creation_year;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
