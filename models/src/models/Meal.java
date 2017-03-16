package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Meal extends Owner {
    public Double calorie = null;
    public Double weight = null;
    public Integer amount = null;
    public Double price = null;

    public Meal() {}
    @SuppressWarnings("unused")
    public Meal(ResultSet from) {
        parse(from);
    }
    @Override
    public Meal parse(ResultSet from) {
        super.parse(from);

        try {
            calorie = from.getDouble("calorie");
            weight = from.getDouble("weight");

            try {
                amount = from.getInt("amount");
            } catch (SQLException ignored) {}

            price = from.getDouble("price");
        } catch (SQLException ignored) {}
        return this;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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
