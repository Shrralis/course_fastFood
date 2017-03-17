package meal_to_order_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Meal;
import models.MealToOrder;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new MealToOrder();
    }

    @FXML public ComboBox<Meal> meal;
    @FXML public TextField count;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty() || isAnyComboBoxEmpty()) {
            return false;
        }


        if (!count.getText().trim().matches("^\\d+?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + count.getPromptText() + " повинно містити ціле число!")
                    .build()
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        meal.getValue().setCount(count.getText().isEmpty() ? null : new Integer(count.getText()));
        ((MealToOrder) objectToProcess).setMeal(meal.getValue());
    }
    @Override
    protected String getDatabaseTableName() {
        return "orders_has_meals";
    }
}
