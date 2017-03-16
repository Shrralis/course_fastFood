package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Drink extends Owner {
    public Integer volume = null;
    public Double calorie = null;
    public Integer amount = null;
    public Double price = null;

    public Drink() {}
    @SuppressWarnings("unused")
    public Drink(ResultSet from) {
        parse(from);
    }
    @Override
    public Drink parse(ResultSet from) {
        super.parse(from);

        try {
            volume = from.getInt("volume");
            calorie = from.getDouble("calorie");

            try {
                amount = from.getInt("amount");
            } catch (SQLException ignored) {}

            price = from.getDouble("price");
        } catch (SQLException ignored) {}
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
