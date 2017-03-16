package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Company extends Owner {
    public String country = null;
    public String ceo_full_name = null;
    public Integer creation_year = null;
    public String office_address = null;

    public Company() {}
    @SuppressWarnings("unused")
    public  Company(ResultSet from) {
        parse(from);
    }
    @Override
    public Company parse(ResultSet from) {
        super.parse(from);

        try {
            country = from.getString("country");
            ceo_full_name = from.getString("ceo_full_name");
            creation_year = from.getInt("creation_year");
            office_address = from.getString("office_address");
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return name + " (" + country + ")";
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCeo_full_name() {
        return ceo_full_name;
    }

    public void setCeo_full_name(String ceo_full_name) {
        this.ceo_full_name = ceo_full_name;
    }

    public Integer getCreation_year() {
        return creation_year;
    }

    public void setCreation_year(Integer creation_year) {
        this.creation_year = creation_year;
    }

    public String getOffice_address() {
        return office_address;
    }

    public void setOffice_address(String office_address) {
        this.office_address = office_address;
    }
}
